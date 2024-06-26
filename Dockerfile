FROM maven:3.8.5-openjdk-17 as build
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn -B dependency:resolve dependency:resolve-plugins
ADD . /usr/src/app
RUN mvn install


FROM eclipse-temurin:17-jre-alpine
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY --from=build /usr/src/app/target/dblog-0.0.2-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]