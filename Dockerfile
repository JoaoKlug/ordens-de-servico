FROM eclipse-temurin:8-jdk
ARG WILDFLY_VERSION=26.1.3.Final
ENV WILDFLY_HOME=/opt/wildfly

RUN apt-get update && apt-get install -y unzip curl gettext-base && rm -rf /var/lib/apt/lists/* \
 && curl -L -o /tmp/wf.zip https://github.com/wildfly/wildfly/releases/download/${WILDFLY_VERSION}/wildfly-${WILDFLY_VERSION}.zip \
 && unzip -q /tmp/wf.zip -d /opt \
 && ln -s /opt/wildfly-${WILDFLY_VERSION} ${WILDFLY_HOME} \
 && rm -f /tmp/wf.zip

RUN ${WILDFLY_HOME}/bin/add-user.sh -u admin -p admin --silent

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080 9990
ENTRYPOINT ["/entrypoint.sh"]
