package org.xdl.datasync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@SpringBootApplication
public class DataSyncApplication {

    public static void main(String[] args) throws SQLException {
        //SpringApplication.run(DataSyncApplication.class, args);

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/data_sync1?serverTimezone=GMT");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        jdbcTemplate.setDataSource(dataSource);

        /*SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from sys_user");
        SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            System.out.println("字段：name-" + metaData.getColumnName(i) + ", type-" + metaData.getColumnTypeName(i) + ", 精度-" + metaData.getPrecision(i) + ", key-" + metaData.isSigned(i));
        }*/

        //region 获取库信息
        List<String> tables = jdbcTemplate.queryForList("show tables", String.class);
        for (String table:tables) {
            System.out.println("表名：" + table);
            //region 获取表结构信息
            ResultSetMetaData resultSetMetaData = jdbcTemplate.query("select * from " + table, ResultSet::getMetaData);
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                System.out.println("sys_user表字段详情：name-" + resultSetMetaData.getColumnName(i) + ", "
                        + "type-" + resultSetMetaData.getColumnTypeName(i) + ", "
                        + "isNullAble-" + resultSetMetaData.isNullable(i));
            }
            //endregion
        }
        //endregion
    }
}
