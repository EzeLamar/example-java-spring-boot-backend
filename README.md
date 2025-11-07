# Sport Booking Manager

Aplicación Spring Boot para la gestión de reservas deportivas.

## Requisitos

- Java 17
- Maven 3.9+
- Docker y Docker Compose (para ejecutar con Docker)

## Ejecución Local (sin Docker)

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Dockerización

Este proyecto está dockerizado y puede ejecutarse completamente con Docker Compose.

### Construcción y Ejecución con Docker Compose (Recomendado)

**Iniciar todos los servicios (aplicación + PostgreSQL):**
```bash
docker-compose up -d
```

**Ver logs de los servicios:**
```bash
docker-compose logs -f manager
docker-compose logs -f postgres
```

**Ver logs de todos los servicios:**
```bash
docker-compose logs -f
```

**Detener todos los servicios:**
```bash
docker-compose down
```

**Detener y eliminar volúmenes (⚠️ elimina la base de datos):**
```bash
docker-compose down -v
```

**Reconstruir la imagen y reiniciar:**
```bash
docker-compose up -d --build
```

**Ejecutar en modo producción:**
```bash
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d
```

**Ver estado de los servicios:**
```bash
docker-compose ps
```

### Comandos Docker Individuales

**Construir la imagen:**
```bash
docker build -t sport-booking-manager .
```

**Ejecutar el contenedor (sin base de datos):**
```bash
docker run -p 8080:8080 sport-booking-manager
```

**Ejecutar en modo interactivo:**
```bash
docker run -it -p 8080:8080 sport-booking-manager
```

**Ejecutar con variables de entorno:**
```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/sportbooking \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  sport-booking-manager
```

**Ver logs del contenedor:**
```bash
docker logs -f <container-id>
```

**Ejecutar comandos dentro del contenedor:**
```bash
docker exec -it <container-id> sh
```

**Eliminar la imagen:**
```bash
docker rmi sport-booking-manager
```

**Limpiar contenedores detenidos:**
```bash
docker container prune
```

**Limpiar imágenes no utilizadas:**
```bash
docker image prune
```

## Configuración

### Perfiles de Spring Boot

El proyecto incluye dos perfiles configurados:

- **dev**: Perfil de desarrollo con logging detallado y SQL visible
- **prod**: Perfil de producción con configuración optimizada y seguridad mejorada

El perfil por defecto es `dev`. Puedes cambiarlo con la variable de entorno `SPRING_PROFILES_ACTIVE`.

**Ejecutar con perfil de producción:**
```bash
SPRING_PROFILES_ACTIVE=prod docker-compose up -d
```

### Variables de Entorno

**Configurar mediante archivo .env (Recomendado):**

1. Copia el archivo de ejemplo:
```bash
cp env.example .env
```

2. Edita `.env` con tus valores:
```bash
SPRING_PROFILES_ACTIVE=dev
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/sportbooking
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
POSTGRES_DB=sportbooking
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
```

El `docker-compose.yml` lee automáticamente el archivo `.env` si existe. Si no existe, usa valores por defecto.

**⚠️ Importante:** Agrega `.env` a tu `.gitignore` para no commitear credenciales.

### Base de Datos

PostgreSQL se ejecuta en el puerto `5432` y los datos se persisten en un volumen de Docker llamado `postgres_data`.

**Conectarse a la base de datos desde el host:**
```bash
docker exec -it sport-booking-postgres psql -U postgres -d sportbooking
```

## Health Check y Actuator

La aplicación incluye un health check que verifica el endpoint `/actuator/health`. Los endpoints del Actuator están configurados en los perfiles:

- **dev**: Expone `health`, `info`, `metrics`, `prometheus`
- **prod**: Expone solo `health`, `info`, `metrics`

**Endpoints disponibles:**
- Health: `http://localhost:8080/actuator/health`
- Info: `http://localhost:8080/actuator/info`
- Metrics: `http://localhost:8080/actuator/metrics`
- Prometheus: `http://localhost:8080/actuator/prometheus` (solo en dev)

## Puertos

- **Aplicación**: `8080`
- **PostgreSQL**: `5432`

## Estructura del Proyecto

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

**El contenedor no inicia:**
```bash
docker-compose logs manager
```

**La aplicación no se conecta a la base de datos:**
- Verifica que PostgreSQL esté corriendo: `docker-compose ps`
- Verifica los logs de PostgreSQL: `docker-compose logs postgres`
- Asegúrate de que las variables de entorno sean correctas

**Reconstruir desde cero:**
```bash
docker-compose down -v
docker-compose build --no-cache
docker-compose up -d
```

## Desarrollo

Para desarrollo local sin Docker, asegúrate de tener PostgreSQL corriendo y configura las propiedades en `application.properties` o mediante variables de entorno.

