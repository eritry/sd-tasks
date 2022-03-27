package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {

    public AddProductServlet(ProductDao productDao) {
        super(productDao);
    }

    @Override
    void doRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        productDao.insert(new Product(name, price));

        response.getWriter().println("OK");
    }
}


