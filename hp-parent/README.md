项目名称：HP惠普项目222222
架构选型：微服务架构（分布式 + 集群）
技术选型： Spring Boot + Spring Cloud + Mybatis + Redis + RabbitMQ + MySQL + (MyCat) + MongoDB + Nginx + (Docker)  
页面交互：Vue 2.x + Element + Bootstrap + HTML5 + CSS3
 
      参考：
  Spring Cloud         Spring Boot
  Finchley            兼容Spring Boot 2.0.x,不兼容Spring Boot 1.5.x
  Dalston和Edgware     兼容Spring Boot 1.5.x, 不兼容Spring Boot 2.0.x
  Camden              兼容Spring Boot 1.4.x， 也兼容Spring Boot 1.5.x   采用本技术
  Brixton             兼容Spring Boot 1.3.x,也兼容Spring Boot 1.4.x
  Angel               兼容 Spring Boot 1.2.x

项目约定： 
     前端：UI，HTML，CSS，JS等。
     后端：JAVA，MySQl等。
     前台：网站前台，是面向网站访问用户的，通俗的说也就是给访问网站的人看的内容和页面。包含app，代理人(微信公众号)，公司网站。
     后台：网站后台，有时也称为网站管理后台，是指用于管理网站前台的一系列操作，如：产品、企业信息的增加、更新、删除等。

环境约定：
   开发环境：也称本地环境，DEV环境，是指开发人员在自己的电脑上的环境。 指公司内网。  使用测试环境的数据库。
 SIT环境：也称测试环境，test环境，是指公司内部供测试人员用的环境。 指公司内网。      使用测试环境的数据库。
 uat环境：(User Acceptance Test),用户接受度测试，即验收测试，所以UAT环境主要是用来作为客户体验的环境。 一般用公司外网。 使用uat环境的数据库。
   生产环境： 也称Product环境，PAT环境， 是指真实用户访问的环境。 是在公司外网。 使用生产环境的数据库。
     
工程简介：
hp-bg-api: 后台业务服务
hp-wx-api: （微信端）前台业务服务
hp-bg-staticresource: 后台页面
hp-common-ms：公共服务
hp-common-redis : redis 
hp-cooomon-utils：工具类
hp-zuul :api  网关



注意：
      工程中配置文件都采用yml文件， 最大的好处是 树状结构，便于管理。