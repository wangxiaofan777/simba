package com.wxf

import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.sql.SparkSession

import scala.util.Random

/**
 * groupBy 测试
 *
 * @author WangMaoSong
 * @date 2022/8/3 17:17
 */
object GroupByTest {

  def main(args: Array[String]): Unit = {
    System.setProperty("java.security.krb5.conf", "D:/workspace/work2021/simba/config/krb5.conf")
    UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "D:/workspace/work2021/simba/config/admin.keytab")

    val spark = SparkSession.builder().appName("GroupBy Test").master("local").getOrCreate()

    val numMappers = 3
    val numKVPair = 4
    val valueSize = 1000
    val numReducers = 2
    val input = 0 until numMappers

    val pairs1 = spark.sparkContext.parallelize(input, numMappers)
      .flatMap(_ => {
        val ranGen = new Random
        val array = new Array[(Int, Array[Byte])](numKVPair)
        for (i <- 0 until numKVPair) {
          val byteArray = new Array[Byte](valueSize)
          ranGen.nextBytes(byteArray)
          array(i) = (ranGen.nextInt(numKVPair), byteArray)
        }
        array
      }).cache()

    println(pairs1.count())
    println(pairs1.toDebugString)
    val results = pairs1.groupByKey(numReducers)
    println(results)
    println(results.count())
    println(results.toDebugString)
  }


}
