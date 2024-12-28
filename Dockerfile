FROM  ubuntu:20.04 AS build
RUN apt-get update -y && apt-get install maven -y
RUN apt install openjdk-21-jdk-headless -y
COPY . app
WORKDIR app
RUN mvn clean install -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /app/target/stream-0.0.1-SNAPSHOT.jar /app/stream.jar
WORKDIR /app
EXPOSE 4002

CMD ["java", "-jar", "stream.jar"]