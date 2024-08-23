<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.book.Book" %>
<%@ page import="org.example.user.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Book List</title>
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
        <h1>Book List</h1>
    </header>
    <main>
        <table class="book-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Detail</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<User> users = (List<User>) request.getAttribute("users");
                if (users != null && !users.isEmpty()) {
                    for (User user : users) {
            %>
            <tr>
                <td><%= user.getId() %>
                </td>
                <td>
                    <a href="users?action=detail&id=<%= user.getId() %>"><%= user.getUsername() %></a>
                </td>
                <td><%= user.getUsername() %>
                </td>
                <td>
                    <form action="users" method="POST" class="action-form">
                        <input type="hidden" name="id" value="<%= user.getId() %>">
                        <input type="hidden" name="action" value="edit">
                        <button type="submit" class="btn edit-btn">Edit</button>
                    </form>
                    <form action="users" method="POST" class="action-form">
                        <input type="hidden" name="id" value="<%= user.getId() %>">
                        <input type="hidden" name="action" value="delete">
                        <button type="submit" class="btn delete-btn">Delete</button>
                    </form>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="4" class="no-data">No users available.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>


        <h2>Add User</h2>
        <form action="users" method="POST" class="user-form">
            <div class="form-group">
                <label>Username:</label>
                <input type="text" name="username" required>
            </div>
            <div class="form-group">
                <label>Password:</label>
                <input type="password" name="password" required>
            </div>
            <input type="hidden" name="action" value="add">
            <button type="submit" class="btn add-btn">Add User</button>
        </form>
    </main>
</div>
</body>
</html>
