package com.wxf

import org.apache.spark.sql.SparkSession

/**
 * Spark 程序入口
 *
 * @author WangMaoSong
 * @date 2022/6/21 17:47
 */
object Main {

  def main(args: Array[String]): Unit = {
//    System.setProperty("java.security.krb5.conf", "krb5.conf")

//    UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "src/main/resources/root.keytab");

    val session = SparkSession.builder().master("yarn").appName("Main").enableHiveSupport().getOrCreate()
    session.sql("show databases").show()

    session.stop()
  }

}
