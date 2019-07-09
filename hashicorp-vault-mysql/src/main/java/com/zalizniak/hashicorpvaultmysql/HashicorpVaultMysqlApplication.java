package com.zalizniak.hashicorpvaultmysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class HashicorpVaultMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(HashicorpVaultMysqlApplication.class, args);
    }

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void postConstruct() throws Exception {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT CURRENT_USER();");
            resultSet.next();

            System.out.println("Connection works with User: " + resultSet.getString(1));

            resultSet.close();
        }
    }

}
