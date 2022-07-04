package com.wxf;

import org.apache.hadoop.security.UserGroupInformation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

/**
 * Hello world!
 */
public class JavaSparkSQL {
    public static void main(String[] args) throws IOException {
        UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "conf/root.keytab");
        SparkSession spark = SparkSession.builder()
                .appName(JavaSparkSQL.class.getName())
                .master("local")
                .getOrCreate();
        runBasicDataFrameExample(spark);

        spark.stop();
    }

    private static void runBasicDataFrameExample(SparkSession spark) {
        /*
        org.apache.spark.sql.execution.datasources.v2.json.JsonDataSourceV2
        org.apache.spark.sql.execution.datasources.json.JsonFileFormat
         */
        Dataset<Row> df = spark.read()
                .format("org.apache.spark.sql.execution.datasources.json.JsonFileFormat")
                .load("D:\\workspace\\work2021\\simba\\simba-spark\\src\\main\\resources\\people.json");

        df.show();
    }
}
