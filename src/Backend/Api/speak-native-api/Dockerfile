# add here building of application in another container and copy output
# build somewhere else and pass build param here!!!
#  java -jar file.jar --spring.profiles.active=docker

FROM tomcat:latest
ADD ./target/SpeakNative.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]