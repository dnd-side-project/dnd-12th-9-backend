FROM openjdk:17-oracle

COPY api/build/libs/*.jar /sbooky.jar
ENTRYPOINT ["java", "-jar", "/sbooky.jar"]
