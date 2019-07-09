# demo


**打包**

```bash
mvn clean package  -Dmaven.test.skip=true
```

**启动**

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=8888 --spring.profiles.active=prod
```