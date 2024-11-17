

# Bank Management System

This is a Bank Management System project that allows for managing customers' accounts and handling their operations securely. It includes functionalities such as adding, updating, deleting customers, and also a simple OTP-based system to confirm sensitive operations like updating and deleting customer records.

## Features

- **Customer Management**: Add, update, delete, and retrieve customer details.
- **OTP Verification**: Secure actions (like update and delete) are protected with OTP verification sent via SMS.
- **Login**: Users can login securely with their email and password.

## Technologies Used

- **Java**: Core language used for development.
- **BCrypt**: For password hashing and verification.
- **OkHttp**: For sending OTP via SMS using the Textbelt API.
- **Textbelt API**: Used for sending SMS OTP to users.

## Project Structure

```
BankMangmentSystem/
├── README.md                   # Project documentation
├── src/
│   ├── com/
│   │   ├── BankMangmentSystem/
│   │   │   ├── controller/       # Contains Controller classes for handling user inputs
│   │   │   ├── model/            # Contains Customer model class
│   │   │   ├── repository/       # Contains classes for database operations
│   │   │   ├── service/          # Contains business logic, including OTP logic
│   │   │   ├── utils/            # Contains utility classes, such as SendSms for OTP
│   │   │   └── BankMangmentSystemApplication.java # Main entry point of the application
└── .gitignore                   # Git ignore file for ignoring compiled files and dependencies
```

## Prerequisites

1. **Java 11+**: Ensure you have Java 11 or higher installed on your machine.
2. **OkHttp library**: Used for sending OTP through Textbelt API.
3. **Textbelt Account (Optional)**: If you plan to use the Textbelt API for SMS, either create a free account or host your own Textbelt server.

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/sanchitmoh/BankMangmentSystem.git
cd BankMangmentSystem
```

### 2. Install dependencies

Make sure you have Maven or Gradle installed. You can run the following command to build the project with Maven:

```bash
mvn clean install
```

### 3. Configuration

To use the Textbelt API for sending OTP, you can either use their free public API key (which has usage limitations) or replace it with your own API key for a paid/self-hosted instance.

In `SendSms.java`, replace the `API_KEY` value with your Textbelt API key:

```java
private static final String API_KEY = "YOUR_ACTUAL_API_KEY";
```

For free usage, you can leave the `API_KEY` as `"textbelt"`.

### 4. Running the Application

To run the application, use the following command:

```bash
mvn exec:java
```

This will launch the Bank Management System. You can interact with it via the console by choosing options for customer management, OTP verification, and login.

## Usage

### 1. Add Customer

- When adding a new customer, you will be prompted for:
  - Name
  - Email
  - Phone number
  - Account type

### 2. Update/Delete Customer

- For operations like updating or deleting a customer, OTP verification is required:
  - The OTP will be sent to the customer’s phone number.
  - You will need to enter the OTP to confirm the operation.

### 3. Login

- Login to the system using the customer’s email and password.

## Example of Sending OTP

The `SendSms` utility class is used to send OTPs via SMS using the Textbelt API. This functionality is triggered when performing sensitive actions like updating or deleting customer records.

### Example of an OTP request:

```java
OtpService otpService = new OtpService();
String phoneNumber = "+1234567890";  // Customer phone number
String otpResponse = otpService.generateAndSendOtp(phoneNumber);
System.out.println(otpResponse);  // Will print "OTP sent successfully."
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
