FROM openjdk:8
# clojure:openjdk-8-boot-slim-buster

WORKDIR /usr/app

COPY target/fullstack.jar fullstack.jar

EXPOSE 3448
EXPOSE 80

CMD ["java", "-jar", "fullstack.jar"]