####1.服务端部署方式：采用docker容器化部署

```
        网关服务:pt-gateway-service

        认证中心服务:pt-auth-center
        
        后台管理api服务:pt-admin-api

        小程序api服务:pt-wx-api
```

####2.环境容器部署：

```
nacos注册中心：
    nacos                0.0.0.0:8848->8848
数据库：
	mysql:8.0.20		0.0.0.0:3306->3306
        用户名：root
        密码: bmy123!@#
redis缓存数据库：
        redis				0.0.0.0:6379->6379
        密码：bmy123!@#
minio对象存储：
        minio		 		0.0.0.0:9000->9000
        用户名：admin
        密码：admin123456
    开发环境需要自己配置
```

####3.接口文档

```
swagger接口文档：

    本地访问：localhost:9001/swagger-ui.html
```