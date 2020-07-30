FROM openjdk:8-jre-alpine

ENV APPLICATION_USER ktor
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY ./target/JobsWrapper-1.0-SNAPSHOT-jar-with-dependencies.jar /app/JobsWrapper.jar
WORKDIR /app

CMD ["java", "-jar", "JobsWrapper.jar"]

