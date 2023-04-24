FROM docker.io/maven:3.8-openjdk-17-slim AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM mcr.microsoft.com/openjdk/jdk:17-distroless
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar]
