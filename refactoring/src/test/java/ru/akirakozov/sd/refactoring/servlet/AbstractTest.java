package ru.akirakozov.sd.refactoring.servlet;

import org.mockito.Mock;
import ru.akirakozov.sd.refactoring.dao.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AbstractTest {
    @Mock
    protected HttpServletRequest servletRequest;

    @Mock
    protected HttpServletResponse servletResponse;

    @Mock
    protected ProductDao productDao;

}
