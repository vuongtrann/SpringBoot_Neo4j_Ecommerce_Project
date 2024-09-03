# Importing JDK and copying required files
FROM openjdk:17-jdk AS build
WORKDIR /Ecommerce
COPY pom.xml .
COPY src src

# Copy Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final Docker image using OpenJDK 19
FROM openjdk:17-jdk
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /Ecommerce/target/*.jar app.war
ENTRYPOINT ["java","-jar","/app.war"]
EXPOSE 8080