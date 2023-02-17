1. 下载

```shell

wget https://mirrors.tuna.tsinghua.edu.cn/apache/doris/1.2/1.2.1-rc01/apache-doris-fe-1.2.1-bin-x86_64.tar.xz --no-check-certificate
wget https://mirrors.tuna.tsinghua.edu.cn/apache/doris/1.2/1.2.1-rc01/apache-doris-be-1.2.1-bin-x86_64.tar.xz --no-check-certificate
wget https://mirrors.tuna.tsinghua.edu.cn/apache/doris/1.2/1.2.1-rc01/apache-doris-dependencies-1.2.1-bin-x86_64.tar.xz --no-check-certificate
```

2. 解压

```shell
tar -xvf apache-doris-fe-1.2.1-bin-x86_64.tar.xz
tar -xvf apache-doris-be-1.2.1-bin-x86_64.tar.xz
tar -xvf apache-doris-dependencies-1.2.1-bin-x86_64.tar.xz

```

3. 重命名

```shell
mv apache-doris-fe-1.2.1-bin-x86_64 doris-fe-1.2.1
mv apache-doris-be-1.2.1-bin-x86_64 doris-be-1.2.1
```

4. 配置

```properties
priority_networks=10.50.30.179/24
meta_dir=/home/wms/doris/doris-meta
```

5. 启动/停止

```shell
sh bin/start_fe.sh --daemon
sh bin/stop_fe.sh

```

6. 查看运行状态

```yaml
# 如果返回结果中带有 "msg":"success" 字样，则说明启动成功。
curl http://127.0.0.1:8030/api/bootstrap
```

7. 访问WEB

```shell
# 这里我们使用 Doris 内置的默认用户 root 进行登录，密码是空
http://10.50.30.179:8030/home

```

8. MySQL登录

```shell
mysql -uroot -P9030 -h127.0.0.1

```

9. 查看fe运行状态
    * 如果 IsMaster、Join 和 Alive 三列均为true，则表示节点正常。

```shell
MySQL [(none)]> show frontends\G;
*************************** 1. row ***************************
             Name: 10.50.30.179_9010_1672807703225
               IP: 10.50.30.179
      EditLogPort: 9010
         HttpPort: 8030
        QueryPort: 9030
          RpcPort: 9020
             Role: FOLLOWER
         IsMaster: true
        ClusterId: 1131709059
             Join: true
            Alive: true
ReplayedJournalId: 975
    LastHeartbeat: 2023-01-04 13:42:14
         IsHelper: true
           ErrMsg: 
          Version: doris-1.2.1-rc01-Unknown
 CurrentConnected: Yes
1 row in set (0.01 sec)

ERROR: No query specified
```

10. 配置be
    * 三台从节点都需要安装

```shell
priority_networks = 10.50.30.179/24
storage_root_path = /home/wms/doris/storage
```

11. 拷贝UDF包到be
    * 三台从节点都需要安装

```shell
cp java-udf-jar-with-dependencies.jar /home/wms/doris/doris-be-1.2.1/lib

```

12. 启动be

```shell
sysctl -w vm.max_map_count=2000000
sh bin/start_be.sh -daemon

```

13. 添加到集群

```shell
ALTER SYSTEM ADD BACKEND "10.50.30.177:9050"
ALTER SYSTEM ADD BACKEND "10.50.30.178:9050"
ALTER SYSTEM ADD BACKEND "10.50.30.179:9050"

```

14. 创建数据库

```shell
create database demo;
use demo;

CREATE TABLE IF NOT EXISTS demo.example_tbl
(
    `user_id` LARGEINT NOT NULL COMMENT "用户id",
    `date` DATE NOT NULL COMMENT "数据灌入日期时间",
    `city` VARCHAR(20) COMMENT "用户所在城市",
    `age` SMALLINT COMMENT "用户年龄",
    `sex` TINYINT COMMENT "用户性别",
    `last_visit_date` DATETIME REPLACE DEFAULT "1970-01-01 00:00:00" COMMENT "用户最后一次访问时间",
    `cost` BIGINT SUM DEFAULT "0" COMMENT "用户总消费",
    `max_dwell_time` INT MAX DEFAULT "0" COMMENT "用户最大停留时间",
    `min_dwell_time` INT MIN DEFAULT "99999" COMMENT "用户最小停留时间"
)
AGGREGATE KEY(`user_id`, `date`, `city`, `age`, `sex`)
DISTRIBUTED BY HASH(`user_id`) BUCKETS 1
PROPERTIES (
    "replication_allocation" = "tag.location.default: 1"
);
```

15. 导入测试数据
    * CSV格式

```shell
10000,2017-10-01,北京,20,0,2017-10-01 06:00:00,20,10,10
10000,2017-10-01,北京,20,0,2017-10-01 07:00:00,15,2,2
10001,2017-10-01,北京,30,1,2017-10-01 17:05:45,2,22,22
10002,2017-10-02,上海,20,1,2017-10-02 12:59:12,200,5,5
10003,2017-10-02,广州,32,0,2017-10-02 11:20:00,30,11,11
10004,2017-10-01,深圳,35,0,2017-10-01 10:00:15,100,3,3
10004,2017-10-03,深圳,35,0,2017-10-03 10:20:22,11,6,6
```

16. 导入
```shell
curl --location-trusted -u root: -T test.csv -H "column_separator:," http://127.0.0.1:8030/api/demo/example_tbl/_stream_load

```
17. 修改密码
```mysql
SET PASSWORD FOR 'root' = PASSWORD ('lygr@0907');
```
18. 