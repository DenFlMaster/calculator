package com.flmaster.calculator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {
    @Bean
    public DataSource dataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:C:\\Users\\Denis\\IdeaProjects\\calculator\\db.sqlite");
        return dataSource;
    }
}