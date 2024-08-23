package org.example.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }


    public void addBooks(Integer userId, List<Integer> bookIds) throws SQLException {
        String sql = "INSERT INTO order_items (user_id, book_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Integer bookId : bookIds) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, bookId);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<OrderItem> getOrderItemsByUserId(Integer userId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT id, user_id, book_id FROM order_items WHERE user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    Integer bookId = rs.getInt("book_id");

                    OrderItem orderItem = new OrderItem(id, userId, bookId);
                    orderItems.add(orderItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return orderItems;
    }
}
