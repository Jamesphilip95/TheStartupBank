package com.coding.exercise.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TheStartupBankApplication {

	private static long customerNumber = 1;

	public static void main(String[] args) {
		SpringApplication.run(TheStartupBankApplication.class, args);
	}

	public static synchronized Long createID()
	{
		return customerNumber++;
	}

}
