package org.example.user;

import org.example.MyDataSourceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private DataSource dataSource;

    @Override
    public void init() {
        try {
            dataSource = MyDataSourceFactory.getDataSource();
            assert dataSource != null;
            userDAO = new UserDAO(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // You should hash the password
        user.setEmail(email);

        try {
            userDAO.registerUser(user);
            resp.sendRedirect("login.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("register.jsp");
        }
    }
}
