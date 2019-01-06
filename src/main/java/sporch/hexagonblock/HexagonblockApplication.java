package sporch.hexagonblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication   // declare it is a spring boot application
public class HexagonblockApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(HexagonblockApplication.class, args);
        System.out.println("ctx->"+ctx);
//        ctx.
    }


}

