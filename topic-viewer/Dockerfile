FROM maven:3.6.3-jdk-8-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM adoptopenjdk/openjdk8:slim
COPY --from=build /home/app/target/topic-viewer.jar /usr/local/lib/topic-viewer.jar
EXPOSE 8002
CMD ["java", "-jar", "/usr/local/lib/topic-viewer.jar"]