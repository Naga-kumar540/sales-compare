# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the source code
COPY . .

# Build the JAR file using Maven (inside the container)
RUN ./mvnw clean package -DskipTests

# Use a lightweight JDK runtime for the final image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the generated JAR from the builder stage
COPY --from=builder /app/target/sales-purchase-compare-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
