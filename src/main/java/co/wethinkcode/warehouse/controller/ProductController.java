package co.wethinkcode.warehouse.controller;

import co.wethinkcode.warehouse.model.Product;
import co.wethinkcode.warehouse.service.ProductService;
import io.javalin.Javalin;
import java.util.Optional;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void registerRoutes(Javalin app) {

        app.get("/api/products", ctx -> {
            ctx.json(productService.getAllProducts());
        });

        app.post("/api/products", ctx -> {

            Product product = ctx.bodyAsClass(Product.class);

            Product createdProduct = productService.createProduct(product);

            ctx.status(201);
            ctx.json(createdProduct);
        });

        app.get("/api/products/{id}", ctx -> {

            long id = Long.parseLong(ctx.pathParam("id"));

            Optional<Product> product = productService.getProductById(id);

            if (product.isPresent()) {
                ctx.json(product.get());
            } else {
                ctx.status(404);
                ctx.json(java.util.Map.of("error", "Product not found"));
            }
        });
    }
}