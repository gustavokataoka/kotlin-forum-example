# kotlin-forum-example
Exemplo de uma api de forum com autenticação por jwt em Kotlin e Spring Boot 3.3.x

## Comandos para rodar o docker
```
# docker build -t forum -f Dockerfile .
# docker run -p 3080:8080 forum
```

## Comandos heroku
```
# heroku login
# heroku create
# heroku git:remote -a <nome-do-app>
# heroku container:login
# heroku container:push web
# heroku container:release web
```

## Configuração do Dockerfile para o heroku
```
# ENTRYPOINT ["java", "$JAVA_OPTS -XX:+UseContainerSupport", "-Xnx300m -Xss512k -XX:CICompilerCount=2", "-Dserver.port=$PORT", "-Dspring.profiles.active=prod", "-jar", "forum.jar"]
```