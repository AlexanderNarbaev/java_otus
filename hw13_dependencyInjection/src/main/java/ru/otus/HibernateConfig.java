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
    @Value("${dataSourceURL}")
    private String dataSourceUrl;
    @Value("${dataSourceUser}")
    private String dataSourceUser;
    @Value("${dataSourcePassword}")
    private String dataSourcePassword;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2DdlAutoPropValue;
    @Value("${hibernate.dialect}")
    private String hibernateDialectPropValue;

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
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUser(dataSourceUser);
        dataSource.setPassword(dataSourcePassword);

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
                HIBERNATE_HBM_2_DDL_AUTO, hibernateHbm2DdlAutoPropValue);
        hibernateProperties.setProperty(
                HIBERNATE_DIALECT, hibernateDialectPropValue);
        return hibernateProperties;
    }
}
