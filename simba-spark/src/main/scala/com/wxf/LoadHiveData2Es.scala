package com.wxf

import org.apache.spark.sql.SparkSession

object LoadHiveData2Es {

  def main(args: Array[String]): Unit = {
    val session: SparkSession = SparkSession.builder().appName("LoadHiveData2Es").master("yarn").enableHiveSupport().getOrCreate()
    session.sql("insert into wms.tablea select * from cmk.tablea;").show()
    session.stop()
  }

}
