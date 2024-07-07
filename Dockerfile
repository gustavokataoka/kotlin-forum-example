FROM openjdk:21

EXPOSE 8080
ENV PROFILE prod

ADD /target/forum-demo.jar forum.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "forum.jar"]
