FROM tomcat:10.1-jdk21
LABEL authors="Mouad Toto"
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war
COPY src/main/resources/mykeystore.p12 /usr/local/tomcat/conf/mykeystore.p12
RUN sed -i '/<\/Service>/i \
<Connector protocol="org.apache.coyote.http11.Http11NioProtocol" \
    port="8443" maxThreads="200" SSLEnabled="true" \
    scheme="https" secure="true" \
    keystoreFile="conf/mykeystore.p12" \
    keystorePass="pass123" \
    keystoreType="PKCS12" \
    keyAlias="mykey" />' /usr/local/tomcat/conf/server.xml
EXPOSE 8443
CMD ["catalina.sh", "run"]
