package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


import static ru.akirakozov.sd.refactoring.dao.Utils.createTables;
import static ru.akirakozov.sd.refactoring.dao.Utils.dropTables;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        dropTables();
        createTables();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ProductDao productDao = new ProductDao();

        context.addServlet(new ServletHolder(new AddProductServlet(productDao)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productDao)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productDao)),"/query");

        server.start();
        server.join();
    }
}
