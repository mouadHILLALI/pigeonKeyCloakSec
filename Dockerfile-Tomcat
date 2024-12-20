FROM tomcat:10.1-jdk21


LABEL authors="Mouad Toto"

COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

COPY src/main/resources/keystore.p12 /usr/local/tomcat/conf/keystore.p12

RUN sed -i '/<\/Service>/i \
<Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol" \
    maxThreads="200" SSLEnabled="true" scheme="https" secure="true"> \
    <SSLHostConfig> \
        <Certificate certificateKeystoreFile="conf/keystore.p12" \
                     certificateKeystorePassword="pass123" \
                     keyAlias="mykey" /> \
    </SSLHostConfig> \
</Connector>' /usr/local/tomcat/conf/server.xml

EXPOSE 8443

CMD ["catalina.sh", "run"]
