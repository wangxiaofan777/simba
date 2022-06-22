package com.wxf

import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.sql.SparkSession

/**
 * Spark 程序入口
 *
 * @author WangMaoSong
 * @date 2022/6/21 17:47
 */
object Main {

  def main(args: Array[String]): Unit = {
    UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "conf/root.keytab")
    val session = SparkSession.builder().master("yarn").appName("Main").enableHiveSupport().getOrCreate()
    session.sql("show databases").show()

    session.stop()
  }

}
