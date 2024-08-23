package org.example.user;

import org.example.MyDataSourceFactory;
import org.example.order.OrderDao;
import org.example.order.OrderItem;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private DataSource dataSource;
    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        try {
            dataSource = MyDataSourceFactory.getDataSource();
            assert dataSource != null;
            userDAO = new UserDAO(dataSource.getConnection());
            orderDao = new OrderDao(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            try {
                List<User> users = userDAO.getAllUsers();
                req.setAttribute("users", users);
                RequestDispatcher dispatcher = req.getRequestDispatcher("user_list.jsp");
                dispatcher.forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            }
        }

        if ("detail".equals(action)) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            User user = userDAO.getById(id);
            List<OrderItem> orderItems = orderDao.getOrderItemsByUserId(id);
            req.setAttribute("user", user);
            req.setAttribute("order_items", orderItems);
            RequestDispatcher dispatcher = req.getRequestDispatcher("user_detail.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password"); // Handle password securely

            User user = new User(username, password);
            try {
                userDAO.addUser(user);
                resp.sendRedirect("users?action=list");
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            }
        }
    }
}
