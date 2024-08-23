package org.example.book;

import org.example.MyDataSourceFactory;

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

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookDAO bookDAO;
    private DataSource dataSource;

    @Override
    public void init() {
        try {
            dataSource = MyDataSourceFactory.getDataSource();
            assert dataSource != null;
            bookDAO = new BookDAO(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            try {
                List<Book> books = bookDAO.getAllBooks();
                req.setAttribute("books", books);
                RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
                dispatcher.forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            switch (action) {
                case "add":
                    String title = req.getParameter("title");
                    String author = req.getParameter("author");
                    bookDAO.addBook(new Book(title, author));
                    break;
                case "update":
                    int id = Integer.parseInt(req.getParameter("id"));
                    title = req.getParameter("title");
                    author = req.getParameter("author");
                    bookDAO.updateBook(new Book(id, title, author));
                    break;
                case "delete":
                    id = Integer.parseInt(req.getParameter("id"));
                    bookDAO.deleteBook(id);
                    break;
            }
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
