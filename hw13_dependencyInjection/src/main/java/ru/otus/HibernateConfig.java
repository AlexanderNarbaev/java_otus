package ru.otus;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class HibernateConfig implements TransactionManagementConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(HibernateConfig.class);

    private static final String MAIN_PACKAGE_NAME = "ru.otus.model";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_HBM_2_DDL_AUTO = "hibernate.hbm2ddl.auto";
    private final String DATA_SOURCE_URL;
    private final String DATA_SOURCE_USER;
    private final String DATA_SOURCE_PASSWORD;
    private final String HIBERNATE_HBM_2_DDL_AUTO_PROP_VALUE;
    private final String HIBERNATE_DIALECT_PROP_VALUE;

    public HibernateConfig(
            @Value("${dataSourceURL}")
                    String data_source_url,
            @Value("${dataSourceUser}")
                    String data_source_user,
            @Value("${dataSourcePassword}")
                    String data_source_password,
            @Value("${hibernate.hbm2ddl.auto}")
                    String hibernate_hbm_2_ddl_auto_prop_value,
            @Value("${hibernate.dialect}")
                    String hibernate_dialect_prop_value) {
        DATA_SOURCE_URL = data_source_url;
        DATA_SOURCE_USER = data_source_user;
        DATA_SOURCE_PASSWORD = data_source_password;
        HIBERNATE_HBM_2_DDL_AUTO_PROP_VALUE = hibernate_hbm_2_ddl_auto_prop_value;
        HIBERNATE_DIALECT_PROP_VALUE = hibernate_dialect_prop_value;
    }

    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return hibernateTransactionManager();
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(MAIN_PACKAGE_NAME);
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(DATA_SOURCE_URL);
        dataSource.setUser(DATA_SOURCE_USER);
        dataSource.setPassword(DATA_SOURCE_PASSWORD);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                HIBERNATE_HBM_2_DDL_AUTO, HIBERNATE_HBM_2_DDL_AUTO_PROP_VALUE);
        hibernateProperties.setProperty(
                HIBERNATE_DIALECT, HIBERNATE_DIALECT_PROP_VALUE);
        return hibernateProperties;
    }
}
