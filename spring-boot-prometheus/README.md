# 接入Prometheus 
教程https://www.yuque.com/leifengyang/springboot3/wsx0br0dalot1pqn
# 还未完成使用切面监控接口

spring boot metrics监控接口 http://localhost:9999/actuator

```shell
#请根据自己的情况修改映射目录
#安装prometheus:时序数据库
docker run -p 9090:9090 -d \
-v ./prometheus:/etc/prometheus \
prom/prometheus
#安装grafana；默认账号密码 admin:admin
docker run -d --name=grafana -p 3000:3000 grafana/grafana
```

