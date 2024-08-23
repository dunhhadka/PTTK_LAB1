<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.book.Book" %>
<%@ page import="org.example.user.User" %>
<%@ page import="org.example.order.OrderItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>User Details</title>
</head>
<body>
<div class="container">
    <header>
        <nav class="navbar">
            <ul>
                <li><a href="users?action=list">User</a></li>
                <li><a href="books?action=list">Book</a></li>
            </ul>
        </nav>
        <h1>User Details</h1>
    </header>
    <main>
        <%
            User user = (User) request.getAttribute("user");
            List<OrderItem> orderItems = (List<OrderItem>) request.getAttribute("order_items");

            if (user != null) {
        %>
        <div class="user-detail">
            <h2>Details for <%= user.getUsername() %></h2>
            <p>ID: <%= user.getId() %></p>
            <p>Username: <%= user.getUsername() %></p>
            <p>Email: <%= user.getEmail() %></p>

            <form action="orders" method="POST">
                <input type="hidden" name="userId" value="<%= user.getId() %>">
                <input type="hidden" name="action" value="pre-order">
                <button type="submit" class="btn order-btn">Đặt sách</button>
            </form>

            <form action="orders" method="POST" style="margin-top: 20px">
                <input type="hidden" name="userId" value="<%= user.getId() %>">
                <input type="hidden" name="action" value="order-books">
                <table class="book-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Select</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Book> books = (List<Book>) request.getAttribute("books");
                        if (books != null && !books.isEmpty()) {
                            for (Book book : books) {
                    %>
                    <tr>
                        <td><%= book.getId() %></td>
                        <td><%= book.getTitle() %></td>
                        <td><%= book.getAuthor() %></td>
                        <td>
                            <input type="checkbox" name="bookIds" value="<%= book.getId() %>">
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="4" class="no-data">No books available.</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
                <button type="submit" class="btn order-btn">Đặt sách</button>
            </form>

            <h2>Order Items</h2>
            <table class="book-table" style="margin-top: 20px">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Book ID</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (orderItems != null && !orderItems.isEmpty()) {
                        for (OrderItem orderItem : orderItems) {
                %>
                <tr>
                    <td><%= orderItem.getId() %></td>
                    <td><%= orderItem.getBookId() %></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="2" class="no-data">No order items found.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
        <%
        } else {
        %>
        <p>User not found.</p>
        <%
            }
        %>
    </main>
</div>
</body>
</html>
