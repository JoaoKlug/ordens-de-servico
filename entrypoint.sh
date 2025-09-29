#!/usr/bin/env bash
set -e
: ${DB_HOST:=oracle}
: ${DB_PORT:=1521}
: ${DB_SERVICE:=XEPDB1}
: ${DB_USER:=COPEL}
: ${DB_PASSWORD:=copel}
: ${DS_NAME:=CopelDS}
: ${JNDI_NAME:=java:/jdbc/CopelDS}
: ${WILDFLY_HOME:=/opt/wildfly}

if [ ! -f /extras/ojdbc8.jar ]; then
  echo "ERRO: /extras/ojdbc8.jar não encontrado. Coloque o driver em ./extras/ e suba de novo."
  exit 1
fi

# adiciona módulo Oracle (sem module.xml)
${WILDFLY_HOME}/bin/jboss-cli.sh --commands="module add --name=com.oracle --resources=/extras/ojdbc8.jar --dependencies=javax.api,javax.transaction.api" || true

# configura driver + datasource (offline)
cat > /opt/configure-ds.cli <<CLI
embed-server --server-config=standalone.xml --std-out=echo
if (outcome == success) of /subsystem=datasources/jdbc-driver=oracle:read-resource
  /subsystem=datasources/jdbc-driver=oracle:remove
end-if
/subsystem=datasources/jdbc-driver=oracle:add(driver-name=oracle,driver-module-name=com.oracle,driver-class-name=oracle.jdbc.OracleDriver)
if (outcome == success) of /subsystem=datasources/data-source=${DS_NAME}:read-resource
  data-source remove --name=${DS_NAME}
end-if
data-source add --name=${DS_NAME} --jndi-name=${JNDI_NAME} --driver-name=oracle \
 --connection-url=jdbc:oracle:thin:@//${DB_HOST}:${DB_PORT}/${DB_SERVICE} \
 --user-name=${DB_USER} --password=${DB_PASSWORD} \
 --min-pool-size=5 --max-pool-size=20 --validate-on-match=true --background-validation=true
stop-embedded-server
CLI

${WILDFLY_HOME}/bin/jboss-cli.sh --file=/opt/configure-ds.cli

# libera interfaces e inicia WildFly
exec ${WILDFLY_HOME}/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0
