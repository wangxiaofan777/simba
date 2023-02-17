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