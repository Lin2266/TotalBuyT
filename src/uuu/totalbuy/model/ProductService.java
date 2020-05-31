package uuu.totalbuy.model;

import java.util.List;
import uuu.totalbuy.domain.Product;
import uuu.totalbuy.domain.TotalBuyException;

/**
 *
 * @author Administrator
 */
public class ProductService {
    final private RDBProductsDAO dao = new RDBProductsDAO();

    public Product get(Integer id) throws TotalBuyException {
        return dao.get(id);
    }

    public List<Product> getAll() throws TotalBuyException {
        return dao.getAll();
    }

    public void insert(Product p) throws TotalBuyException {
        dao.insert(p);
    }

    public void update(Product p) throws TotalBuyException {
        dao.update(p);
    }

    public void delete(Product p) throws TotalBuyException {
        dao.delete(p);
    }

    public ProductService() {
    }

    public static double calculateProductInventory(Product product) {
        return product.getStock() * product.getUnitPrice();
    }

    public static void addPrice(Product p) {
        p.setUnitPrice(p.getUnitPrice() + 100);
    }
}
