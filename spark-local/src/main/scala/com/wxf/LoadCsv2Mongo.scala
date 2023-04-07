package com.wxf

import org.apache.spark.sql.SparkSession

object LoadCsv2Mongo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName("MongoSparkConnectorIntro")
      .config("spark.mongodb.read.connection.uri", "mongodb://admin:admin@10.50.17.191:29000/wms.wms?authSource=admin")
      .config("spark.mongodb.write.connection.uri", "mongodb://admin:admin@10.50.17.191:29000/wms.wms?authSource=admin")
      .getOrCreate()


    val df = spark.read
      // 制定编码
      .option("encoding", "gbk")
      //          推断数据类型
      .option("inferSchema", "true")
      //         可设置分隔符，默认，
      //.option("delimiter",",")
      //          设置空值
      .option("nullValue", "?")
      //          表示有表头，若没有则为false
      .option("header", true)
      .csv("D:/tmp/zyxj_2022081701_md5_market.csv")

    df.show()

    df.write.format("mongodb").mode("overwrite").save()

    spark.stop()
  }

}
