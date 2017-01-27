package com.teamtreehouse.instateam.config;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
// This is a Spring configuration file for database functionality.
@Configuration
@PropertySource("application.properties")
public class DataConfig {

    @Autowired
    private Environment env;

    // This method initializes a SessionFactory bean.
    // A session is simply a CRUD operation executed, like inserting, updating or deleting a recording
    // in the database.
    // The SessionFactory is a way of Hibernate Java framework to communicate with the database.
    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        Resource config = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setConfigLocation(config);
        sessionFactory.setPackagesToScan(env.getProperty("instateam.entity.package"));
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }

    // This method initializes a DataSource bean.
    // The data source is the actual tables and recorings.
    // This method is called by the session factory bean.
    // It gets it's information from the application.properties file, like driver, url, username and password.
    @Bean
    public DataSource dataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(env.getProperty("instateam.db.driver"));
        ds.setUrl(env.getProperty("instateam.db.url"));
        ds.setUsername(env.getProperty("instateam.db.username"));
        ds.setPassword(env.getProperty("instateam.db.password"));
        return ds;
    }
}
