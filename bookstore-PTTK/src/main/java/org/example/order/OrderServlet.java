package org.example.order;

import org.example.MyDataSourceFactory;
import org.example.book.Book;
import org.example.book.BookDAO;
import org.example.user.User;
import org.example.user.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource dataSource;
    private OrderDao orderDao;
    private UserDAO userDAO;
    private BookDAO bookDAO;

    @Override
    public void init() throws ServletException {
        try {
            dataSource = MyDataSourceFactory.getDataSource();
            assert dataSource != null;
            orderDao = new OrderDao(dataSource.getConnection());
            userDAO = new UserDAO(dataSource.getConnection());
            bookDAO = new BookDAO(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("pre-order".equals(action)) {
            try {
                Integer userId = Integer.parseInt(req.getParameter("userId"));
                User user = userDAO.getById(userId);
                List<Book> books = bookDAO.getAllBooks();
                req.setAttribute("user", user);
                req.setAttribute("books", books);
                req.setAttribute("action", "pre-order");
                RequestDispatcher dispatcher = req.getRequestDispatcher("user_detail.jsp");
                dispatcher.forward(req, resp);
            } catch (Exception e) {

            }
        }

        if ("order-books".equals(action)) {
            try {
                Integer userId = Integer.parseInt(req.getParameter("userId"));
                User user = userDAO.getById(userId);
                List<Integer> bookIds = Arrays.stream(req.getParameterValues("bookIds"))
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
                orderDao.addBooks(userId, bookIds);

                List<OrderItem> orderItems = orderDao.getOrderItemsByUserId(userId);
                req.setAttribute("user", user);
                req.setAttribute("action", "pre-order");
                req.setAttribute("order_items", orderItems);
                RequestDispatcher dispatcher = req.getRequestDispatcher("user_detail.jsp");
                dispatcher.forward(req, resp);
            } catch (Exception e) {

            }
        }
    }

}
