package com.wxf

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.elasticsearch.spark.rdd.EsSpark

object LoadHiveData2Es {

  def main(args: Array[String]): Unit = {
    System.setProperty("java.security.krb5.conf", "D:/workspace/work2021/simba/config/krb5.conf")

    val configuration = new Configuration()


    UserGroupInformation.setConfiguration(configuration)

    UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "D:/workspace/work2021/simba/config/admin.keytab")

    val conf = new SparkConf()
    conf.set("es.nodes", "10.50.30.177")
    conf.set("es.port", "9500")
    conf.set("es.index.auto.create", "true")
    val session = SparkSession.builder().config(conf).appName("LoadHiveData2Es").master("local").enableHiveSupport().getOrCreate()
    val javaRDD = session.sql("select * from cmk.tablea;").toJavaRDD
    val rdd = javaRDD.map(x => {
      Map(
        "phone" -> x.getAs[String]("phone"),
        "dt" -> x.getAs[String]("dt"),
        "a1" -> x.getAs[String]("a1"),
        "a2" -> x.getAs[String]("a2"),
        "a3" -> x.getAs[String]("a3"),
        "a4" -> x.getAs[String]("a4"),
        "a5" -> x.getAs[String]("a5"),
        "a6" -> x.getAs[String]("a6"),
        "a7" -> x.getAs[String]("a7"),
        "a8" -> x.getAs[String]("a8"),
        "a9" -> x.getAs[String]("a9"),
        "a10" -> x.getAs[String]("a10"),
        "a11" -> x.getAs[String]("a11"),
        "a12" -> x.getAs[String]("a12"),
        "a13" -> x.getAs[String]("a13"),
        "a14" -> x.getAs[String]("a14"),
        "a15" -> x.getAs[String]("a15"),
        "a16" -> x.getAs[String]("a16"),
        "a17" -> x.getAs[String]("a17"),
        "a18" -> x.getAs[String]("a18"),
        "a19" -> x.getAs[String]("a19"),
        "a20" -> x.getAs[String]("a20")
      )
    })


    EsSpark.saveToEs(rdd, "tablea");

    session.stop()
  }

}
