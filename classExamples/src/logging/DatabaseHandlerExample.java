package logging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DatabaseHandlerExample {
    static Logger logger = Logger.getLogger("myLogger");

    public static void main(String[] args) throws Exception {
        var config = new DatabaseConfig("jdbc:mysql://localhost:3306", "pet_store", "root", "monkeypie");
        logger.addHandler(new DatabaseHandler(config));

        logger.log(Level.INFO, "This will be logged");
        logger.log(Level.WARNING, "And this also");
    }


    public static record DatabaseConfig(String url, String dbName, String user, String password) {
    }

    public static class DatabaseHandler extends Handler {
        private final DatabaseConfig config;

        public DatabaseHandler(DatabaseConfig config) throws SQLException {
            this.config = config;
            configureDatabase();
        }

        public void publish(LogRecord record) {
            try {
                try (var conn = getConnection()) {
                    var stm = String.format("INSERT INTO `%s`.log (level, message, date) VALUES(?, ?, now())", config.dbName);
                    try (var preparedStatement = conn.prepareStatement(stm)) {
                        preparedStatement.setString(1, record.getLevel().toString());
                        preparedStatement.setString(2, record.getMessage());

                        preparedStatement.executeUpdate();
                    }
                }
            } catch (Exception ex) {
                System.out.printf("Failed to log: %s%n", ex.getMessage());
            }
        }

        public void flush() {
        }

        public void close() throws SecurityException {
        }


        Connection getConnection() throws SQLException {
            return DriverManager.getConnection(config.url, config.user, config.password);
        }

        void configureDatabase() throws SQLException {
            try (var conn = getConnection()) {
                var stm = String.format("CREATE DATABASE IF NOT EXISTS `%s`", config.dbName);
                var createDbStatement = conn.prepareStatement(stm);
                createDbStatement.executeUpdate();

                var createPetTable = String.format("""
                        CREATE TABLE  IF NOT EXISTS `%s`.log (
                            id INT NOT NULL AUTO_INCREMENT,
                            message VARCHAR(4096) NOT NULL,
                            level VARCHAR(16) NOT NULL,
                            date DATETIME NOT NULL,
                            PRIMARY KEY(id),
                            INDEX(date)
                        )""", config.dbName);
                try (var createTableStatement = conn.prepareStatement(createPetTable)) {
                    createTableStatement.executeUpdate();
                }
            }
        }
    }


}
