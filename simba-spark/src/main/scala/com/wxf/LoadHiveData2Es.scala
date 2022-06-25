package com.wxf

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.sql.{Row, SparkSession}
import org.elasticsearch.spark.rdd.EsSpark

object LoadHiveData2Es {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.set("es.nodes", "10.50.30.177")
    conf.set("es.port", "9500")
    conf.set("es.index.auto.create", "true")
    val session: SparkSession = SparkSession.builder().config(conf).appName("LoadHiveData2Es").master("yarn").enableHiveSupport().getOrCreate()
    val javaRDD: JavaRDD[Row] = session.sql("insert into wms.tablea select * from cmk.tablea;").toJavaRDD
    val rdd: JavaRDD[(String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)] = javaRDD.map(x => {
      Tuple22(
        x.getAs[String]("phone"),
        x.getAs[String]("dt"),
        x.getAs[String]("a1"),
        x.getAs[String]("a2"),
        x.getAs[String]("a3"),
        x.getAs[String]("a4"),
        x.getAs[String]("a5"),
        x.getAs[String]("a6"),
        x.getAs[String]("a7"),
        x.getAs[String]("a8"),
        x.getAs[String]("a9"),
        x.getAs[String]("a10"),
        x.getAs[String]("a11"),
        x.getAs[String]("a12"),
        x.getAs[String]("a13"),
        x.getAs[String]("a14"),
        x.getAs[String]("a15"),
        x.getAs[String]("a16"),
        x.getAs[String]("a17"),
        x.getAs[String]("a18"),
        x.getAs[String]("a19"),
        x.getAs[String]("a20")
      )
    })

    EsSpark.saveToEs(rdd, "tablea");

    session.stop()
  }

}
