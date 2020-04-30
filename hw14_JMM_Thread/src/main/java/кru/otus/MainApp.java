package кru.otus;

public class MainApp {
    public static int counter = 0;

    public static void main(String[] args) {

        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                int value = 0;
                while (true) {
                    if (counter < 10) {
                        counter++;
                        System.out.print(value++);
                    } else {
                        counter--;
                        System.out.print(value--);
                    }
                }
            }
        };
        t.start();
    }
}
