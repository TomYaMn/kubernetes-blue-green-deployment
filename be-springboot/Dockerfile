# -------- Stage 1: Build --------
FROM maven:3.9.9-eclipse-temurin-24-noble as build

WORKDIR /app

# Only copy necessary files for dependency resolution first (cache optimization)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw dependency:go-offline -B

# Now copy the rest of the source
COPY src ./src

# Package the application
RUN ./mvnw clean package -DskipTests

# -------- Stage 2: Run --------
FROM maven:3.9.9-eclipse-temurin-24-noble

WORKDIR /app

# Copy only the packaged jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
