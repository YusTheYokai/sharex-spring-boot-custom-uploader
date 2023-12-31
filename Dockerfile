# Use maven to build
FROM maven:3.9.2-eclipse-temurin-17 AS builder

# Establish the working directory
WORKDIR /app

# Copy the source code and pom.xml
COPY ./pom.xml ./
COPY ./src ./src

# Build the JAR file
RUN mvn package -DskipTests

# Eclipse JRE 17 Base Image
FROM eclipse-temurin:17.0.7_7-jre

# Copy the JAR file
COPY --from=builder /app/target/*.jar ./app.jar

# Establish the environment variables
ENV PASSWORD=
ENV URL=
ENV FOLDER=uploads

# Run the JAR file
ENTRYPOINT java -Dsharex.password=$PASSWORD -Dsharex.url=$URL -Dsharex.folder=$FOLDER -jar app.jar
