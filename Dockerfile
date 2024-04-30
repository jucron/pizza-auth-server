FROM openjdk:17-jdk AS BUILDER
RUN microdnf install findutils
WORKDIR /build
COPY gradlew /build/
COPY build.gradle /build/
COPY .gradle /build/.gradle
COPY src /build/src
RUN ./gradlew build

FROM openjdk:17-jre-slim
ARG VERSION=0.0.1-SNAPSHOT
ARG APP_NAME=PizzaAuthServer
#ADD src/main/resources/application-dev.yml /app/application.yml
WORKDIR /app
COPY --from=BUILDER /build/target/${APP_NAME}-${VERSION}.jar /app/myapp.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar","/app/myapp.jar"]
