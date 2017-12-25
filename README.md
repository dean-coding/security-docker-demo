# security-docker-demo
springboot ，spring security, jpa, thymeleaf,docker,docker-compose实现的web应用从开发到部署的 demo
security权限控制（实现不同的角色不同的登录页面，不同的成功处理）

# localhost:8080/consumerlogin 普通用户登陆
# localhost:8080/managerlogin 管理员用户登陆

# docker部署的配置，参见说明文档： https://github.com/lingqibaobei/springboot-func

# 部署流程：

## mvn clean package -Demaven.test.skip=true docker:build
## cd ..指定项目目录
## docker images //查看镜像
## docker-compose up -d 业务名称（docker-compose.yml中配置的名称,-d后台运行）
## docker-compose logs 业务名称 //查询启动日志
