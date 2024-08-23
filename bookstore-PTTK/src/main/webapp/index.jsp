<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.book.Book" %>
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
                <th>Title</th>
                <th>Author</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Book> books = (List<Book>) request.getAttribute("books");
                Book editingBook = (Book) request.getAttribute("editingBook");
                if (books != null && !books.isEmpty()) {
                    for (Book book : books) {
            %>
            <tr>
                <td><%= book.getId() %></td>
                <td><%= book.getTitle() %></td>
                <td><%= book.getAuthor() %></td>
                <td>
                    <form action="books" method="POST" class="action-form">
                        <input type="hidden" name="id" value="<%= book.getId() %>">
                        <input type="hidden" name="action" value="edit">
                        <button type="submit" class="btn edit-btn">Edit</button>
                    </form>
                    <form action="books" method="POST" class="action-form">
                        <input type="hidden" name="id" value="<%= book.getId() %>">
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
                <td colspan="4" class="no-data">No books available.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>

        <%
            if (editingBook != null) {
        %>
        <h2>Edit Book</h2>
        <form action="books" method="POST" class="book-form">
            <div class="form-group">
                <input type="hidden" name="id" value="<%= editingBook.getId() %>">
                <label>Title:</label>
                <input type="text" name="title" value="<%= editingBook.getTitle() %>" required>
            </div>
            <div class="form-group">
                <label>Author:</label>
                <input type="text" name="author" value="<%= editingBook.getAuthor() %>" required>
            </div>
            <input type="hidden" name="action" value="update">
            <button type="submit" class="btn update-btn">Update Book</button>
        </form>
        <%
            }
        %>

        <h2>Add Book</h2>
        <form action="books" method="POST" class="book-form">
            <div class="form-group">
                <label>Title:</label>
                <input type="text" name="title" required>
            </div>
            <div class="form-group">
                <label>Author:</label>
                <input type="text" name="author" required>
            </div>
            <input type="hidden" name="action" value="add">
            <button type="submit" class="btn add-btn">Add Book</button>
        </form>
    </main>
</div>
</body>
</html>
