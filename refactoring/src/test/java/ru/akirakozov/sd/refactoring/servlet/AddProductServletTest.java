package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.dao.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AddProductServletTest {
    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private AddProductServlet servlet;

    @Mock
    private ProductDao productDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        servlet = new AddProductServlet(productDao);
    }

    @Test
    public void testAddProductServlet() throws IOException {
        when(servletRequest.getParameter("name"))
                .thenReturn("iphone6");
        when(servletRequest.getParameter("price"))
                .thenReturn("300");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter())
                .thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        assertThat(stringWriter.toString()).isEqualTo("OK" + System.lineSeparator());
    }

}
