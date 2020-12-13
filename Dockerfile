FROM azul/zulu-openjdk:14

EXPOSE 7000

VOLUME /tmp
ADD target/javalin-todobackend-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]