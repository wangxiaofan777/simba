package com.wxf;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangMaoSong
 * @date 2022/7/29 10:06
 */
public class JavaSparkDatasource {

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                .appName("JavaSparkDatasource")
                .master("local")
                .getOrCreate();

        Dataset<Row> df = getDf(spark, "jdbc",
                "jdbc:mysql://10.50.30.179:3306/data_query_service?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true&useSSL=false&allowPublicKeyRetrieval=true",
                "com.mysql.cj.jdbc.Driver",
                "root",
                "lygr@0907",
                "data_query_table_columns"

        );

        String tableName = "adl_ocm_msg_common_credit_card_daily_assembly_d1";

        List<String> columnList = getTableName(df, tableName);

        String sql = createSql("adl", tableName, columnList);
        System.out.println(sql);


        spark.stop();
    }

    private static String createSql(String database, String tableName, List<String> columnList) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        columnList.forEach(s -> {
            sb.append(s).append(" ").append(", ");
        });
        sb.append(database).append(".").append(tableName);
        return sb.toString();
    }

    /**
     * 根据表名获取列
     *
     * @param df        DF
     * @param tableName 表名
     * @return 列
     */
    private static List<String> getTableName(Dataset<Row> df, String tableName) {
        return df.select("variable", "table_name")
                .where("table_name = '" + tableName + "'")
                .collectAsList()
                .stream()
                .map(row -> row.get(0))
                .map(String::valueOf)
                .collect(Collectors.toList());
    }


    /**
     * 读取MySQL获取表DF
     *
     * @param spark     spark
     * @param format    格式
     * @param url       URL
     * @param driver    驱动
     * @param username  用户名
     * @param password  密码
     * @param tableName 表名
     * @return 表DF
     */
    public static Dataset<Row> getDf(SparkSession spark,
                                     String format,
                                     String url,
                                     String driver,
                                     String username,
                                     String password,
                                     String tableName) {
        return spark.read()
                .format(format)
                .option("url", url)
                .option("driver", driver)
                .option("user", username)
                .option("password", password)
                .option("dbtable", tableName)
                .load();
    }
}
