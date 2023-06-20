FROM eclipse-temurin:11

WORKDIR /opt/app

COPY csv2sqlite-0.0.1-SNAPSHOT-jar-with-dependencies.jar /opt/app

ENTRYPOINT ["java", "-jar", "/opt/app/csv2sqlite-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
