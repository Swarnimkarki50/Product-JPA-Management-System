FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle
RUN ./gradlew dependencies --no-daemon || true
COPY src src
RUN ./gradlew clean bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/productjpa-0.0.1-SNAPSHOT.jar app.jar
RUN mkdir -p uploads data
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=render"]
