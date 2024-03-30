FROM maven:latest as build
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn -B dependency:resolve dependency:resolve-plugins
ADD . /usr/src/app
RUN mvn clean package


FROM eclipse-temurin:latest
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY --from=build /usr/src/app/target/dblog-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]