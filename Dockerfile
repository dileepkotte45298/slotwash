FROM eclipse-temurin:17.0.6_10-jdk

ENV P_HOME /slotwash

ENV HEAP 1024M

WORKDIR $P_HOME

ARG JAR_FILE=server/target/*.jar

ENV JHIPSTER_SLEEP 0

COPY ${JAR_FILE} $P_HOME/app.jar

EXPOSE 8082 8080

CMD echo "The application will start in ${JHIPSTER_SLEEP}s..." && \
    echo "Memory: ${HEAP}" && \
    sleep ${JHIPSTER_SLEEP} && \
    java -Xmx${HEAP} -Djava.net.preferIPv4Stack=true -Djava.security.egd=file:/dev/./urandom -jar $P_HOME/app.jar