package com.eteration.simplebanking;

import com.eteration.simplebanking.entity.BankAccount;
import com.eteration.simplebanking.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class SimplebankingApplication {

	@Autowired
	private BankAccountRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SimplebankingApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		List allCustomers = this.repository.findAll();
		System.out.println("Number of customers: " + allCustomers.size());

		BankAccount account = new BankAccount();
		account.setOwner("John");
		account.setBalance(20);
		System.out.println("Saving new customer...");
		this.repository.save(account);

		allCustomers = this.repository.findAll();
		System.out.println("Number of customers: " + allCustomers.size());
	}
}
