FROM openjdk:11-jre-slim-buster

COPY target/DummyAadhar-0.1.jar /DummyAadhar-0.1.jar

EXPOSE 8022

ENTRYPOINT ["java","-jar","-Djdk.tls.client.protocols=TLSv1.2","-Dmicronaut.server.port=8022","/DummyAadhar-0.1.jar"]
