package org.solomanin.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.solomanin.dto.ProductDto;
import org.solomanin.entity.Product;
import org.solomanin.jdbc.DBUtil;
import org.solomanin.mapper.ProductMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    public static final String UPDATE_PRODUCTS_By_ID = "update products set product_name=?, price=?, quantity=?, available=? where product_id = ?";
    public static final String SELECT_ALL_PRODUCTS = "select * from products";
    public static final String INSERT_INTO_PRODUCTS = "insert into products (product_name, price, quantity, available) values (?,?,?,?)";
    public static final String DELETE_FROM_PRODUCTS_WHERE_PRODUCT_ID = "delete from products where product_id=?";
    private final String URL = DBUtil.url;
    private final String USER = DBUtil.user;
    private final String PASS = DBUtil.password;

    public static final long serialVersionUID = 1;
    public Connection connection;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName(DBUtil.driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found " + e);
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection is successful");

            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCTS);

            ResultSet resultSet = statement.executeQuery();
            List<ProductDto> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(ProductMapper.INSTANCE.productToProductDTO(new Product(
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
            connection = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_PRODUCTS);

            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            }

            ProductDto prod = new ObjectMapper().readValue(sb.toString(), ProductDto.class);
            statement.setString(1, prod.getProdName());
            statement.setBigDecimal(2, prod.getProdPrice());
            statement.setInt(3, prod.getProdQuantity());
            statement.setBoolean(4, prod.isProdAvailable());

            statement.executeUpdate();

            statement.close();
            connection.close();

            resp.sendRedirect("/products");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName(DBUtil.driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCTS_By_ID);

            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            }

            ProductDto prod = new ObjectMapper().readValue(sb.toString(), ProductDto.class);
            statement.setString(1, prod.getProdName());
            statement.setBigDecimal(2, prod.getProdPrice());
            statement.setInt(3, prod.getProdQuantity());
            statement.setBoolean(4, prod.isProdAvailable());
            statement.setLong(5, prod.getProdId());

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
            connection = DriverManager.getConnection(URL, USER, PASS);
            StringBuilder jsonProdBuilder = new StringBuilder();

            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonProdBuilder.append(line).append('\n');
                }
            }

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_PRODUCTS_WHERE_PRODUCT_ID);
            ProductDto prod = new ObjectMapper().readValue(jsonProdBuilder.toString(), ProductDto.class);
            preparedStatement.setLong(1, prod.getProdId());
            preparedStatement.executeUpdate();

            resp.sendRedirect("/products");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
