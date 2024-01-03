FROM openjdk:21-slim AS builder
WORKDIR /app
COPY . .

RUN ./mvnw clean package

FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/target/transaction-servive-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "transaction-servive-0.0.1-SNAPSHOT.jar"]
