# Example Java With Spring Boot REST API

A simple REST API for managing book records with Spring Boot, providing endpoints for CRUD operations.

## Requirements

- Java 17
- Maven 3.9+
- Docker and Docker Compose (to run with Docker)

## Local Execution (without Docker)

```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080`

## Dockerization

This project is dockerized and can be run entirely with Docker Compose.

### Build and Run with Docker Compose (Recommended)

**Start all services (application + PostgreSQL):**
```bash
docker-compose up -d
```

**View individual service logs:**
```bash
docker-compose logs -f manager
docker-compose logs -f postgres
```

**View logs for every service:**
```bash
docker-compose logs -f
```

**Stop every service:**
```bash
docker-compose down
```

**Stop and remove volumes (⚠️ removes the database):**
```bash
docker-compose down -v
```

**Rebuild the image and restart:**
```bash
docker-compose up -d --build
```

**Run in production mode:**
```bash
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d
```

**Check service status:**
```bash
docker-compose ps
```

### Individual Docker Commands

**Build the image:**
```bash
docker build -t sport-booking-manager .
```

**Run the container (without a database):**
```bash
docker run -p 8080:8080 sport-booking-manager
```

**Run in interactive mode:**
```bash
docker run -it -p 8080:8080 sport-booking-manager
```

**Run with environment variables:**
```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/sportbooking \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  sport-booking-manager
```

**View container logs:**
```bash
docker logs -f <container-id>
```

**Run commands inside the container:**
```bash
docker exec -it <container-id> sh
```

**Remove the image:**
```bash
docker rmi sport-booking-manager
```

**Prune stopped containers:**
```bash
docker container prune
```

**Prune unused images:**
```bash
docker image prune
```

## Configuration

### Spring Boot Profiles

The project includes two configured profiles:

- **dev**: Development profile with detailed logging and visible SQL
- **prod**: Production profile with optimized configuration and enhanced security

The default profile is `dev`. You can change it with the `SPRING_PROFILES_ACTIVE` environment variable.

**Run with the production profile:**
```bash
SPRING_PROFILES_ACTIVE=prod docker-compose up -d
```

### Environment Variables

**Configure via a .env file (Recommended):**

1. Copy the sample file:
```bash
cp env.example .env
```

2. Edit `.env` with your values:
```bash
SPRING_PROFILES_ACTIVE=dev
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/sportbooking
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
POSTGRES_DB=sportbooking
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
```

`docker-compose.yml` automatically reads the `.env` file if it exists. If it does not, it falls back to default values.

**⚠️ Important:** Add `.env` to `.gitignore` to avoid committing credentials.

### Database

PostgreSQL runs on port `5432`, and data is persisted in a Docker volume named `postgres_data`.

**Connect to the database from the host:**
```bash
docker exec -it sport-booking-postgres psql -U postgres -d sportbooking
```

## Health Check and Actuator

The application includes a health check that hits the `/actuator/health` endpoint. Actuator endpoints are configured per profile:

- **dev**: Exposes `health`, `info`, `metrics`, `prometheus`
- **prod**: Exposes `health`, `info`, `metrics`

**Available endpoints:**
- Health: `http://localhost:8080/actuator/health`
- Info: `http://localhost:8080/actuator/info`
- Metrics: `http://localhost:8080/actuator/metrics`
- Prometheus: `http://localhost:8080/actuator/prometheus` (dev only)

## Swagger / OpenAPI

Interactive API documentation is available at `http://localhost:8080/swagger-ui/index.html`.
Access the OpenAPI JSON specification at `http://localhost:8080/v3/api-docs`.

## Ports

- **Application**: `8080`
- **PostgreSQL**: `5432`

## Project Structure

```
manager/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── docker-compose.prod.yml
├── .dockerignore
├── env.example
└── pom.xml
```

## Troubleshooting

**Container does not start:**
```bash
docker-compose logs manager
```

**Application cannot connect to the database:**
- Check that PostgreSQL is running: `docker-compose ps`
- Inspect PostgreSQL logs: `docker-compose logs postgres`
- Confirm environment variables are correct

**Rebuild from scratch:**
```bash
docker-compose down -v
docker-compose build --no-cache
docker-compose up -d
```

## Development

For local development without Docker, ensure PostgreSQL is running and configure the properties in `application.properties` or via environment variables.

