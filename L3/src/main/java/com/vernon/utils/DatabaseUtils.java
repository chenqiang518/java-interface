package com.vernon.utils;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;
import org.jooq.tools.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    Statement statement;
    Connection conn;
    // 初始化数据库信息
    public DatabaseUtils(String DB_URL, String USER, String PASS) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 问题: Operation not allowed for a result set of type ResultSet. TYPE_FORWARP_ONLY.
            // 解决方案: createStatement 添加配置 ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // 根据 sql 返回在单个字段数据库查询的信息
    public List<String> getResultStringListBySQL(String sql_query, String columnLabel){
        List<String> resList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(sql_query);
            while (resultSet.next()){
                resList.add(resultSet.getString(columnLabel));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    // 根据sql查询返回多个字段并转化为标准的json格式
    public String getResultSetBySQL(String sql) {
        // 格式转换将ResultSet 转换为 标准的json格式，方便断言时使用JsonPath获取对应信息。
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            // 获取所有
            ResultSetMetaData md = resultSet.getMetaData();
            int numCols = md.getColumnCount();
            // 获取所有的数据表列名
            List<String> colNames = new ArrayList<>();
            for (int i = 1; i <= numCols; i++) {
                String name = md.getColumnName(i);
                colNames.add(name);
            }
            // 返回一个json结构的查询结果
            return DSL.using(conn)
                    .fetch(resultSet)
                    .map(new RecordMapper() {
                        @Override
                        public JSONObject map(Record r) {
                            JSONObject obj = new JSONObject();
                            colNames.forEach(cn -> obj.put(cn, r.get(cn)));
                            return obj;
                        }
                    }).toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 如果异常，return 空 json，不影响后续逻辑执行
        return "{}";
    }
}
