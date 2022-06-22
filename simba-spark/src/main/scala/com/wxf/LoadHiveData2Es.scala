package com.wxf

import org.apache.spark.api.java.JavaRDD
import org.apache.spark.sql.{Row, SQLContext, SparkSession}

object LoadHiveData2Es {

  def main(args: Array[String]): Unit = {
    val session: SparkSession = SparkSession.builder().appName("LoadHiveData2Es").master("yarn").enableHiveSupport().getOrCreate()
    val sc: SQLContext = session.sqlContext
    val rowRdd: JavaRDD[Row] = sc.sql("select * from cmk.tablea").toJavaRDD

    rowRdd.foreach(println)

    session.stop()

  }

}
