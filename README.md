# mysql_server_proxy
mysql server proxy demo based on vertx

![image](http://o79vc15mq.bkt.clouddn.com/o_1aol5ovrjmtr1n7l1jc9hl1lfo9.png)

# Requirment
- Java8
- Vert.x 3

Maven is great to add dependency
```
<dependency>
  <groupId>io.vertx</groupId>
  <artifactId>vertx-core</artifactId>
  <version>3.3.2</version>
</dependency>
```

Class `MysqlProxyConnection` is to open or close connection

Class `MysqlProxyServerVerticle` is the proxy


