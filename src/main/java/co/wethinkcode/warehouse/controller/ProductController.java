package co.wethinkcode.warehouse.controller;

import co.wethinkcode.warehouse.model.Product;
import co.wethinkcode.warehouse.service.ProductService;
import io.javalin.Javalin;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void registerRoutes(Javalin app) {

        app.post("/api/products", ctx -> {

            Product product = ctx.bodyAsClass(Product.class);

            Product createdProduct = productService.createProduct(product);

            ctx.status(201);
            ctx.json(createdProduct);
        });
    }
}