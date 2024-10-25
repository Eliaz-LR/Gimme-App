FROM gradle:8-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon


FROM amazoncorretto:21 AS runtime
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
