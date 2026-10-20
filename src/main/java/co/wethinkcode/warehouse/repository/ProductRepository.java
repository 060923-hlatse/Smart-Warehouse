package co.wethinkcode.warehouse.repository;

import co.wethinkcode.warehouse.config.DatabaseConfig;
import co.wethinkcode.warehouse.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Product> findAll() throws SQLException {

        String sql = """
                SELECT id, sku, name, description, category,
                    unit_price, reorder_level, active, created_at
                FROM products
                ORDER BY id
                """;

        List<Product> products = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product();

                product.setId(resultSet.getLong("id"));
                product.setSku(resultSet.getString("sku"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setCategory(resultSet.getString("category"));
                product.setUnitPrice(resultSet.getBigDecimal("unit_price"));
                product.setReorderLevel(resultSet.getInt("reorder_level"));
                product.setActive(resultSet.getBoolean("active"));
                product.setCreatedAt(
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );

                products.add(product);
            }
        }

        return products;
    }


    public Optional<Product> findById(long id) throws SQLException {

        String sql = """
                SELECT id, sku, name, description, category,
                    unit_price, reorder_level, active, created_at
                FROM products
                WHERE id = ?
                """;

        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    Product product = new Product();

                    product.setId(resultSet.getLong("id"));
                    product.setSku(resultSet.getString("sku"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCategory(resultSet.getString("category"));
                    product.setUnitPrice(resultSet.getBigDecimal("unit_price"));
                    product.setReorderLevel(resultSet.getInt("reorder_level"));
                    product.setActive(resultSet.getBoolean("active"));
                    product.setCreatedAt(
                            resultSet.getTimestamp("created_at").toLocalDateTime()
                    );

                    return Optional.of(product);
                }
            }
        }

        return Optional.empty();
    }

    }
