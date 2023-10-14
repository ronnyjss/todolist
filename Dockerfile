FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install -y openjdk-17-jdk

COPY . .

RUN apt-get install -y maven
RUN mvn clean install

EXPOSE 8080

COPY --from=build /target/todolist-0.0.1.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]