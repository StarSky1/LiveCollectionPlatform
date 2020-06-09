# LiveCollectionPlatform
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu) 
[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)

用Java编写的一个直播平台聚合网站，收集各大直播平台热门有趣的直播，享受直播的乐趣吧！
## 演示地址：[http://live.starsky1.cn](http://live.starsky1.cn)
## 欢迎大家分享意见和代码。
## 最新版在dev分支，开发或贡献代码请加入dev分支。

## mysql数据库 配置
liveplatform数据库编码请使用 utf8mb4

my.ini
```
在文件最后一行加入
[mysqld]

character-set-client-handshake = FALSE
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci

[mysql]

default-character-set = utf8mb4

[client]

default-character-set = utf8mb4

```
另外，在jdbc的连接地址后面，去除characterEncoding=UTF-8参数

具体原因请查看：[JDBC对Mysql utf8mb4字符集的处理](https://www.cnblogs.com/liuge36/p/9882785.html)
## 图片介绍

### 所有直播

![所有直播](pictures/1.png)

### 平台和分类

![平台和分类](pictures/2.png)

### 个人注册

![个人注册](pictures/3.png)

### 我的关注

![我的关注](pictures/6.png)
![](pictures/7.png)

### 其他

![其他](pictures/8.png)

## LICENSE

MIT Anti996

欢迎 Star 和 Fork ~

如果你有什么问题请提 Issue
