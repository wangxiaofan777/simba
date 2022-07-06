package com.wxf;

import org.apache.hadoop.security.UserGroupInformation;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

import static org.apache.spark.sql.functions.col;

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
//        runBasicDataFrameExample(spark);
//        runDatasetCreationExample(spark);
        runInferSchemaExample(spark);

        spark.stop();
    }

    private static void runBasicDataFrameExample(SparkSession spark) {
        /*
        org.apache.spark.sql.execution.datasources.v2.json.JsonDataSourceV2
        org.apache.spark.sql.execution.datasources.json.JsonFileFormat
         */
        Dataset<Row> df = spark.read().json("D:\\workspace\\work2021\\simba\\simba-spark\\src\\main\\resources\\people.json");

        // 打印前几行数据
        df.show();

        // 打印Schema信息
        df.printSchema();

        // 查询name字段
        df.select("name").show();

        // 对列进行操作
        df.select(col("name"), col("age").plus(1)).show();

        // 过滤
        df.filter(col("age").gt(21)).show();

        // 注册一个视图
        df.createOrReplaceTempView("people");

        // 视图中查询数据
        Dataset<Row> sqlDF = spark.sql("select * from people");
        sqlDF.show();

        // 全局视图可以使用全局视图别名
        spark.sql("select * from global_temp.people").show();
    }


    private static void runDatasetCreationExample(SparkSession spark) {

        // bean 对象
        Person person = new Person();
        person.setName("wms");
        person.setAge(32L);

        Encoder<Person> personEncoder = Encoders.bean(Person.class);
        Dataset<Person> personDataset = spark.createDataset(
                Collections.singletonList(person),
                personEncoder
        );

        personDataset.show();

        // 基本数据类型
        Encoder<Long> longEncoder = Encoders.LONG();
        Dataset<Long> primitiveDS = spark.createDataset(
                Arrays.asList(1L, 2L, 3L),
                longEncoder
        );

        Dataset<Long> transferDS = primitiveDS.map((MapFunction<Long, Long>) value -> value + 1, longEncoder);
        transferDS.collect();

        // create a dataframe as bean
        Dataset<Person> peopleDS = spark.read().json("D:\\workspace\\work2021\\simba\\simba-spark\\src\\main\\resources\\people.json").as(personEncoder);
        peopleDS.show();

    }

    private static void runInferSchemaExample(SparkSession spark) {
        JavaRDD<Person> personRDD = spark.read().textFile("D:\\workspace\\work2021\\simba\\simba-spark\\src\\main\\resources\\people.txt")
                .javaRDD()
                .map(line -> {
                    String[] parts = line.split(",");
                    Person person = new Person();
                    person.setName(parts[0]);
                    person.setAge(Long.parseLong(parts[1].trim()));
                    return person;
                });

        Dataset<Row> peopleDF = spark.createDataFrame(personRDD, Person.class);
        peopleDF.createOrReplaceTempView("people");
        Dataset<Row> teenagersDF = spark.sql("select * from people where age between 19 and 19");
        Encoder<String> stringEncoder = Encoders.STRING();
        Dataset<String> teenagerNamesByIndexDF = teenagersDF.map((MapFunction<Row, String>) row -> row.getString(1), stringEncoder);
        teenagerNamesByIndexDF.show();

        Dataset<String> teenagerNamesByFieldDF = teenagersDF.map(new MapFunction<Row, String>() {
            @Override
            public String call(Row row) throws Exception {
                return row.getAs("name");
            }
        }, stringEncoder);

        teenagerNamesByFieldDF.show();

    }


    public static class Person implements Serializable {

        private String name;

        private Long age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getAge() {
            return age;
        }

        public void setAge(Long age) {
            this.age = age;
        }
    }
}
