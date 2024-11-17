package com.BankMangmentSystem;

import com.BankMangmentSystem.controller.CustomerController;
import com.BankMangmentSystem.service.OtpService;
import com.BankMangmentSystem.model.Customer;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        CustomerController customerController = new CustomerController();
        OtpService otpService = new OtpService(); // Initialize OTP service
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Bank Account Management System!");

        while (true) {
            // Display menu
            System.out.println("\n--------------------------- Menu --------------------------");
            System.out.println("1: Add Customer");
            System.out.println("2: Get All Customers");
            System.out.println("3: Get Customer By ID");
            System.out.println("4: Update Customer");
            System.out.println("5: Delete Customer");
            System.out.println("6: Send OTP");
            System.out.println("7: Validate OTP");
            System.out.println("8: Exit");
            System.out.println("----------------------------------------------------------");
            System.out.print("Enter your choice: ");

            int choice;

            // Validate input for choice
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
                continue;
            }

            switch (choice) {
                case 1: // Add Customer
                    System.out.println("\nAdding a new customer...");
                    String result = customerController.addCustomer();
                    System.out.println("Result: " + result);
                    break;

                case 2: // Get All Customers
                    System.out.println("\nFetching all customers...");
                    List<Customer> customers = customerController.getAllCustomers();
                    if (customers.isEmpty()) {
                        System.out.println("No customers found.");
                    } else {
                        System.out.println("Customer List:");
                        for (Customer customer : customers) {
                            System.out.println(customer);
                        }
                    }
                    break;

                case 3: // Get Customer By ID
                    System.out.print("\nEnter the Customer ID: ");
                    int id;
                    try {
                        id = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID. Please enter a valid number.");
                        break;
                    }
                    Customer customer = customerController.getCustomerById(id);
                    if (customer == null) {
                        System.out.println("Customer with ID " + id + " not found.");
                    } else {
                        System.out.println("Customer Details: " + customer);
                    }
                    break;

                case 4: // Update Customer
                    System.out.print("\nEnter the Customer ID to update: ");
                    int updateId;
                    try {
                        updateId = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID. Please enter a valid number.");
                        break;
                    }

                    Customer customerToUpdate = customerController.getCustomerById(updateId);
                    if (customerToUpdate == null) {
                        System.out.println("Customer with ID " + updateId + " not found.");
                    } else {
                        System.out.print("Enter updated name: ");
                        String updatedName = sc.nextLine();

                        System.out.print("Enter updated email: ");
                        String updatedEmail = sc.nextLine();

                        System.out.print("Enter updated phone: ");
                        String updatedPhone = sc.nextLine();

                        System.out.print("Enter updated account type: ");
                        String updatedAccountType = sc.nextLine();

                        customerToUpdate.setName(updatedName);
                        customerToUpdate.setEmail(updatedEmail);
                        customerToUpdate.setPhone(updatedPhone);
                        customerToUpdate.setAccountType(updatedAccountType);

                        String updateResult = customerController.updateCustomer();

                        System.out.println("Update Result: " + updateResult);
                    }
                    break;

                case 5: // Delete Customer
                    System.out.print("\nEnter the Customer ID to delete: ");
                    int deleteId;
                    try {
                        deleteId = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID. Please enter a valid number.");
                        break;
                    }

                    String deleteResult = customerController.deleteCustomer();
                    System.out.println("Delete Result: " + deleteResult);
                    break;

                case 6: // Send OTP
                    System.out.print("\nEnter phone number to send OTP: ");
                    String phoneNumber = sc.nextLine();
                    String otpResult = otpService.generateAndSendOtp(phoneNumber); // Call OTP service to send OTP
                    System.out.println(otpResult);
                    break;

                case 7: // Validate OTP
                    System.out.print("\nEnter phone number to validate OTP: ");
                    String validatePhoneNumber = sc.nextLine();
                    System.out.print("Enter OTP: ");
                    String enteredOtp = sc.nextLine();
                    boolean otpValid = otpService.validateOtp(validatePhoneNumber, enteredOtp);
                    if (otpValid) {
                        System.out.println("OTP validated successfully.");
                    } else {
                        System.out.println("Invalid OTP.");
                    }
                    break;

                case 8: // Exit
                    System.out.println("Thank you for using the Bank Account Management System. Goodbye!");
                    sc.close();
                    System.exit(0);

                default: // Invalid choice
                    System.out.println("Invalid choice. Please select a valid option (1-8).");
                    break;
            }
        }
    }
}


