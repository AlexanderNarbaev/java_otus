package ru.otus;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "socket.app")
@Validated
public class ApplicationProperties {
    private String dataSourceURL;
    private String dataSourceUser;
    private String dataSourcePassword;
    private String hbm2ddl;
    private String dialect;

    public String getDataSourceURL() {
        return dataSourceURL;
    }

    public void setDataSourceURL(String dataSourceURL) {
        this.dataSourceURL = dataSourceURL;
    }

    public String getDataSourceUser() {
        return dataSourceUser;
    }

    public void setDataSourceUser(String dataSourceUser) {
        this.dataSourceUser = dataSourceUser;
    }

    public String getDataSourcePassword() {
        return dataSourcePassword;
    }

    public void setDataSourcePassword(String dataSourcePassword) {
        this.dataSourcePassword = dataSourcePassword;
    }

    public String getHbm2ddl() {
        return hbm2ddl;
    }

    public void setHbm2ddl(String hbm2ddl) {
        this.hbm2ddl = hbm2ddl;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }
}
