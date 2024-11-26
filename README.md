# Exam Portal Backend

## Project Overview
This is a Spring Boot backend application for an Exam Portal, providing comprehensive functionality for user registration, authentication, quiz management, and result tracking.

## Project Structure

### Main Directories
- src/main/java: Primary source code
- src/test/java: Test cases
- graphs: Project architecture and interaction diagrams
- coverageReport: Code coverage reports
- htmlReport: Detailed code coverage HTML reports

### Key Components
1. *Configurations*
    - JWT authentication
    - Security configurations
    - Request filtering

2. *Controllers*
    - Authentication
    - Category management
    - Quiz management
    - Question handling
    - Quiz result tracking

3. *Models*
    - User
    - Role
    - Quiz
    - Question
    - Category
    - Login/Authentication models

4. *Services*
    - Implementation of business logic
    - Service interfaces
    - Service implementations

5. *Repositories*
    - Database interaction layers
    - JPA repositories for each entity

## Technologies and Tools Used

### Backend Technologies
- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Token) for authentication
- JPA/Hibernate
- Maven

### Testing Tools
- JUnit (Unit Testing)
- Mockito (Mocking)
- JMeter (Performance Testing)

### Development and Analysis Tools
- IntelliJ IDEA (IDE)
- JArchitect (Code Architecture Analysis)
- JaCoCo (Code Coverage)

### Design and Documentation
- Markdown
- SVG for activity and interaction diagrams

## Project Setup and Running

### Prerequisites
- Java Development Kit (JDK) 11+
- Maven
- IDE (IntelliJ IDEA recommended)

### Installation Steps
1. Clone the repository
2. Open the project in your IDE
3. Ensure Maven dependencies are downloaded
4. Configure application.properties with your database settings
5. Run ExamPortalBackendApplication.java

### Running Tests
- Unit Tests: mvn test
- Integration Tests: Located in src/test/java/com/project/examportalbackend/IntegrationTest
- Code Coverage Report: mvn jacoco:report

## Project Architecture

### Activity Transition Graphs
Located in graphs/activity transition graphs/
- Covers transitions for:
    - User Registration/Login
    - Quiz Flow
    - Question Management
    - Result Processing

### Interaction Maps
Located in graphs/class interaction map/
- Detailed interactions between:
    - Controllers and Services
    - Services and Repositories
    - End-to-end interaction flow

## Code Quality and Coverage

### Coverage Reports
- Detailed HTML reports in htmlReport
- Covers different sorting and analysis perspectives
- Visualizes code coverage across different classes and methods

### JArchitect Analysis
- Architectural insights in graphs/jarchitect project file/
- Provides code quality metrics and potential improvements

## Performance Testing
- JMeter test plan available in testPlans/User Registration and Login.jmx
- Simulates user registration and login scenarios

## Contribution Guidelines
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License
[Add your license information here]

## Contact
[Add project maintainer contact information]