
package co.wethinkcode.warehouse;

import co.wethinkcode.warehouse.controller.ProductController;
import co.wethinkcode.warehouse.repository.ProductRepository;
import co.wethinkcode.warehouse.service.ProductService;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(
                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
                );
            }));
        });

        ProductRepository productRepository = new ProductRepository();
        ProductService productService =
                new ProductService(productRepository);
        ProductController productController =
                new ProductController(productService);

        productController.registerRoutes(app);

        app.get("/", ctx -> {
            ctx.result("Smart Warehouse Management System");
        });

        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
            ctx.status(500);
            ctx.json(e.getMessage());
        });

        app.start(5050);
    }
}
