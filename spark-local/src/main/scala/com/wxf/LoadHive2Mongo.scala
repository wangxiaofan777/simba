package com.wxf

import org.apache.spark.sql.SparkSession

object LoadHive2Mongo {

  def main(args: Array[String]): Unit = {

    val tableName = "ads.variable_extraction_1"
    val spark = SparkSession.builder()
      .master("local")
      .appName("MongoSparkConnectorIntro")
      .config("spark.mongodb.read.connection.uri", "mongodb://admin:admin@10.50.17.191:29000/wms.wms?authSource=admin")
      .config("spark.mongodb.write.connection.uri", "mongodb://admin:admin@10.50.17.191:29000/wms.wms?authSource=admin")
      .enableHiveSupport()
      .getOrCreate()


    val df = spark.sql(s"select * from cmk.tablea")

    df.show()


    df.write.format("mongodb").mode("overwrite").save()

    spark.stop()
  }

}
