package dy_assert;

import util.SqlUtils;

import java.sql.SQLException;

public class AssertSql {

    public static String prizeAssert(String id){
        StringBuilder builder = new StringBuilder("SELECT name from kxcj.cj_prize where id = \"");
        builder.append(id);
        builder.append("\"");
        return builder.toString();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println(SqlUtils.select(prizeAssert("238fd128-3db7-4163-a8e3-11919006100")));
    }
}
