# Billing System Backend

This is the backend service for the Billing System project. It handles all business logic, data persistence, and API endpoints for billing operations.

## Features

- Customer management (CRUD)
- Item/product management (CRUD)
- Cart and order management
- Invoice and payment processing
- RESTful API endpoints
- Integration with frontend and external services

## Tech Stack

- Java 17
- Spring Boot / Jakarta EE (update if applicable)
- Maven
- MySQL or PostgreSQL (update as per your DB)
- GlassFish (for deployment)
- JUnit (for testing)

## Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL/PostgreSQL (or your preferred RDBMS)
- GlassFish server

### Setup

1. **Clone the repository:**
   ```sh
   git clone https://github.com/mnmmusharraf/BillingSystemUi.git
   cd BillingSystemUi
   ```

2. **Configure the database:**
   - Update database credentials in `src/main/resources/application.properties` (or equivalent).

3. **Build the project:**
   ```sh
   mvn clean install
   ```

   > To skip tests during build:
   > ```sh
   > mvn clean install -DskipTests
   > ```

4. **Deploy to GlassFish:**
   - Copy the generated `.war` file from `target/` to your GlassFish `autodeploy` directory:
     ```sh
     cp target/*.war /path/to/glassfish/domains/domain1/autodeploy/
     ```

5. **Start the server:**
   - Ensure GlassFish is running and the application is deployed.

### API Usage

- API base URL: `http://localhost:8080/` (update if different)
- See the `api` or `controller` package for available endpoints.

### Running Tests

```sh
mvn test
```

## Contributing

1. Fork this repo
2. Create your feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Create a new Pull Request

## License

This project is licensed under the MIT License.

---

**Maintained by [mnmmusharraf](https://github.com/mnmmusharraf)**
