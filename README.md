# kotlin-forum-example
Exemplo de uma api de forum com autenticação por jwt em Kotlin e Spring Boot 3.3.x

## Comandos para rodar o docker

```shell
docker build -t forum -f Dockerfile .
docker run -p 3080:8080 forum
```

## Comandos heroku

```shell
heroku login
heroku create
heroku git:remote -a <nome-do-app>
heroku container:login
heroku container:push web
heroku container:release web
```

## Configuração do Dockerfile para o heroku

```dockerfile
ENTRYPOINT ["java", "$JAVA_OPTS -XX:+UseContainerSupport", "-Xnx300m -Xss512k -XX:CICompilerCount=2", "-Dserver.port=$PORT", "-Dspring.profiles.active=prod", "-jar", "forum.jar"]
```

## Configuração de banco de dados Mysql

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version>
</dependency>
```

```yaml
spring:
    datasource:
        driverclassName: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/forum
        username: root
        password: root
    jpa:
      properties:
        hibernate:
          show sql: true
          format sql: true
```

```shell
docker pull mysql:8.0.28
docker run -d -p 3306:3306-name mysql-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_PASSWORD-root mysql:8.0.28
docker exec -it mysql-container bash
mysql -u root -p
create database forum
```

## Configuração do Redis

### Subir com docker

```shell
docker pull redis:latest
docker run -d -p 6379:6379 --name redis --restart always redis:latest
```

### Verificar funcionamento do redis

```shell
docker exec -it redis sh
redis-cli
monitor
```
