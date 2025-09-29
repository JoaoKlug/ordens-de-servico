# Sistema de Ordens de Serviço

Este é um projeto didático de um sistema para gerenciamento de Ordens de Serviço (OS), demonstrando uma arquitetura Java EE 8 completa com JSF, JPA, JAX-RS e integração com banco de dados Oracle.

## Tabela de Conteúdos

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Começando](#começando)
  - [Pré-requisitos](#pré-requisitos)
  - [Instalação e Configuração](#instalação-e-configuração)
- [Uso](#uso)
  - [Build e Deploy](#build-e-deploy)
  - [Acessando a Aplicação](#acessando-a-aplicação)
- [Banco de Dados](#banco-de-dados)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Solução de Problemas](#solução-de-problemas)
- [Comandos Úteis do Docker](#comandos-úteis-do-docker)

## Sobre o Projeto

O sistema permite:
- Abrir, atribuir e concluir Ordens de Serviço.
- Listar e filtrar OS por status e prioridade.
- Preenchimento automático de endereço a partir do CEP (via API ViaCEP).

A aplicação é projetada para rodar em um servidor WildFly 26 e utiliza um banco de dados Oracle XE 21c, ambos orquestrados com Docker Compose para facilitar a configuração do ambiente de desenvolvimento.

## Tecnologias Utilizadas

- **Backend**: Java EE 8
  - **Servidor de Aplicação**: WildFly 26.1.3.Final
  - **Persistência**: JPA 2.2 (Hibernate)
  - **View**: JSF 2.3 (com PrimeFaces)
  - **APIs REST**: JAX-RS 2.1
- **Banco de Dados**: Oracle Database XE 21c
- **Build & Dependências**: Apache Maven
- **Ambiente**: Docker & Docker Compose
- **JDK**: Java 8

> **Nota**: As APIs do Java EE (JSF, JPA, JAX-RS) são fornecidas pelo WildFly. Portanto, no `pom.xml`, elas são declaradas com `<scope>provided</scope>` para evitar que sejam empacotadas no arquivo `.war` final.

## Começando

Siga estas instruções para configurar e rodar o projeto em seu ambiente local.

### Pré-requisitos

- **Java Development Kit (JDK) 8**:
  ```bash
  # Exemplo no Ubuntu
  sudo apt install openjdk-8-jdk
  ```
- **Apache Maven**:
  ```bash
  # Exemplo no Ubuntu
  sudo apt install maven
  ```
- **Docker e Docker Compose**:
  - [Instruções de Instalação do Docker Engine](https://docs.docker.com/engine/install/)
  - [Instruções de Instalação do Docker Compose](https://docs.docker.com/compose/install/)

Verifique suas instalações:
```bash
java -version
mvn -v
docker --version
docker compose version
```

### Instalação e Configuração

1.  **Clone o repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd ordens-de-servico
    ```

2.  **Baixe o Driver JDBC da Oracle:**
    O WildFly precisa do driver JDBC para se comunicar com o Oracle. Crie uma pasta `extras/` e baixe o `ojdbc8.jar`.
    ```bash
    mkdir -p extras
    curl -o extras/ojdbc8.jar https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/23.5.0.24.07/ojdbc8-23.5.0.24.07.jar
    ```
    > **Importante**: Adicione `extras/ojdbc8.jar` ao seu arquivo `.gitignore` para não versionar o binário do driver.

3.  **Inicie os contêineres Docker:**
    Este comando irá construir a imagem customizada do WildFly (instalando o driver Oracle e o DataSource) e iniciar o serviço do banco de dados.
    ```bash
    docker compose up -d --build
    ```
    Aguarde alguns minutos para que o banco de dados Oracle seja inicializado completamente. Você pode acompanhar os logs com `docker compose logs -f oracle`.

## Uso

### Build e Deploy

Com os contêineres rodando, você pode compilar o projeto e fazer o deploy no WildFly usando o Maven.

```bash
mvn -DskipTests=true clean package wildfly:deploy \
  -Dwildfly.user=**** -Dwildfly.pass=****
```
> **Dica**: Para evitar digitar o usuário e a senha, configure um servidor no seu arquivo `~/.m2/settings.xml`.

### Acessando a Aplicação

-   **Aplicação Web**: [http://localhost:8080/ordens-de-servico](http://localhost:8080/ordens-de-servico)
-   **Console de Administração do WildFly**: [http://localhost:9990](http://localhost:9990)
    -   **Usuário**: `****`
    -   **Senha**: `******`

## Banco de Dados

-   **Serviço**: `oracle` (acessível por este hostname dentro da rede do Docker)
-   **Porta**: `1521`
-   **Service Name**: `XEPDB1`
-   **Usuário da Aplicação**: `COPEL`
-   **Senha**: `copel`

Para verificar a conexão com o DataSource configurado no WildFly:
```bash
docker exec -it $(docker compose ps -q wildfly) /opt/wildfly/bin/jboss-cli.sh --connect \
"/subsystem=datasources/data-source=CopelDS:test-connection-in-pool"
```
O resultado esperado é `{"outcome" => "success"}`.

## Estrutura do Projeto

```
ordens-de-servico/
├── docker-compose.yml      # Orquestra os contêineres do WildFly e Oracle.
├── Dockerfile              # Define a imagem customizada do WildFly.
├── entrypoint.sh           # Script que configura o DataSource no WildFly.
├── pom.xml                 # Arquivo de configuração do Maven.
├── README.md               # Este arquivo.
└── src/
    └── main/
        ├── java/br/com/copel/os/
        │   ├── api/        # Endpoints REST (JAX-RS).
        │   ├── config/     # Configurações da aplicação (JSF, JAX-RS).
        │   ├── domain/     # Entidades JPA (Ex: Cliente, OrdemServico).
        │   ├── service/    # Lógica de negócio (EJBs/CDI Beans).
        │   └── view/       # Managed Beans do JSF.
        ├── resources/
        │   ├── import.sql  # Script para popular o banco de dados inicial.
        │   └── META-INF/
        │       └── persistence.xml # Configuração da unidade de persistência JPA.
        └── webapp/
            ├── os/         # Páginas XHTML da funcionalidade de OS.
            ├── index.xhtml # Página inicial.
            └── WEB-INF/
                └── web.xml # Deployment descriptor.
```

## Solução de Problemas

-   **WildFly não conecta no Oracle**:
    -   Verifique se o contêiner `oracle` está rodando (`docker compose ps`).
    -   Confira os logs do WildFly (`docker compose logs -f wildfly`) para erros de conexão.
    -   Garanta que o arquivo `extras/ojdbc8.jar` existe antes de dar o `build` no compose.

-   **Portas Ocupadas (8080, 9990, 1521)**:
    -   Pare qualquer outro serviço que esteja utilizando estas portas ou altere o mapeamento no `docker-compose.yml`.

## Comandos Úteis do Docker

```bash
# Iniciar os serviços em background
docker compose up -d --build

# Parar os serviços
docker compose down

# Visualizar logs em tempo real
docker compose logs -f wildfly
docker compose logs -f oracle

# Listar contêineres em execução
docker compose ps

# Forçar a recriação da imagem do WildFly
docker compose up -d --build wildfly
```