FROM openjdk:14-alpine
COPY build/libs/weighter-*-all.jar weighter.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "weighter.jar"]