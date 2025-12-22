# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

TheStartupBank is a RESTful API banking application built with Spring Boot 2.1.4, Java 8, Spring Security, and H2 in-memory database. It simulates basic banking operations including customer management, account creation, and transaction processing.

## Build and Run Commands

### Build the project
```bash
./mvnw clean package
```

### Run the application
```bash
./mvnw spring-boot:run
```

The application runs on port **8989** with context path `/the-startup-bank`.
Access the application at: `http://localhost:8989/the-startup-bank`

### Run tests
```bash
# Run all tests
./mvnw test

# Run a specific test class
./mvnw test -Dtest=CustomerServiceTest

# Run a specific test method
./mvnw test -Dtest=CustomerServiceTest#testMethodName
```

### Access H2 Console
- URL: `http://localhost:8989/the-startup-bank/h2-console`
- The H2 console is enabled for database inspection during development

### Access Swagger UI
- URL: `http://localhost:8989/the-startup-bank/swagger-ui.html`
- Swagger is configured for API documentation and testing

### Default Credentials
- Username: `bankapp`
- Password: `changeit`

## Architecture

### Layer Structure
The application follows a standard layered architecture:

1. **REST Layer** (`rest/`): Controllers expose RESTful endpoints
   - [CustomerController.java](src/main/java/com/coding/exercise/bankapp/rest/CustomerController.java)
   - [AccountController.java](src/main/java/com/coding/exercise/bankapp/rest/AccountController.java)
   - [TransactionController.java](src/main/java/com/coding/exercise/bankapp/rest/TransactionController.java)
   - Swagger documentation interfaces in `rest/swagger/`

2. **Service Layer** (`service/`): Business logic and orchestration
   - Services convert between POJOs (DTOs) and entities using `BankServiceHelper`
   - Located in [service/helper/BankServiceHelper.java](src/main/java/com/coding/exercise/bankapp/service/helper/BankServiceHelper.java)

3. **Repository Layer** (`respository/`): Note the typo in package name - it's `respository` not `repository`
   - Spring Data JPA repositories extending `CrudRepository`
   - Contains `CustomerRepository`, `AccountRepository`, `TransactionRepository`

4. **Model Layer** (`model/`): JPA entities
   - [Customer.java](src/main/java/com/coding/exercise/bankapp/model/Customer.java): customerNumber as ID
   - [Account.java](src/main/java/com/coding/exercise/bankapp/model/Account.java): accountNumber (UUID) as ID
   - [Transaction.java](src/main/java/com/coding/exercise/bankapp/model/Transaction.java): UUID as ID

5. **POJOs Layer** (`pojos/`): Data transfer objects (DTOs)
   - Used for request/response payloads
   - `CustomerDetails`, `AccountDetails`, `TransactionDetails`
   - `TransactionType` enum: CASH, TRANSFER

### Key Architectural Patterns

**Entity vs POJO Separation**: The application maintains strict separation between JPA entities (model layer) and DTOs (pojos layer). All conversions happen through static methods in `BankServiceHelper`.

**ID Management**:
- Customer IDs are managed via a synchronized counter in `BankServiceHelper.createCustomerNumber()`
- Account IDs are UUIDs generated automatically
- Transaction IDs are auto-generated UUIDs via `@GeneratedValue`

**Security Configuration**:
- Spring Security is configured in [SecurityConfig.java](src/main/java/com/coding/exercise/bankapp/config/SecurityConfig.java)
- CSRF is disabled (for development/testing)
- H2 console and root path are permitted for all users
- Frame options are disabled for H2 console compatibility

**Transaction Types**:
- CASH: Direct cash deposits/withdrawals
- TRANSFER: Account-to-account transfers (some transfer logic is commented out in the codebase)

### Testing Structure

Tests are organized by layer in `src/test/java/`:
- `repository/`: Repository layer tests
- `service/`: Service layer tests
- `rest/`: Controller integration tests using REST Assured

[BaseTest.java](src/test/java/com/coding/exercise/bankapp/BaseTest.java) provides common test data builders for creating test entities and payloads. Always use these builders when writing new tests for consistency.

### Important Notes

- The repository package has a typo: `respository` instead of `repository`. Maintain this spelling for consistency.
- Lombok is used extensively - `@Builder`, `@Data`, `@AllArgsConstructor`, `@NoArgsConstructor` are common.
- There is commented-out code related to account balance calculation and enhanced transfer functionality in [TransactionService.java](src/main/java/com/coding/exercise/bankapp/service/TransactionService.java) that may indicate future features.
- Custom exceptions: `ResourceNotFoundException` and `BadRequestException` in the `common/` package.

### API Endpoints

**Customers** (`/customers`):
- POST `/customers` - Create customer
- GET `/customers` - List all customers
- GET `/customers/{customerNumber}` - Get customer by ID

**Accounts** (`/accounts`):
- POST `/accounts` - Create account
- GET `/accounts` - List all accounts (optional `?customerNumber=` filter)
- GET `/accounts/{accountNumber}` - Get account by ID

**Transactions** (`/transactions`):
- POST `/transactions` - Create transaction
- GET `/transactions` - List all transactions (optional `?accountNumber=` filter)
- GET `/transactions/{transactionId}` - Get transaction by ID
