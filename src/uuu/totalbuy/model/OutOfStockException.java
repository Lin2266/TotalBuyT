package uuu.totalbuy.model;

// 例外類別, Checked
public class OutOfStockException extends Exception {
    
    public OutOfStockException(String message) {
        super(message);
    }
    
}
