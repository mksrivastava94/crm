# CRM Server

A microservices-based CRM (Customer Relationship Management) system built with Spring Boot, Spring Cloud, and MongoDB.

## Architecture

The system follows a microservices architecture with the following components:

### Core Infrastructure
| Service | Port | Description |
|---------|------|-------------|
| `eureka-server` | 8761 | Service Discovery (Netflix Eureka) |
| `config-server` | 8012 | Centralized Configuration (Spring Cloud Config) |
| `api-gateway` | 8080 | API Gateway (Spring Cloud Gateway) |

### Business Services
| Service | Description |
|---------|-------------|
| `user-service` | User authentication and authorization |
| `profile-service` | User profile management |
| `employee-service` | Employee management |
| `client-service` | Client/customer management |
| `vendor-service` | Vendor management |
| `product-service` | Product catalog management |
| `material-service` | Material/inventory management |
| `order-service` | Order processing |
| `purchase-service` | Purchase order management |
| `sale-service` | Sales management |
| `expense-service` | Expense tracking |
| `file-upload-service` | File storage (AWS S3) |

## Prerequisites

- **Java 17** (OpenJDK)
- **Maven 3.6+**
- **MongoDB** (local or MongoDB Atlas)
- **AWS S3** credentials (for file-upload-service)

## Environment Variables

All sensitive credentials are externalized. Set the following environment variables before running:

| Variable | Description | Default |
|----------|-------------|---------|
| `MONGODB_URI` | MongoDB connection string | `mongodb://localhost:27017/erpinventory` |
| `JWT_SECRET` | Secret key for JWT token signing | `defaultSecretKeyForLocalDevelopment` |
| `AWS_ACCESS_KEY` | AWS S3 access key | (required for file-upload-service) |
| `AWS_SECRET_KEY` | AWS S3 secret key | (required for file-upload-service) |
| `GITLAB_CONFIG_URI` | Git repository URL for config-server | `https://gitlab.com/mohit/erp-configuration.git` |
| `GITLAB_USERNAME` | GitLab username for config-server | (required for config-server) |
| `GITLAB_TOKEN` | GitLab Personal Access Token | (required for config-server) |

### Example: Setting environment variables (Linux/Mac)
```bash
export MONGODB_URI="mongodb://localhost:27017/erpinventory"
export JWT_SECRET="your-secret-key-here"
export AWS_ACCESS_KEY="your-aws-access-key"
export AWS_SECRET_KEY="your-aws-secret-key"
export GITLAB_CONFIG_URI="https://gitlab.com/your-user/erp-configuration.git"
export GITLAB_USERNAME="your-gitlab-username"
export GITLAB_TOKEN="your-gitlab-token"
```

### Example: Setting environment variables (Windows)
```cmd
set MONGODB_URI=mongodb://localhost:27017/erpinventory
set JWT_SECRET=your-secret-key-here
set AWS_ACCESS_KEY=your-aws-access-key
set AWS_SECRET_KEY=your-aws-secret-key
set GITLAB_CONFIG_URI=https://gitlab.com/your-user/erp-configuration.git
set GITLAB_USERNAME=your-gitlab-username
set GITLAB_TOKEN=your-gitlab-token
```

## Building the Project

Each microservice is built independently:

```bash
cd <service-name>
mvn clean package
```

Or build all services:
```bash
for service in eureka-server config-server api-gateway user-service client-service employee-service expense-service file-upload-service material-service order-service product-service profile-service purchase-service sale-service vendor-service; do
  cd $service && mvn clean package && cd ..
done
```

## Running the Application

Start the services in the following order:

1. **Eureka Server** (Service Discovery)
   ```bash
   cd eureka-server && mvn spring-boot:run
   ```

2. **Config Server** (requires `GITLAB_*` environment variables)
   ```bash
   cd config-server && mvn spring-boot:run
   ```

3. **API Gateway**
   ```bash
   cd api-gateway && mvn spring-boot:run
   ```

4. **Business Services** (start in any order after Eureka is running)
   ```bash
   cd user-service && mvn spring-boot:run
   # Repeat for other services
   ```

## Known Issues

- **order-service**: Missing `model` and `external` packages — requires implementation of entity classes and DTOs.
- **config-server**: Requires valid GitLab credentials to clone the configuration repository.

## Security

- All credentials are externalized via environment variables
- JWT-based authentication is used for API protection
- MongoDB connection strings should never be committed to version control
- Rotate all secrets regularly

## Technology Stack

- **Backend**: Spring Boot 2.7.x, Spring Cloud
- **Database**: MongoDB
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Authentication**: JWT (Auth0)
- **File Storage**: AWS S3
- **Circuit Breaker**: Resilience4j
- **Distributed Tracing**: Spring Cloud Sleuth + Zipkin
