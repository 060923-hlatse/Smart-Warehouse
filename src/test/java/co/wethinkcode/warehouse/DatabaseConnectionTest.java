package co.wethinkcode.warehouse;

import co.wethinkcode.warehouse.config.DatabaseConfig;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseConnectionTest {

    @Test
    void shouldConnectToDatabase() throws Exception {
        Connection connection = DatabaseConfig.getConnection();

        assertNotNull(connection);

        connection.close();
    }
}