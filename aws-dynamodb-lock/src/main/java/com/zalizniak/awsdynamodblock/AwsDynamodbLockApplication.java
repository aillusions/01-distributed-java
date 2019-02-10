package com.zalizniak.awsdynamodblock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * -Daws.profile=alex_zalizniak_com_dev
 */
@SpringBootApplication
public class AwsDynamodbLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsDynamodbLockApplication.class, args);
	}

}

