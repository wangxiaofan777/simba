package com.wxf.elasticsearch;

import org.elasticsearch.xpack.sql.jdbc.EsDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author WangMaoSong
 * @date 2022/5/2 10:14
 */
public class Test {

    public static void main(String[] args) throws SQLException {
        EsDataSource esDataSource = new EsDataSource();
        esDataSource.setUrl("jdbc:es://10.50.30.177:9500");

        Connection connection = esDataSource.getConnection();
    }
}
