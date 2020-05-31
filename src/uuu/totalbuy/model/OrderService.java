package uuu.totalbuy.model;

import java.util.List;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.Order;
import uuu.totalbuy.domain.Product;
import uuu.totalbuy.domain.TotalBuyException;
import uuu.totalbuy.domain.VIP;

public class OrderService {
    private OrdersDAO dao = new OrdersDAO();
    
    public OrderService() { }
    
    public void checkOut(Order order) throws TotalBuyException{
        dao.insert(order);
    }
    
    public Order get(int orderId)throws TotalBuyException{
        Order order = dao.get(orderId);
        return order;
    }
    
    public List<Order> getOrdersHistory(String id) throws TotalBuyException{
        return dao.getAllByCustomerId(id);
    }

    public static double order(Customer c, Product p, int quantity) 
            throws OutOfStockException {
        
        if ( p.getStock() < quantity ) {
            throw new OutOfStockException(p.getName());
        }

        double rtn = p.getUnitPrice() * quantity;

        if (c instanceof VIP) {
            VIP v = (VIP) c;
            rtn *= v.getRate();
        }

        return rtn;
    }
    
}
