package ru.akirakozov.sd.refactoring.dao;


import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import  ru.akirakozov.sd.refactoring.product.Product;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ru.akirakozov.sd.refactoring.dao.Utils.dropTables;
import static ru.akirakozov.sd.refactoring.dao.Utils.createTables;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {

    private static List<Product> PRODUCTS_EXAMPLE;
    private static ProductDao productDao;

    @BeforeClass
    public static void setup() throws SQLException {
        PRODUCTS_EXAMPLE = Arrays.asList(
                new Product("iphone6", 100), new Product("iphone7", 200), new Product("iphone8", 300)
        );
        productDao = new ProductDao();
        createTables();
    }

    @After
    public void clean() throws Exception {
        dropTables();
    }

    private void insertProducts(List<Product> products) throws SQLException {
        for (Product product : products) {
            productDao.insert(product);
        }
    }

    @Test
    public void testGetFromEmptyDB() throws SQLException {
        assertThat(productDao.getProducts()).isEmpty();
    }

    @Test
    public void testFindMinMaxFromEmptyDB() throws SQLException {
        assertThat(productDao.findMinPriceProduct()).isEmpty();
        assertThat(productDao.findMaxPriceProduct()).isEmpty();
    }

    @Test
    public void testInsert() throws SQLException {
        insertProducts(PRODUCTS_EXAMPLE);
        assertThat(productDao.getProducts()).containsExactlyInAnyOrder(PRODUCTS_EXAMPLE.toArray(new Product[3]));
    }

    @Test
    public void testGetProductsCount() throws SQLException {
        insertProducts(PRODUCTS_EXAMPLE);
        assertThat(productDao.getProductsCount()).isEqualTo(3);
    }

    @Test
    public void testGetPricesSum() throws SQLException {
        insertProducts(PRODUCTS_EXAMPLE);
        assertThat(productDao.getPricesSum()).isEqualTo(600);
    }

    @Test
    public void testFindMinPriceProduct() throws SQLException {
        insertProducts(PRODUCTS_EXAMPLE);
        Optional<Product> product = productDao.findMinPriceProduct();
        assertThat(product).isPresent();
        assertThat(product.get()).isEqualTo(new Product("iphone6", 100));
    }

    @Test
    public void testFindMaxPriceProduct() throws SQLException {
        insertProducts(PRODUCTS_EXAMPLE);
        Optional<Product> product = productDao.findMaxPriceProduct();
        assertThat(product).isPresent();
        assertThat(product.get()).isEqualTo(new Product("iphone8", 300));
    }

}