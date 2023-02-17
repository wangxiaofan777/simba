package com.wxf

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author WangMaoSong
 * @date 2023/1/3 17:04
 */
object MovieUserAnalyzer {

  private val basePath: String = "data/"

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("MovieUserAnalyzer")
    val sc = new SparkContext(sparkConf)

    // 用户ID::电影ID::评分数据::时间戳
    val ratingsRDD: RDD[String] = sc.textFile(basePath + "ratings.dat")

    // 用户ID::性别::年龄::职业::邮编代码
    val usersRDD: RDD[String] = sc.textFile(basePath + "users.dat")

    // 电影ID::电影名称::
    val moviesRDD: RDD[String] = sc.textFile(basePath + "movies.dat")

    val ratings: RDD[(String, String, String)] = ratingsRDD
      .map((_: String).split("::"))
      .map((x: Array[String]) => (x(0), x(1), x(2)))
      .cache()

    println("所有电影中评分最高的电影：")
    ratings.map((x: (String, String, String)) => (x._2, (x._3.toDouble, 1)))
      .reduceByKey((x: (Double, Int), y: (Double, Int)) => (x._1 + y._1, x._2 + y._2))
      .map((x: (String, (Double, Int))) => (x._2._1.toDouble / x._2._2, x._1))
      .sortByKey(ascending = false)
      .take(10)
      .foreach(println)

    println("所有电影中粉丝或者观看人数最多的电影：")
    ratings.map((x: (String, String, String)) => (x._2, 1))
      .reduceByKey((_: Int) + (_: Int))
      .map((x: (String, Int)) => (x._2, x._1))
      .sortByKey(ascending = false)
      .map((x: (Int, String)) => (x._2, x._1))
      .take(10)
      .foreach(println)


    val genderRatings: RDD[(String, ((String, String, String), String))] = ratings.map((x: (String, String, String)) => (x._1, (x._1, x._2, x._3))).join(
      usersRDD.map((_: String).split("::")).map((x: Array[String]) => (x(0), x(1)))
    ).cache

    val maleFilteredRatings: RDD[(String, String, String)] = genderRatings.filter((x: (String, ((String, String, String), String))) => x._2._2.eq("M")).map((x: (String, ((String, String, String), String))) => x._2._1)
    val feMaleFilteredRatings: RDD[(String, String, String)] = genderRatings.filter((x: (String, ((String, String, String), String))) => x._2._2.eq("F")).map((x: (String, ((String, String, String), String))) => x._2._1)

    sc.stop
  }

}
