package com.wxf

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}

object DfJoinTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName("DfJoinTest")
      .getOrCreate()

    val persons = List(
      ("wxf", "male", 10),
      ("qfb", "female", 20),
      ("jlx", "male", 28)
    )

    StructType(Seq(
      StructField("name", DataTypes.StringType),
      StructField("sex", DataTypes.StringType),
      StructField("age", DataTypes.IntegerType)
    ))

    val rdd = spark.createDataFrame(persons)
    val df = rdd.toDF("name", "sex", "age")


    val df1 = rdd.toDF("name", "sex", "age")

    df.join(df1, Seq("name", "sex", "age"), "left_outer").show()


    spark.close


  }
}
