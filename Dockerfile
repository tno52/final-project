FROM eclipse-temurin:17
WORKDIR /home
COPY ./flowers ./flowers
COPY ./target/database-demo-0.0.1-SNAPSHOT.jar database-demo.jar
ENTRYPOINT ["java", "-jar", "database-demo.jar"]