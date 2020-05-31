package uuu.totalbuy.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uuu.totalbuy.domain.Outlet;
import uuu.totalbuy.domain.Product;
import uuu.totalbuy.domain.TotalBuyException;

/**
 *
 * @author Administrator
 */
public class RDBProductsDAO implements DAOInterface<Integer, Product> {

    private static final String INSERT_SQL_WITHOUT_ID = "INSERT INTO products "
            + "(name,unit_price, free, stock,description, url, status, discount,type) "
            + "VALUES(?,?,?,?,?,?,?,?,?)";

    private static final String INSERT_SQL_WITH_ID = "INSERT INTO products "
            + "(name,unit_price, free, stock,description, url, status, discount,type, id) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE_SQL = "UPDATE products "
            + "SET name=?,unit_price=?,free=?,stock=?,description=?,url=?,status=?,discount=?,type=? "
            + "WHERE id=?";

    private static final String DELETE_SQL = "DELETE FROM products WHERE id=?";

    private static final String SELECT_ALL_SQL = "SELECT id,name,unit_price, free, stock,description, url, status, discount,type "
            + "FROM products";

    private static final String SELECT_SQL = SELECT_ALL_SQL + " WHERE id = ?";

    private Product createProduct(String type) {
        Product p = null;
        if (type != null) { //type: uuu.totalbuy.domain.Member, VIP, uuu.totalbuy.domain.GoldenVIP, ...
            String fullyClassName = Product.class.getName().replace("Product", type);
            try {
                p = (Product)Class.forName(fullyClassName).newInstance();
            } catch (Exception ex) {
                Logger.getLogger(RDBProductsDAO.class.getName()).log(Level.SEVERE, null, ex);
                p = new Product();
            }
        } else {
            p = new Product();
        }
        return p;
    }

    @Override
    public Product get(Integer id) throws TotalBuyException {
        Product p = null;
        //TODO: 
        try (Connection connection = RDBConnection.getConnection(); //1. 取得Connection
                PreparedStatement pstmt = connection.prepareStatement(SELECT_SQL);//2. 準備指令
                ) {
            System.out.println(SELECT_SQL);
            pstmt.setInt(1, id); //2.1 傳值
            try (ResultSet rs = pstmt.executeQuery();) {//3. 執行指令並取得ResultSet
                while (rs.next()) {//4.處理Result, 讀取產品資料
                    if (p != null) {
                        throw new TotalBuyException("查詢產品失敗(遭受SQL Injection): id: " + id);
                    }
                    String type = rs.getString("type");
                    p = createProduct(type);
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setUnitPrice(rs.getDouble("unit_price"));
                    p.setFree(rs.getBoolean("free"));
                    p.setStock(rs.getInt("stock"));
                    p.setDescription(rs.getString("description"));
                    p.setUrl(rs.getString("url"));
                    p.setStatus(rs.getInt("status"));
                    if (p instanceof Outlet) {
                        ((Outlet) p).setDiscount(rs.getInt("discount"));
                    }
                }
                return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RDBProductsDAO.class.getName()).log(Level.SEVERE, "查詢產品失敗: id-" + id + ", " + ex.getErrorCode(), ex);
            throw new TotalBuyException("查詢產品失敗: id-" + id + ", " + ex.getErrorCode(), ex);
        }
    }

    @Override
    public List<Product> getAll() throws TotalBuyException {
        try (Connection connection = RDBConnection.getConnection(); //1. 建立連線
                PreparedStatement pstmt = connection.prepareStatement(SELECT_ALL_SQL); //2. 準備指令
                ResultSet rs = pstmt.executeQuery(); //3. preparedStatement執行指令，回傳ResultSet
                ) {
            List<Product> productsList = new ArrayList<>();
            while (rs.next()) {//4.處理Result, 讀取產品資料
                String type = rs.getString("type");
                Product p = createProduct(type);
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setUnitPrice(rs.getDouble("unit_price"));
                p.setFree(rs.getBoolean("free"));
                p.setStock(rs.getInt("stock"));
                p.setDescription(rs.getString("description"));
                p.setUrl(rs.getString("url"));
                p.setStatus(rs.getInt("status"));
                if (p instanceof Outlet) {
                    ((Outlet) p).setDiscount(rs.getInt("discount"));
                }

                productsList.add(p);
            }
            //System.out.println(membersList);
            return productsList;
        } catch (SQLException ex) {
            Logger.getLogger(RDBProductsDAO.class.getName()).log(Level.SEVERE, "查詢全部產品失敗: " + ex.getErrorCode(), ex);
            throw new TotalBuyException("查詢全部產品失敗: " + ex.getErrorCode(), ex);
        }
    }

    @Override
    public void insert(Product p) throws TotalBuyException {
        try (Connection connection = RDBConnection.getConnection();//1. 建立連線
                PreparedStatement pstmt = connection.prepareStatement(//2. 準備指令
                        p.getId() > 0 ? INSERT_SQL_WITH_ID : INSERT_SQL_WITHOUT_ID, //由資料庫自動給號
                        p.getId() > 0 ? Statement.NO_GENERATED_KEYS : Statement.RETURN_GENERATED_KEYS);) {
            //2.1 傳值, id,name,unit_price, free, stock,description, url, status, discount,type
            pstmt.setString(1, p.getName());
            pstmt.setDouble(2, p.getUnitPrice());
            pstmt.setBoolean(3, p.isFree());
            pstmt.setInt(4, p.getStock());
            pstmt.setString(5, p.getDescription());
            pstmt.setString(6, p.getUrl());
            pstmt.setInt(7, p.getStatus());
            pstmt.setInt(8, (p instanceof Outlet ? ((Outlet) p).getDiscount() : 0));
            pstmt.setString(9, p.getClass().getSimpleName());
            if (p.getId() > 0) {
                pstmt.setInt(10, p.getId());
            }

            //3. 執行指令
            pstmt.executeUpdate();

            if (p.getId() <= 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys();) {//取得自動給號的值
                    if (rs.next()) {
                        int key = rs.getInt(1);
                        p.setId(key);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RDBProductsDAO.class.getName()).log(Level.SEVERE, "新增產品資料失敗-" + ex.getErrorCode(), ex);
            throw new TotalBuyException("新增產品資料失敗-" + ex.getErrorCode(), ex);
        }
    }

    @Override
    public void update(Product p) throws TotalBuyException {
        try (Connection connection = RDBConnection.getConnection();//1. 建立連線
                PreparedStatement pstmt = connection.prepareStatement(UPDATE_SQL);) { //2. 準備指令
            //2.1 傳值, id,name,unit_price, free, stock,description, url, status, discount,type            
            pstmt.setString(1, p.getName());
            pstmt.setDouble(2, p.getUnitPrice());
            pstmt.setBoolean(3, p.isFree());
            pstmt.setInt(4, p.getStock());
            pstmt.setString(5, p.getDescription());
            pstmt.setString(6, p.getUrl());
            pstmt.setInt(7, p.getStatus());
            pstmt.setInt(8, (p instanceof Outlet ? ((Outlet) p).getDiscount() : 0));
            pstmt.setString(9, p.getClass().getSimpleName());
            pstmt.setInt(10, p.getId());
            //3. 執行指令
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RDBProductsDAO.class.getName()).log(Level.SEVERE, "修改產品資料失敗-" + ex.getErrorCode(), ex);
            throw new TotalBuyException("修改產品資料失敗-" + ex.getErrorCode(), ex);
        }
    }

    @Override
    public void delete(Product p) throws TotalBuyException {
        try (Connection connection = RDBConnection.getConnection();//1. 建立連線
                PreparedStatement pstmt = connection.prepareStatement(DELETE_SQL);) { //2. 準備指令
            //2.1 傳值, id,name,unit_price, free, stock,description, url, status, discount,type            
            pstmt.setInt(1, p.getId());
            //3. 執行指令
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RDBProductsDAO.class.getName()).log(Level.SEVERE, "刪除產品資料失敗-" + ex.getErrorCode(), ex);
            throw new TotalBuyException("刪除產品資料失敗-" + ex.getErrorCode(), ex);
        }
    }
}
