package co.wethinkcode.warehouse.service;

import co.wethinkcode.warehouse.model.Product;
import co.wethinkcode.warehouse.repository.ProductRepository;

import java.sql.SQLException;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) throws SQLException {

        validateProduct(product);

        if (product.getActive() == null) {
            product.setActive(true);
        }

        if (product.getReorderLevel() == null) {
            product.setReorderLevel(0);
        }

        return productRepository.save(product);
    }

    private void validateProduct(Product product) {

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.getSku() == null || product.getSku().isBlank()) {
            throw new IllegalArgumentException("SKU is required");
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }

        if (product.getUnitPrice() == null) {
            throw new IllegalArgumentException("Unit price is required");
        }

        if (product.getUnitPrice().signum() < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }

        if (product.getReorderLevel() != null && product.getReorderLevel() < 0) {
            throw new IllegalArgumentException("Reorder level cannot be negative");
        }
    }
}