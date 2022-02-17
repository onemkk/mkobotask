FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/mkobotask.jar mkobotask.jar
ENTRYPOINT ["java", "-jar", "mkobotask.jar"]
