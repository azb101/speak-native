# add here building of application in another container and copy output
# build somewhere else and pass build param here!!!
#  java -jar file.jar --spring.profiles.active=docker

FROM maven:3.6.0-jdk-11-slim AS build

COPY / /home/src
RUN mvn -f /home/src/pom.xml clean compile package -Dmaven.test.skip=true

FROM tomcat:latest
COPY --from=build /home/src/target/SpeakNative.war /usr/local/tomcat/webapps

CMD ["catalina.sh", "run"]