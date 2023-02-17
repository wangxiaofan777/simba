package com.wxf.table;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.datagen.table.DataGenConnectorOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.FormatDescriptor;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableDescriptor;
import org.apache.flink.table.api.TablePipeline;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import static org.apache.flink.table.api.Expressions.$;

/**
 * Table测试
 *
 * @author WangMaoSong
 * @date 2023/1/5 17:53
 */
public class TableTest {

    private static final StreamTableEnvironment tEnv;
    private static final StreamExecutionEnvironment env;

    static {
        env = initStreamTableEnvironment();
        tEnv = init();
    }

    public static void main(String[] args) {
        // 创建表
//        createTable();

        // 创建视图
//        createTemporaryView();

        // 查询表
//        queryFromTable();

        // SQL 查询
//        sqlQuery();

        // 表管道
//        tablePipeline();

        // 执行计划
        explain();
    }

    /**
     * 初始化
     *
     * @return TableEnv
     */
    public static StreamExecutionEnvironment initStreamTableEnvironment() {
        return StreamExecutionEnvironment.getExecutionEnvironment();
    }

    public static StreamTableEnvironment init() {
        return StreamTableEnvironment.create(initStreamTableEnvironment());
    }


    /**
     * 创建表
     */
    public static void createTable() {
        TableDescriptor sourceDescriptor = TableDescriptor.forConnector("datagen")
                .schema(Schema.newBuilder()
                        .column("f0", DataTypes.STRING())
                        .build())
                .option(DataGenConnectorOptions.ROWS_PER_SECOND, 100L)
                .build();

        // 永久表
        tEnv.createTable("SourceTableA", sourceDescriptor);
        // 临时表
        tEnv.createTemporaryTable("SourceTableB", sourceDescriptor);


        tEnv.executeSql("CREATE [TEMPORARY] TABLE TABLE_NAME (...) WITH (....)");
    }

    /**
     * 创建临时视图
     */
    public static void createTemporaryView() {
        tEnv.useCatalog("custom_catalog");
        tEnv.useDatabase("custom_database");

        Table table = tEnv.from("custom_catalog.custom_database.custom_table");

        tEnv.createTemporaryView("example_view", table);
        tEnv.createTemporaryView("other_database.example_view", table);
        tEnv.createTemporaryView("other.view", table);
        tEnv.createTemporaryView("other_catalog.other_database.other_table", table);
    }


    /**
     * 查询Table
     */
    public static void queryFromTable() {
        Table orders = tEnv.from("Orders");

        Table revenue = orders.filter($("cCountry").isEqual("FRANCE"))
                .groupBy($("cID"), $("cName"))
                .select($("cId"), $("cName"), $("revenue").sum().as("revSum"));

        revenue.execute();
    }


    /**
     * SQL查询
     */
    public static void sqlQuery() {
        Table table = tEnv.sqlQuery("SELECT cID, cNAME, SUM(revenue) AS revSum FROM Orders WHERE cCOUNTRY = 'FRANCE' GROUP BY cID, cNAME");

        table.execute();
    }

    /**
     * 表pipeline
     */
    public static void tablePipeline() {
        Schema schema = Schema.newBuilder()
                .column("a", DataTypes.INT())
                .column("b", DataTypes.STRING())
                .column("c", DataTypes.BIGINT())
                .build();

        TableDescriptor tableDescriptor = TableDescriptor.forConnector("filesystem")
                .schema(schema)
                .option("path", "file:///d:/tmp/1.csv")
                .format(FormatDescriptor.forFormat("csv")
                        .option("field-delimiter", "|").build())
                .build();
        tEnv.createTemporaryTable("CsvSinkTable", tableDescriptor);

        //
        tEnv.createTable("CsvSinkTable", tableDescriptor);
        Table result = tEnv.from("CsvSinkTable");

        result.printSchema();

        TablePipeline pipeline = result.insertInto("CsvSinkTable");

        pipeline.printExplain();

        pipeline.execute();
    }

    /**
     * 执行计划
     */
    public static void explain() {
        DataStream<Tuple2<Integer, String>> stream1 = env.fromElements(new Tuple2<>(1, "hello"));
        DataStream<Tuple2<Integer, String>> stream2 = env.fromElements(new Tuple2<>(1, "hello"));

        Table table1 = tEnv.fromDataStream(stream1, $("count"), $("word"));
        Table table2 = tEnv.fromDataStream(stream2, $("count"), $("word"));

        Table table = table1.where($("word").like("F%"))
                .unionAll(table2);

        System.out.println(table.explain());

    }
}
