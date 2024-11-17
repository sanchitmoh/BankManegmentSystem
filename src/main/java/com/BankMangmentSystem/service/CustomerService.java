package com.BankMangmentSystem.service;

import com.BankMangmentSystem.model.Customer;
import com.BankMangmentSystem.repository.CustomerRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepository = new CustomerRepository();
    private final OtpService otpService = new OtpService(); // Instance of OTP service

    // Add a new customer
    public String addCustomer(Customer customer) {
        if (customerRepository.isCustomerExists(customer.getEmail())) {
            return "Customer already exists.";
        }

        customer.setPassword(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt(12)));
        customer.setCreatedAt(LocalDate.now());
        customer.setRole("USER");

        return customerRepository.addCustomer(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    // Get a customer by ID
    public Customer getCustomerById(int id) {
        return customerRepository.getCustomerById(id);
    }

    // Update a customer, with OTP validation
    public String updateCustomer(Customer customer, int id, String enteredOtp) {
        if (customerRepository.getCustomerById(id) == null) {
            return "Customer not found.";
        }

        // Validate OTP before updating customer data
        String phoneNumber = customer.getPhone();
        boolean isOtpValid = otpService.validateOtp(phoneNumber, enteredOtp);
        if (isOtpValid) {
            return customerRepository.updateCustomer(customer, id);
        } else {
            return "Invalid OTP. Update failed.";
        }
    }

    // Delete a customer, with OTP validation
    public String deleteCustomer(int id, String enteredOtp) {
        if (customerRepository.getCustomerById(id) == null) {
            return "Customer not found.";
        }

        Customer customer = customerRepository.getCustomerById(id);
        String phoneNumber = customer.getPhone();

        // Validate OTP before deleting customer
        boolean isOtpValid = otpService.validateOtp(phoneNumber, enteredOtp);
        if (isOtpValid) {
            return customerRepository.deleteCustomer(id);
        } else {
            return "Invalid OTP. Deletion failed.";
        }
    }

    // Login functionality
    public boolean login(String email, String password) {
        Customer customer = customerRepository.getCustomerByEmail(email);
        return customer != null && BCrypt.checkpw(password, customer.getPassword());
    }

    // Generate and send OTP for actions (update, delete)
    public String generateAndSendOtpForAction(Customer customer) {
        String phoneNumber = customer.getPhone();
        return otpService.generateAndSendOtp(phoneNumber); // Generate and send OTP
    }
}

