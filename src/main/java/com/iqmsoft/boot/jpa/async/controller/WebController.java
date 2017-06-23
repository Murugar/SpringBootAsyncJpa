package com.iqmsoft.boot.jpa.async.controller;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iqmsoft.boot.jpa.async.model.Customer;
import com.iqmsoft.boot.jpa.async.repo.CustomerRepository;

@RestController
public class WebController {

	@Autowired
	CustomerRepository repository;

	@RequestMapping("/save")
	public String process() {
		repository.save(new Customer("Jack", "Smith"));
		repository.save(new Customer("Adam", "Johnson"));
		repository.save(new Customer("Kim", "Smith"));
		repository.save(new Customer("David", "Williams"));
		repository.save(new Customer("Peter", "Davis"));
		return "Done";
	}

	@RequestMapping("/findall")
	public String findAll() {
		Future<List<Customer>> future = repository.findAllCustomers();

		List<Customer> customers = Collections.emptyList();

		try {
			customers = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return customers.toString();
	}

	@RequestMapping("/findonebyid")
	public String findOneById(@RequestParam("id") Long id) {

		String result = "";
		CompletableFuture<Customer> future = repository.findOneById(id);

		try {
			Customer customer = future.get();
			result = customer.toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping("/findbylastname")
	public String fetchDataByLastName(@RequestParam("lastname") String lastName) {

		Future<List<Customer>> future = repository.findByLastName(lastName);

		List<Customer> customers = Collections.emptyList();

		try {
			customers = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return customers.toString();
	}

}
