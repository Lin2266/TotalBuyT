/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import uuu.totalbuy.domain.BloodType;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.TotalBuyException;
import uuu.totalbuy.domain.VIP;

/**
 *
 * @author Administrator
 */
public class CustomersDAO implements DAOInterface<String, Customer> {

    private static final String SELECT_ALL_SQL = "SELECT id, name, password, gender, email,birth_date, address, phone,married,"
            + " blood_type, status, discount, type FROM customers";
    private static final String SELECT_SQL = SELECT_ALL_SQL + " WHERE id=?";
    private static final String INSERT_SQL = "INSERT INTO customers "
            + "(id, name, password, gender, email,birth_date, address, phone,married,blood_type, status, discount, type)"
            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE customers SET name=?, password=?, gender=?, email=?,"
            + " birth_date=?, address=?, phone=?, married=?, blood_type=?, status=?, discount=?, type=?"
            + " WHERE id=?";
    private static final String DELETE_SQL = "DELETE FROM customers WHERE id=?";

    @Override
    public void insert(Customer data) throws TotalBuyException {
        //2.get Connection
        try (Connection connection = RDBConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(INSERT_SQL);) { //3. prepare statement
            pstmt.setString(1, data.getId());
            pstmt.setString(2, data.getName());
            pstmt.setString(3, data.getPassword());
            pstmt.setString(4, String.valueOf(data.getGender()));
            pstmt.setString(5, data.getEmail());

            pstmt.setDate(6, data.getBirthDate() == null ? null : new java.sql.Date(data.getBirthDate().getTime()));
            pstmt.setString(7, data.getAddress());
            pstmt.setString(8, data.getPhone());
            pstmt.setBoolean(9, data.isMarried());
            pstmt.setString(10, data.getBloodType() == null ? null : data.getBloodType().name());

            pstmt.setInt(11, data.getStatus());

            if (data instanceof VIP) {
                pstmt.setInt(12, ((VIP) data).getDiscount());
            } else {
                pstmt.setInt(12, 0);
            }

            pstmt.setString(13, data.getClass().getSimpleName());
            pstmt.executeUpdate(); //4. 執行指令

        } catch (SQLException ex) {
            System.out.println("CustomersDAO.insert 失敗:" + data + "," + ex);
            throw new TotalBuyException("會員註冊失敗:" + data, ex);
        }
    }

    @Override
    public void update(Customer data) throws TotalBuyException {
        //2.get Connection
        try (Connection connection = RDBConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(UPDATE_SQL);) { //3. prepare statement
            pstmt.setString(1, data.getName());
            pstmt.setString(2, data.getPassword());
            pstmt.setString(3, String.valueOf(data.getGender()));
            pstmt.setString(4, data.getEmail());

            pstmt.setDate(5, data.getBirthDate() == null ? null : new java.sql.Date(data.getBirthDate().getTime()));
            pstmt.setString(6, data.getAddress());
            pstmt.setString(7, data.getPhone());
            pstmt.setBoolean(8, data.isMarried());
            pstmt.setString(9, data.getBloodType() == null ? null : data.getBloodType().name());

            pstmt.setInt(10, data.getStatus());

            if (data instanceof VIP) {
                pstmt.setInt(11, ((VIP) data).getDiscount());
            } else {
                pstmt.setInt(11, 0);
            }

            pstmt.setString(12, data.getClass().getSimpleName());
            pstmt.setString(13, data.getId());

            pstmt.executeUpdate(); //4. 執行指令

        } catch (SQLException ex) {
            System.out.println("CustomersDAO.update 失敗:" + data + "," + ex);
            throw new TotalBuyException("修改會員失敗:" + data, ex);
        }
    }

    @Override
    public void delete(Customer data) throws TotalBuyException {
        //2.get Connection
        try (Connection connection = RDBConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(DELETE_SQL);) { //3. prepare statement
            pstmt.setString(1, data.getId());

            pstmt.executeUpdate(); //4. 執行指令

        } catch (SQLException ex) {
            System.out.println("CustomersDAO.delete 失敗:" + data + "," + ex);
            throw new TotalBuyException("刪除會員失敗:" + data, ex);
        }
    }

    @Override
    public Customer get(String id) throws TotalBuyException {
        Customer c = null;
        try (Connection connection = RDBConnection.getConnection();//2. get connection
                //Statement stmt = connection.createStatement();
                PreparedStatement pstmt = connection.prepareStatement(SELECT_SQL); //3. prepare statement                    
                ) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery();) { //4. 執行指令
                //5. 讀取rs
                while (rs.next()) {
                    String type = rs.getString("type");
                    if (type.equals("VIP")) {
                        c = new VIP();
                    } else {
                        c = new Customer();
                    }
                    c.setId(rs.getString("id"));
                    c.setName(rs.getString("name"));
                    c.setPassword(rs.getString("password"));
                    c.setGender(rs.getString("gender").charAt(0));
                    c.setEmail(rs.getString("email"));

                    c.setBirthDate(rs.getDate("birth_date"));
                    c.setAddress(rs.getString("address"));
                    c.setPhone(rs.getString("phone"));
                    c.setMarried(rs.getBoolean("married"));

                    String bType = rs.getString("blood_type");
                    if (bType != null) {
                        c.setBloodType(BloodType.valueOf(bType));
                    }

                    c.setStatus(rs.getInt("status"));

                    if (c instanceof VIP) {
                        ((VIP) c).setDiscount(rs.getInt("discount"));
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("查詢客戶失敗:" + id + ", errorCode:" + ex.getErrorCode());
            throw new TotalBuyException("查詢客戶失敗:" + id, ex);
        }
        return c;
    }

    @Override
    public List<Customer> getAll() throws TotalBuyException {
        List<Customer> list = null;
        try (Connection connection = RDBConnection.getConnection();//2. get connection
                PreparedStatement pstmt = connection.prepareStatement(//3. prepare statement 
                        SELECT_ALL_SQL);
                ResultSet rs = pstmt.executeQuery();) { //4. execute statement
            //5. 讀取rs
            while (rs.next()) {
                Customer c;
                String type = rs.getString("type");
                if (type.equals("VIP")) {
                    c = new VIP();
                } else {
                    c = new Customer();
                }
                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));
                c.setPassword(rs.getString("password"));
                c.setGender(rs.getString("gender").charAt(0));
                c.setEmail(rs.getString("email"));
                c.setBirthDate(rs.getDate("birth_date"));
                c.setAddress(rs.getString("address"));
                c.setPhone(rs.getString("phone"));
                c.setMarried(rs.getBoolean("married"));
                String bType = rs.getString("blood_type");
                if (bType != null) {
                    c.setBloodType(BloodType.valueOf(bType));
                }
                c.setStatus(rs.getInt("status"));
                if (c instanceof VIP) {
                    ((VIP) c).setDiscount(rs.getInt("discount"));
                }
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("查詢全部客戶失敗:" + ", errorCode:" + ex.getErrorCode());
            throw new TotalBuyException("查詢全部客戶失敗:", ex);
        }
        return list;
    }
}
