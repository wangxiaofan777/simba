package com.wxf

import org.apache.spark.sql.SparkSession

/**
 *
 * @author WangMaoSong
 * @date 2022/6/24 17:58
 */
object ParallelTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("test").getOrCreate()
    val sc = spark.sparkContext
    val data = Array(1, 2, 3, 4, 5)
    val rdd = sc.parallelize(data)
    rdd.foreach(println)

  }


}
