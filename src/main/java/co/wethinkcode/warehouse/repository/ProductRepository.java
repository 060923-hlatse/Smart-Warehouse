package co.wethinkcode.warehouse.repository;

import co.wethinkcode.warehouse.config.DatabaseConfig;
import co.wethinkcode.warehouse.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRepository {

    public Product save(Product product) throws SQLException {

        String sql = """
                INSERT INTO products (
                    sku,
                    name,
                    description,
                    category,
                    unit_price,
                    reorder_level,
                    active
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING id, created_at
                """;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getSku());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getCategory());
            statement.setBigDecimal(5, product.getUnitPrice());
            statement.setInt(6, product.getReorderLevel());
            statement.setBoolean(7, product.getActive());

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    product.setId(resultSet.getLong("id"));
                    product.setCreatedAt(
                            resultSet.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        }

        return product;
    }
}
