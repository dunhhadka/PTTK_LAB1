package org.example.user;

import org.example.MyDataSourceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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

        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) { // You should check hashed password
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                resp.sendRedirect("index.jsp");
            } else {
                resp.sendRedirect("login.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("login.jsp");
        }
    }
}
