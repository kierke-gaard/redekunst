FROM openjdk:14-alpine

WORKDIR /usr/app

COPY target/fullstack.jar fullstack.jar

EXPOSE 80

CMD ["java", "-jar", "fullstack.jar"]