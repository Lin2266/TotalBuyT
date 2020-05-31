/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.Order;
import uuu.totalbuy.domain.OrderItem;
import uuu.totalbuy.domain.Product;
import uuu.totalbuy.domain.ShoppingCart;
import uuu.totalbuy.domain.TotalBuyException;

/**
 *
 * @author Administrator
 */
public class OrdersDAO implements DAOInterface<Integer, Order> {

    private final String INSERT_Order_SQL = "INSERT INTO orders (customer_id, order_time, payment_type, payment_amount, "
            + "shipping_type, shipping_amount, receiver_name, receiver_email, receiver_phone, shipping_address) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?)";

    private final String INSERT_OrderItem_SQL = "INSERT INTO order_items (order_id, product_id, quantity, price, free) VALUES(?,?,?,?,?)";

    private final String SELECT_BY_Customer_Id_SQL = "SELECT orders.id, "
            + "customer_id, name, order_time, orders.status, bad_status, "
            + "payment_type, payment_amount, payment_note, "
            + "shipping_type, shipping_amount, shipping_note, "
            + "receiver_name, receiver_email, receiver_phone, shipping_address, "
            + "SUM(price*quantity) AS total_amount "
            + "FROM orders JOIN customers ON orders.customer_id = customers.id "
            + "JOIN order_items ON orders.id=order_items.order_id "
            + "WHERE customer_id=? "
            + "GROUP BY orders.id";

    private final String SELECT_BY_Order_Id_SQL = "SELECT orders.id, "
            + "order_time, orders.status, bad_status, "
            + "payment_type, payment_amount, payment_note, "
            + "shipping_type, shipping_amount, shipping_note, "
            + "receiver_name, receiver_email, receiver_phone, shipping_address, "
            + "product_id, name, price, quantity, order_items.free "            
            + "FROM orders JOIN order_items ON orders.id=order_items.order_id "
            + "JOIN products ON order_items.product_id=products.id "
            + "WHERE orders.id=? ";

    @Override
    public void insert(Order data) throws TotalBuyException {
        try (Connection connection = RDBConnection.getConnection(); //1. 建立連線
                PreparedStatement pstmt1 = connection.prepareStatement(INSERT_Order_SQL, Statement.RETURN_GENERATED_KEYS); //2.1 準備指令
                PreparedStatement pstmt2 = connection.prepareStatement(INSERT_OrderItem_SQL); //2.2 準備指令                       
                ) {
            pstmt1.setString(1, data.getCustomer().getId());
            pstmt1.setTimestamp(2, new java.sql.Timestamp(data.getOrderTime().getTime()));
            pstmt1.setInt(3, data.getPaymentType().ordinal());
            pstmt1.setDouble(4, data.getPaymentAmount());
            pstmt1.setInt(5, data.getShippingType().ordinal());
            pstmt1.setDouble(6, data.getShippingAmount());
            pstmt1.setString(7, data.getReceiverName());
            pstmt1.setString(8, data.getReceiverEmail());
            pstmt1.setString(9, data.getReceiverPhone());
            pstmt1.setString(10, data.getShippingAddress());

            connection.setAutoCommit(false);
            try {
                pstmt1.executeUpdate();
                try (ResultSet rs = pstmt1.getGeneratedKeys();) {//取得自動給號的order id值
                    if (rs.next()) {
                        int key = rs.getInt(1);
                        data.setId(key);
                    }
                }

                for (OrderItem item : data.getItems()) {
                    pstmt2.setInt(1, data.getId());
                    pstmt2.setInt(2, item.getProduct().getId());
                    pstmt2.setInt(3, item.getQuantity());
                    pstmt2.setDouble(4, item.getPrice());
                    pstmt2.setBoolean(5, item.isFree());
                    pstmt2.executeUpdate();
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {            
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, "新增訂單資料失敗-" + ex.getErrorCode(), ex);
            throw new TotalBuyException("新增訂單資料失敗-" + ex.getErrorCode(), ex);
        }
    }

    @Override
    public void update(Order data) throws TotalBuyException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Order data) throws TotalBuyException {
        throw new UnsupportedOperationException("Not supported!"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order get(Integer id) throws TotalBuyException {
        Order order = null;
        try (Connection connection = RDBConnection.getConnection();//1. 建立連結
                PreparedStatement pstmt = connection.prepareStatement(SELECT_BY_Order_Id_SQL);) {//2. 準備指令
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) { //3. 執行指令
                while (rs.next()) { //4. 取得資料                    
                    if (order == null) {
                        order = new Order();
                        order.setId(rs.getInt("id"));
                        //order.setCustomer(null);
                        order.setOrderTime(rs.getTimestamp("order_time"));
                        order.setPaymentType(Order.PaymentType.values()[rs.getInt("payment_type")]);
                        order.setPaymentAmount(rs.getDouble("payment_amount"));
                        order.setPaymentNote(rs.getString("payment_note"));
                        order.setShippingType(Order.ShippingType.values()[rs.getInt("shipping_type")]);
                        order.setShippingAmount(rs.getDouble("shipping_amount"));
                        order.setShippingNote(rs.getString("shipping_note"));
                        order.setReceiverName(rs.getString("receiver_name"));
                        order.setReceiverEmail(rs.getString("receiver_email"));
                        order.setReceiverPhone(rs.getString("receiver_phone"));
                        order.setShippingAddress(rs.getString("shipping_address"));
                        order.setStatus(Order.Status.values()[rs.getInt("status")]);
                        order.setBadStatus(Order.BadStatus.values()[rs.getInt("bad_status")]);
                    }
                    OrderItem item = new OrderItem();
                    item.setOrderId(order.getId());
                    item.setProduct(new Product(rs.getInt("product_id"), rs.getString("name")));
                    item.setPrice(rs.getDouble("price"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setFree(rs.getBoolean("free"));
                    order.add(item);
                }
                return order;
            }
        } catch (SQLException ex) {
            System.out.println(SELECT_BY_Order_Id_SQL);
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, "查詢訂單資料失敗-" + ex.getErrorCode(), ex);
            throw new TotalBuyException("查詢訂單資料失敗-" + ex.getErrorCode(), ex);
        }
    }

    @Override
    public List<Order> getAll() throws TotalBuyException {
        throw new UnsupportedOperationException("Not supported!"); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Order> getAllByCustomerId(String id) throws TotalBuyException {
        List<Order> list = new ArrayList<>();
        try (Connection connection = RDBConnection.getConnection();//1. 建立連結
                PreparedStatement pstmt = connection.prepareStatement(SELECT_BY_Customer_Id_SQL);) {//2. 準備指令
            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) { //3. 執行指令
                while (rs.next()) { //4. 取得資料
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCustomer(new Customer(rs.getString("customer_id"), rs.getString("name")));
                    order.setOrderTime(rs.getTimestamp("order_time"));
                    order.setPaymentType(Order.PaymentType.values()[rs.getInt("payment_type")]);
                    order.setPaymentAmount(rs.getDouble("payment_amount"));
                    order.setPaymentNote(rs.getString("payment_note"));
                    order.setShippingType(Order.ShippingType.values()[rs.getInt("shipping_type")]);
                    order.setShippingAmount(rs.getDouble("shipping_amount"));
                    order.setShippingNote(rs.getString("shipping_note"));
                    order.setReceiverName(rs.getString("receiver_name"));
                    order.setReceiverEmail(rs.getString("receiver_email"));
                    order.setReceiverPhone(rs.getString("receiver_phone"));
                    order.setShippingAddress(rs.getString("shipping_address"));
                    order.setStatus(Order.Status.values()[rs.getInt("status")]);
                    order.setBadStatus(Order.BadStatus.values()[rs.getInt("bad_status")]);
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    list.add(order);
                }
                return list;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, "查詢客戶訂單資料失敗-" + ex.getErrorCode(), ex);
            throw new TotalBuyException("查詢客戶訂單資料失敗-" + ex.getErrorCode(), ex);
        }
    }

    public static void main(String[] args) {
        try {
            //0.1 登入
            CustomerService service = new CustomerService();
            Customer c = service.login("A123456789", "123456");

            //0.2 查詢產品清單
            ProductService pservice = new ProductService();
            List<Product> plist = pservice.getAll();

            //1.1 建立購物車
            ShoppingCart cart = new ShoppingCart(c);
            cart.add(plist.get(0), 2); //for 第1次測試
            cart.add(plist.get(3), 2); //for 第1次測試

            //cart.add(plist.get(1), 2); for 第2次測試
            //1.2 結帳
            Order order = new Order(c, cart);

            order.setPaymentType(Order.PaymentType.ATM);
            order.setPaymentAmount(Order.PaymentType.ATM.getAmount());
            order.setShippingType(Order.ShippingType.HOME);
            order.setShippingAmount(Order.ShippingType.HOME.getAmount());
            order.setReceiverName(c.getName());
            order.setReceiverEmail(c.getEmail() != null ? c.getEmail() : "test@uuu.com.tw");
            order.setReceiverPhone(c.getPhone() != null ? c.getPhone() : "0225149191");
            order.setShippingAddress(c.getAddress() != null ? c.getAddress() : "台北市復興北路99號16F");

            OrdersDAO dao = new OrdersDAO();
            dao.insert(order);

            //2. 查詢歷史訂單
            List<Order> olist = dao.getAllByCustomerId(c.getId());
            for (Order orderData : olist) {
                System.out.println(orderData.getId());
                System.out.println(orderData.getCustomer());
                System.out.println(orderData.getOrderTime());
                System.out.println(orderData.getTotalAmount());
            }
        } catch (Exception ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
