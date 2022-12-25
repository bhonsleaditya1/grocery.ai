FROM gradle:7.6-jdk17-alpine
WORKDIR /app
COPY --from=0 / /app
#USER root                # This changes default user to root
#RUN chown -R gradle /app # This changes ownership of folder
#USER gradle              # This changes the user back to the default user "gradle"

RUN ./gradlew build --stacktrace

FROM openjdk:17-jdk-alpine
MAINTAINER grocery.ai
VOLUME /tmp
EXPOSE 8080
ADD build/libs/messaging-0.0.1-SNAPSHOT.jar grocery.jar
ENTRYPOINT ["java","-jar","/grocery.jar"]