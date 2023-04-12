```shell
spark3-submit --class com.wxf.LoadHive2Mongo \
              --master yarn  \
              --deploy-mode cluster  \
              --principal admin/admin@HADOOP.COM \
              --keytab  /root/admin.keytab \
              --name LoadHive2Mongo \
              --driver-memory 1g \
              --executor-memory 1g \
              --driver-cores 1 \
              --executor-cores 1 \
              --queue root.users.admin \
              --num-executors 1 \
              --jars /home/wms/mongo-spark-connector_2.12-10.1.0.jar \
              /home/wms/spark-local-1.0-SNAPSHOT.jar
  
```