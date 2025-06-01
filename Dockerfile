FROM maven:3.9.9-amazoncorretto-21-alpine AS build
WORKDIR /app
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=bind,source=src,target=src \
    mvn clean package -DskipTests

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
LABEL name=flight-service
ENTRYPOINT ["java","-jar","app.jar"]