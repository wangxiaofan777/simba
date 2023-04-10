### 1. 代码中不配置写入Keytab时提交任务

```shell
spark3-submit --class com.wxf.Main \
              --master yarn  \
              --deploy-mode cluster  \
              --principal admin/admin@HADOOP.COM \
              --keytab /root/admin.keytab \
              /home/wms/simba-spark-1.0-SNAPSHOT.jar
```


```shell
spark3-submit --class com.wxf.LoadHiveData2Es \
              --master yarn  \
              --deploy-mode cluster  \
              --principal admin/admin@HADOOP.COM \
              --keytab  /root/admin.keytab \
              --name LoadHiveData2Es \
              /home/wms/simba-spark-1.0-SNAPSHOT.jar
```



```shell
spark3-submit --class com.wxf.LoadHive2Mongo \
              --master yarn  \
              --deploy-mode cluster  \
              --principal admin/admin@HADOOP.COM \
              --keytab  /root/admin.keytab \
              --name LoadHive2Mongo \
              --driver-memory 4g \
              --executor-memory 4g \
              --driver-cores 2 \
              --executor-cores 4 \
              --queue root.users.admin \
              --num-executors 1 \
              /home/wms/simba-spark-1.0-SNAPSHOT.jar ads.variable_extraction_1
              
```