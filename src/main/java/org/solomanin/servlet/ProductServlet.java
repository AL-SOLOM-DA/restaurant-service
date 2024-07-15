package org.solomanin.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.solomanin.dto.ProductGetDto;
import org.solomanin.mapper.ProductMapper;
import org.solomanin.entity.Product;
import org.solomanin.jdbc.DBUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private final String URL = DBUtil.url;
    private final String USER = DBUtil.user;
    private final String PASS = DBUtil.password;

    public static final long serialVersionUID = 1;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName(DBUtil.driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found " + e);
        }
        try {
            Connection conn = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connection is successful");

            PreparedStatement statement = conn.prepareStatement("select * from products");

            ResultSet resultSet = statement.executeQuery();
            List<ProductGetDto> products = new ArrayList<>();
            while(resultSet.next()){
                products.add(ProductMapper.INSTANCE.productToProductDto(new Product(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getBigDecimal(3),
                        resultSet.getInt(4),
                        resultSet.getBoolean(5))));
//                        new ArrayList<ProductCategory>((Collection<? extends ProductCategory>) Arrays.asList(resultSet.getArray(6))))));
            }
            PrintWriter out = resp.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(mapper.writeValueAsString(products));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName(DBUtil.driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement statement = connection.prepareStatement("insert into products (product_name, price, quantity, available) values (?,?,?,?)");

            statement.setString(1, req.getParameter("prodName"));
            statement.setDouble(2, Double.parseDouble(req.getParameter("prodPrice")));
            statement.setInt(3, Integer.parseInt(req.getParameter("prodQuantity")));
            statement.setBoolean(4, Boolean.parseBoolean(req.getParameter("prodAvailable")));

            statement.executeUpdate();

            statement.close();
            connection.close();

            resp.sendRedirect("/products");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Class.forName(DBUtil.driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection conn = DriverManager.getConnection(URL,USER,PASS);
            PreparedStatement preparedStatement = conn.prepareStatement("delete from products where product_id=?");
            preparedStatement.setInt(1, Integer.parseInt(req.getParameter("prodId")));

            resp.sendRedirect("/products");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
