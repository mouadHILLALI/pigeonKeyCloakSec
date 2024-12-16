FROM tomcat:10.1-jdk21
LABEL authors="Mouad Toto"
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]