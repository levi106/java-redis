FROM docker.io/maven:3.8-openjdk-17-slim AS build
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM mcr.microsoft.com/openjdk/jdk:17-distroless
RUN mkdir /app
COPY --from=build /app/target/*.jar /app/app.jar
WORKDIR /app
CMD "java" "-jar" "app.jar
