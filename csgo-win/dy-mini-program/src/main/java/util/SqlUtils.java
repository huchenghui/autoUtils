package util;

import config.EnumMysql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class SqlUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SqlUtils.class);
    private static Connection con = null;

    /**
     * 连接数据库
     * **/
    private static Connection getCon() throws ClassNotFoundException, SQLException {
        Class.forName(EnumMysql.DRIVER_PATH.getVal());
        if (con == null){
            con = DriverManager.getConnection(getUrl(),EnumMysql.USER_NAME.getVal()
                    ,EnumMysql.PASS_WORD.getVal());
            return con;
        }
        return con;
    }

    /**
     * 查询单条记录
     * @param sql sql语句
     * **/
    public static String select(String sql) throws SQLException, ClassNotFoundException {
        Statement statement = getCon().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        String val = null;
        while (resultSet.next()){
            val = resultSet.getString(1);
        }
        return val;
    }

    /**
     * 查询多条记录
     * @param sql sql语句
     * **/
    public static ResultSet selectAll(String sql) throws SQLException, ClassNotFoundException {
        Statement statement = getCon().createStatement();
        return statement.executeQuery(sql);
    }

    public static void close(){
        if (con != null){
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 数据库连接地址
     * **/
    private static String getUrl(){
        StringBuilder builder = new StringBuilder("jdbc:mysql://");
        builder.append(EnumMysql.DOMAIN.getVal());
        builder.append(EnumMysql.DATABASE.getVal());
        builder.append("?serverTimezone=UTC");

        LOG.info("数据库地址 >>> " + builder.toString());
        return builder.toString();
    }

}
