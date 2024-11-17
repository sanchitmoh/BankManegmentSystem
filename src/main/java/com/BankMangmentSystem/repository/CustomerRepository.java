package com.BankMangmentSystem.repository;

import com.BankMangmentSystem.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Bank_Mangment_System";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Add a new customer
    public String addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, email, phone, account_type, password, role, is_verified, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAccountType());
            pstmt.setString(5, customer.getPassword());
            pstmt.setString(6, customer.getRole());
            pstmt.setBoolean(7, customer.isVerified());
            pstmt.setDate(8, Date.valueOf(customer.getCreatedAt()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0 ? "Customer added successfully" : "Customer registration failed";

        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    // Check if customer exists by email
    public boolean isCustomerExists(String email) {
        String sql = "SELECT id FROM customers WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking customer existence: " + e.getMessage());
            return false;
        }
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                customers.add(mapRowToCustomer(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
        return customers;
    }

    // Get customer by ID
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToCustomer(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customer by ID: " + e.getMessage());
        }
        return null;
    }

    // Update customer details
    public String updateCustomer(Customer customer, int id) {
        String sql = "UPDATE customers SET name = ?, email = ?, phone = ?, account_type = ?, is_verified = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAccountType());
            pstmt.setBoolean(5, customer.isVerified());
            pstmt.setInt(6, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0 ? "Customer updated successfully" : "Customer update failed";

        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    // Delete customer by ID
    public String deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0 ? "Customer deleted successfully" : "Customer deletion failed";

        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    // Helper method to map a result set row to a Customer object
    private Customer mapRowToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAccountType(rs.getString("account_type"));
        customer.setVerified(rs.getBoolean("is_verified"));
        customer.setRole(rs.getString("role"));
        customer.setCreatedAt(rs.getDate("created_at").toLocalDate());
        return customer;
    }

    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCustomer(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customer by email: " + e.getMessage());
        }
        return null; // Return null if no customer is found
    }

}
