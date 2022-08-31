package com.wxf

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.sql.SparkSession

/**
 *
 * @author WangMaoSong
 * @date 2022/8/24 15:00
 */
object ReadParquet extends App {


  System.setProperty("java.security.krb5.conf", "D:/workspace/work2021/simba/config/krb5.conf")

  val configuration = new Configuration()


  UserGroupInformation.setConfiguration(configuration)

  UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "D:/workspace/work2021/simba/config/admin.keytab")
  val spark = SparkSession.builder().master("local").appName("ceshi").getOrCreate()
  spark.read.parquet("file:///C:/Users/oceanum/Desktop/data/part-00000-5859084c-78c1-4c19-87df-604c005e5d48.snappy.parquet").show()


  spark.stop()
}
