package ru.otus.jdbc.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoException;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class JdbcTemplate<T> {

    private static final String SELECT_WORD = "select ";
    private static final String WHERE_WORD = " where ";
    private static final String EQUAL_WORD = " = ";
    private static final String BIND_WORD = " ? ";
    private static final String FROM_WORD = " from ";
    private static final String INSERT_WORD = "insert ";
    private static final String INTO_WORD = " into ";
    private static final String OPEN_PARENTHESIS_WORD = " (";
    private static final String CLOSE_PARENTHESIS_WORD = ") ";
    private static final String VALUE_WORD = " values ";
    private static final String UPDATE_WORD = "update ";
    private static final String SET_WORD = " set ";
    private static Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;

    public JdbcTemplate(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }


    public long create(T objectData) {
        try {
            List<Field> allFields = getAllFields(objectData.getClass());
            List<String> fieldNames = new ArrayList<>(allFields.size());
            List<String> objectValues = new ArrayList<>(allFields.size());
            for (Field field : allFields) {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(Id.class)) {
                    fieldNames.add(field.getName());
                    objectValues.add(field.get(objectData).toString());
                }
            }
            String insertBuilder = INSERT_WORD +
                    INTO_WORD +
                    objectData.getClass().getSimpleName() +
                    OPEN_PARENTHESIS_WORD +
                    String.join(",", fieldNames) +
                    CLOSE_PARENTHESIS_WORD +
                    VALUE_WORD +
                    OPEN_PARENTHESIS_WORD +
                    fieldNames.stream().map(t -> BIND_WORD).collect(Collectors.joining(",")) +
                    CLOSE_PARENTHESIS_WORD;
            return dbExecutor.insertRecord(getConnection(), insertBuilder, objectValues);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    public long update(T objectData) {
        try {
            List<Field> allFields = getAllFields(objectData.getClass());
            List<String> fieldNames = new ArrayList<>(allFields.size());
            List<String> objectValues = new ArrayList<>(allFields.size());
            String idFieldName = "";
            for (Field field : allFields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    idFieldName = field.getName();
                }
                fieldNames.add(field.getName());
                objectValues.add(field.get(objectData).toString());
            }
            String updateBuilder = UPDATE_WORD +
                    objectData.getClass().getSimpleName() +
                    SET_WORD +
                    String.join("=?,", fieldNames) +
                    WHERE_WORD +
                    idFieldName +
                    EQUAL_WORD +
                    BIND_WORD;
            return dbExecutor.insertRecord(getConnection(), updateBuilder, objectValues);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    public void createOrUpdate(T objectData) {
        try {
            List<Field> allFields = getAllFields(objectData.getClass());
            long idFieldValue = 0;
            for (Field field : allFields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    idFieldValue = field.getLong(objectData);
                }
            }
            if (load(idFieldValue, (Class<T>) objectData.getClass()).isPresent()) {
                update(objectData);
            } else {
                create(objectData);
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public Optional<T> load(long id, Class<T> clazz) {
        try {
            if (clazz != null) {
                List<Field> allFields = getAllFields(clazz);
                String idFieldName = "";
                List<String> fieldNames = new ArrayList<>();
                for (Field field : allFields) {
                    if (field.isAnnotationPresent(Id.class)) {
                        idFieldName = field.getName();
                    }
                    fieldNames.add(field.getName());
                }
                String selectBuilder = SELECT_WORD +
                        String.join(",", fieldNames) +
                        FROM_WORD +
                        clazz.getSimpleName() +
                        WHERE_WORD +
                        idFieldName +
                        EQUAL_WORD +
                        BIND_WORD;
                return dbExecutor.selectRecord(getConnection(), selectBuilder, id, resultSet -> {
                    try {
                        if (resultSet.next()) {
                            if (clazz.getConstructors().length == 1 && fieldNames.size() == clazz.getConstructors()[0].getParameterCount()) {
                                Object[] constructorParams = new Object[fieldNames.size()];
                                int index = 0;
                                for (String fieldName : fieldNames) {
                                    constructorParams[index] = resultSet.getObject(fieldName);
                                    index++;
                                }
                                return (T) clazz.getConstructors()[0].newInstance(constructorParams);
                            }
                        }
                    } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        logger.error(e.getMessage(), e);
                    }
                    return null;
                });
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private List<Field> getAllFields(Class clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        List<Field> result = new ArrayList<>(getAllFields(clazz.getSuperclass()));
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return result;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}