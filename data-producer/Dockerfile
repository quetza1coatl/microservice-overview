FROM maven:3.6.3-jdk-8-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM adoptopenjdk/openjdk8:slim
COPY --from=build /home/app/target/data-producer.jar /usr/local/lib/data-producer.jar
EXPOSE 8001
CMD ["java", "-jar", "/usr/local/lib/data-producer.jar"]