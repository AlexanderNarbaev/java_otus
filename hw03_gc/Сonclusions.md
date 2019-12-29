### Тестирование эффективности различных сборщиков мусора JVM

Тестовый стенд:

    Java Virtual Machine
        java version "13" 2019-09-17
        Java(TM) SE Runtime Environment (build 13+33)
        Java HotSpot(TM) 64-Bit Server VM (build 13+33, mixed mode, sharing)
    Operating System
        Майкрософт Windows 10 Домашняя для одного языка 64-bit
    CPU
        Intel Core i7 @ 2.20GHz	46 °C
        Coffee Lake 14nm Technology
    RAM
        16,0ГБ
    Motherboard
        ASUSTeK COMPUTER INC. FX504GE (U3E1)
    Graphics
        Generic PnP Monitor (1920x1080@60Hz)
        Intel UHD Graphics 630 (ASUStek Computer Inc)
        4095MB NVIDIA GeForce GTX 1050 Ti (ASUStek Computer Inc)	37 °C
        ForceWare version: 441.12
        SLI Disabled
    Storage
        119GB KINGSTON RBUSNS8154P3128GJ (SATA-2 (SSD))
        931GB Seagate ST1000LM035-1RK172 (SATA )	35 °C

Суть измеряемой программы:
    
    Введены метрики:
    
    minimalAddTime - минимальное время добавления в коллекцию всех элементов
    maximumAddTime - максимальное время добавления в коллекцию всех элементов
    averageAddTime - среднее время добавления в коллекцию всех элементов
    addDelayCount - количество задержек(когда фактическое время превышало последнее минимальное) добавления в коллекцию всех элементов
    failAddCount - количество ошибок добавления в коллекцию всех элементов
    addThroughput - пропускная способность добавления объектов (кол-во добавленных объектов поделенное на время работы программы)
    
    minimalTrimTime - минимальное время сокращения коллекции вдвое
    maximumTrimTime - максимальное время сокращения коллекции вдвое
    averageTrimTime - среднее время сокращения коллекции вдвое
    trimDelayCount - количество задержек(когда фактическое время превышало последнее минимальное) сокращения коллекции вдвое
    failTrimCount - количество ошибок операции сокращения коллекции вдвое
    
    time - общее время выполнения программы
    
    Event:	end of minor GC, duration:	, count:	 - событие: сборка молодых объектов мусора, продолжительность за всё время выполнения: общее количество:
    Event:	end of major GC, duration:	, count:	 - событие: сборка старых объектов мусора, продолжительность за всё время выполнения: общее количество:
    
    Действия: при запуске main class происходит подключение к MBean GarbageCollection
    на события cборки мусора, а также запуск основного потока программы, 
    который выполняет хранение массива, в который добавляются в цикле, продолжительностью до 5 минут реального времени,
    по 3 500 000 (три миллиона пятьсот тысяч) элементов, а после происходит удаление половины элементов.
    
    Результатом выполнения программы становится падения по утечке памяти.
    
    За критерий оценки эффективности я взял производительность программы, 
    в таблице приведены результаты измерений для разных сборщиков и разных параметров хипа:
    
                            |-Xms512m                           |-Xms512m                           |-Xms512m                               |-Xms10G
                            |-Xmx512m                           |-Xmx512m                           |-Xmx512m                               |-Xmx512m
    Метрика                 |-XX:+HeapDumpOnOutOfMemoryError    |-XX:+HeapDumpOnOutOfMemoryError    |-XX:+HeapDumpOnOutOfMemoryError        |-XX:+HeapDumpOnOutOfMemoryError
                            |-XX:+UseSerialGC                   |-XX:+UseParallelGC                 |-XX:+UseConcMarkSweepGC                |-XX:+UseG1GC
    ------------------------|-----------------------------------|-----------------------------------|---------------------------------------|-------------------------------
    minimalAddTime          |0                                  |0                                  |0                                      |0
    maximumAddTime          |586                                |915                                |853                                    |1 014
    averageAddTime          |293                                |457                                |426                                    |507
    addDelayCount           |14 179                             |403                                |19 870                                 |11 773
    failAddCount            |0                                  |0                                  |0                                      |0
    minimalTrimTime         |51                                 |60                                 |55                                     |136
    maximumTrimTime         |87                                 |162                                |134                                    |336
    averageTrimTime         |69                                 |111                                |94                                     |236
    trimDelayCount          |64                                 |3                                  |122                                    |97
    failTrimCount           |7                                  |0                                  |7                                      |7
    time                    |218 000                            |7 000                              |297 000                                |299 000
    Event: end of minor GC  |duration: 812, count: 82           |duration: 660, count: 4            |duration: 1 314, count: 136            |duration: 53 573, count: 915
    Event: end of major GC  |duration: 120 926, count: 308      |duration: 2 520, count: 6          |duration: 133 116, count: 264          |duration: 115 243, count: 195
    Дополнительно           |                                   |                                   |Программа завершилась до падения в OME |Программа завершилась до падения в OME
    
    Увеличиваем продолжительность до 15 минут и одновременно увеличиваем размер Heap 
    
                            |-Xms10G                                |-Xms10G                                |-Xms10G                                |-Xms10G
                            |-Xmx10G                                |-Xmx10G                                |-Xmx10G                                |-Xmx10G
    Метрика                 |-XX:+HeapDumpOnOutOfMemoryError        |-XX:+HeapDumpOnOutOfMemoryError        |-XX:+HeapDumpOnOutOfMemoryError        |-XX:+HeapDumpOnOutOfMemoryError
                            |-XX:+UseSerialGC                       |-XX:+UseParallelGC                     |-XX:+UseConcMarkSweepGC                |-XX:+UseG1GC
    ------------------------|---------------------------------------|---------------------------------------|---------------------------------------|-------------------------------
    minimalAddTime          |0                                      |0                                      |0                                      |0
    maximumAddTime          |504                                    |1 814                                  |766                                    |470
    averageAddTime          |252                                    |907                                    |383                                    |235
    addDelayCount           |52 750                                 |55 305                                 |51 853                                 |48 376
    failAddCount            |0                                      |0                                      |0                                      |0
    addThroughput           |1 875                                  |1 579                                  |1 597                                  |1 305
    minimalTrimTime         |54                                     |77                                     |56                                     |81
    maximumTrimTime         |199                                    |309                                    |174                                    |521
    averageTrimTime         |126                                    |193                                    |115                                    |301
    trimDelayCount          |662                                    |598                                    |619                                    |499
    failTrimCount           |7                                      |7                                      |7                                      |7
    time                    |814 000                                |873 000                                |897 000                                |882 000
    Event: end of minor GC  |duration: 20 471, count: 60            |duration: 98 451, count: 178           |duration: 153 204, count: 444          |duration: 190 789, count: 530
    Event: end of major GC  |Не производилась                       |duration: 1 916, count: 2              |duration: 147 457, count: 26           |Не производилась 
    Дополнительно           |Программа завершилась до падения в OME |Программа завершилась до падения в OME |Программа завершилась до падения в OME |Программа завершилась до падения в OME
    
    Видно, что при сборщике SerialGC производительность максимальна - т.е. минимальное время операции добавления, и удаления, но при этом есть ошибки при ранней сборки мусора.
    У сборщика ParallelGC при меньшем размере Heap нет ошибок удаления, но при этом наименьшее время работы до падения с OutOfMemoryError. При увелечении Heap наблюдается только увеличение задержек в работе
    Сборщики ConcMarkSweepGC и G1GC показывают лучшую стабильность по времени работы, т.е. приложение не падает в OutOfMemoryError при заданных параметрах на протяжении 5 минут и более,
     но при при этом также есть ошибки при подчищении коллекции, из-за предварительной очитски объектов в памяти, и повышен расход процессорного времени и увелечены задержки из-за частой сборки мусора.