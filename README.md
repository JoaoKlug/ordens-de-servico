# OS — Ordens de Serviço (Java 8 • JSF • JPA • Oracle • Payara 5)

Projeto **didático** para demonstrar: abrir/atribuir/concluir **Ordens de Serviço**, listar/filtrar por **status/prioridade** e **autocompletar endereço por CEP** (ViaCEP).

## Stack

* **Servidor**: Payara 5 (Java EE 8)
* **View**: JSF 2.x
* **API**: JAX‑RS (endpoints `/api/...`)
* **Persistência**: JPA (EclipseLink), DataSource JNDI `jdbc/CopelDS`
* **Banco**: Oracle XE 21c (Docker)
* **Build**: Maven • **JDK 8**

## Estrutura em camadas

```
ordens-de-servico/
  src/main/java/br/com/copel/os/   ← código Java
    api/       # recursos REST (JAX‑RS)
    config/    # config (JAX‑RS, JSF)
    domain/    # entidades JPA
    service/   # regras/serviços (EJB/CDI)
  src/main/resources/META-INF/persistence.xml
  src/main/webapp/                  ← views JSF
    WEB-INF/web.xml
    index.xhtml
    os/
      list.xhtml
      form.xhtml
      view.xhtml
  pom.xml
```

## Pré‑requisitos

* Ubuntu 24.04 • **Java 8** (`openjdk-8-jdk`) • **Maven**
* **Docker** (Oracle XE) • **Payara 5** instalado

## Banco (resumo)

* Container: `gvenzl/oracle-xe:21-slim`
* JDBC: `jdbc:oracle:thin:@//localhost:1521/XEPDB1`
* Usuário: `COPEL / copel`

## DataSource (resumo)

* Pool: `CopelPool` • Recurso: `jdbc/CopelDS`
* Driver: `ojdbc8.jar` em `domains/domain1/lib/`

## Build & Deploy (resumo)

```bash
mvn clean package
asadmin deploy --force target/ordens-de-servico.war
# abrir: http://localhost:8080/ordens-de-servico/
```

## DDL (executar como COPEL)

* Sequências: `SEQ_CLIENTE`, `SEQ_ENDERECO`, `SEQ_TECNICO`, `SEQ_OS`
* Tabelas: `CLIENTE`, `ENDERECO`, `TECNICO`, `ORDEM_SERVICO`

## Pitch (o que mostrar)

1. Abrir OS → endereço por CEP.
2. Atribuir técnico → mudar status.
3. Filtrar OS por status/prioridade.
