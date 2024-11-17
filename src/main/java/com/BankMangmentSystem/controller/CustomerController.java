package com.BankMangmentSystem.controller;

import com.BankMangmentSystem.model.Customer;
import com.BankMangmentSystem.service.CustomerService;

import java.util.List;
import java.util.Scanner;

public class CustomerController {

    private final Scanner scanner = new Scanner(System.in);
    private final CustomerService customerService = new CustomerService();

    // Add a new customer
    public String addCustomer() {
        System.out.println("Enter customer details:");

        String name = getInput("Name: ");
        String email = getInput("Email: ");
        String phone = getInput("Phone: ");
        String accountType = getInput("Account Type (SAVINGS/CURRENT): ");

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAccountType(accountType);

        try {
            return customerService.addCustomer(customer);
        } catch (Exception e) {
            System.out.println("Error adding customer: " + e.getMessage());
            return "Failed to add customer";
        }
    }

    // Show all customers
    public void showAllCustomers() {
        List<Customer> customers = getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Customers List:");
            customers.forEach(System.out::println);
        }
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        try {
            return customerService.getAllCustomers();
        } catch (Exception e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
            return List.of();
        }
    }

    // Show a single customer by ID
    public void showCustomerById() {
        int id = getIntInput("Enter Customer ID: ");
        Customer customer = getCustomerById(id);
        displayCustomerDetails(customer);
    }

    // Get a customer by ID
    public Customer getCustomerById(int id) {
        try {
            return customerService.getCustomerById(id);
        } catch (Exception e) {
            System.out.println("Error retrieving customer by ID: " + e.getMessage());
            return null;
        }
    }

    // Update a customer by ID (including OTP validation)
    public String updateCustomer() {
        int id = getIntInput("Enter Customer ID to update: ");
        Customer existingCustomer = getCustomerById(id);
        if (existingCustomer == null) {
            return "Customer not found.";
        }

        // Generate and send OTP before updating
        String otpResult = customerService.generateAndSendOtpForAction(existingCustomer);
        System.out.println(otpResult);  // Display result of OTP sending (e.g., success or failure)

        // Ask user to enter OTP
        String enteredOtp = getInput("Enter the OTP sent to your phone: ");
        return customerService.updateCustomer(existingCustomer, id, enteredOtp);
    }

    // Delete a customer by ID (including OTP validation)
    public String deleteCustomer() {
        int id = getIntInput("Enter Customer ID to delete: ");
        Customer existingCustomer = getCustomerById(id);
        if (existingCustomer == null) {
            return "Customer not found.";
        }

        // Generate and send OTP before deleting
        String otpResult = customerService.generateAndSendOtpForAction(existingCustomer);
        System.out.println(otpResult);  // Display result of OTP sending (e.g., success or failure)

        // Ask user to enter OTP
        String enteredOtp = getInput("Enter the OTP sent to your phone: ");
        return customerService.deleteCustomer(id, enteredOtp);
    }

    // Utility methods for input handling
    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline
        return value;
    }

    private void displayCustomerDetails(Customer customer) {
        if (customer != null) {
            System.out.println(customer);
        } else {
            System.out.println("No customer found.");
        }
    }
}

