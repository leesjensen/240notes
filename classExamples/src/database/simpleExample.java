package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class simpleExample {

    public static void main(String[] args) throws SQLException {
        simpleSelect();
        simpleParameter(5, 37);
    }

    private static void simpleSelect() throws SQLException {
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT 1+1")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
                System.out.println(rs.getInt(1));
            }
        }
    }

    private static void simpleParameter(int a, int b) throws SQLException {
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT ?+?")) {
                preparedStatement.setInt(1, a);
                preparedStatement.setInt(2, b);

                var rs = preparedStatement.executeQuery();
                rs.next();
                System.out.println(rs.getInt(1));
            }
        }
    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "monkeypie");
    }

}
