package com.wxf

import com.alibaba.fastjson2.JSONObject
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Encoders, Row, SparkSession}

/**
 *
 * @author WangMaoSong
 * @since 2023/5/19 17-46:01
 * @version 1.1.0
 */
object LoadCsvToMySql {
  // 物料
  val _MATERIAL = "MATERIAL"

  // 分派标识
  val _ASSIGNEE = "ASSIGNEE"
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName("LoadCsvToMySql")
      .getOrCreate()


    val df = spark.read
      .option("header", "true")
      .option("multiLine", "true")
      .option("encoding", "utf-8")
      //        .option("sep", ",")
      .csv("file:///D:\\tmp\\records\\f508eda4eb9740f6a31f8af7dd87b49b.csv")


    val df1 = df.map((row: Row) => row.json, encoder = Encoders.STRING)
      .toDF(_MATERIAL.split(","): _*)
      .withColumn(_ASSIGNEE, lit("1"))

    df1.show(false)

    spark.stop
  }

}
