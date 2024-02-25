FROM maven:3.8.3-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-oracle AS production
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/analytics-service-0.0.1.jar
CMD ["java", "-jar", "/app/analytics-service-0.0.1.jar"]