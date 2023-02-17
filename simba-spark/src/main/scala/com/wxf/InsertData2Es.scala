package com.wxf

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.elasticsearch.spark.rdd.EsSpark

object InsertData2Es {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.set("es.nodes", "10.50.30.177")
    conf.set("es.port", "9500")
    conf.set("es.index.auto.create", "true")

    val session: SparkSession = SparkSession.builder().config(conf).appName("LoadHiveData2Es").master("yarn").enableHiveSupport().getOrCreate()


    val sc = session.sparkContext
    val value = sc.makeRDD(
      Seq(Person("zhangsan", 1), Person("lisi", 2), Person("wangwu", 3))
    )

    EsSpark.saveToEs(value, "test")

    session.stop()
  }

  case class Person(name: String, age: Int)

}
