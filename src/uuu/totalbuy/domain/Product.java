package uuu.totalbuy.domain;

import java.text.NumberFormat;

public class Product {

    private int id;//auto increment
    private String name;
    private double unitPrice = 10;
    private boolean free;
    private int stock;
    private String url;
    private String description;
    private int status; //0: 新產品, 1:上架, 2:停售, 3:停產
    public static final NumberFormat PRICE_FORMAT;

    static {
        PRICE_FORMAT = NumberFormat.getInstance();
        PRICE_FORMAT.setMaximumFractionDigits(2);
        PRICE_FORMAT.setMinimumFractionDigits(0);
    }

    public Product() {
    }

    public Product(int id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Product(int id, String name, double unitPrice) {
        this(id, name);
        this.unitPrice = unitPrice;
    }

    public Product(int id, String name, double unitPrice, boolean free, int stock) {
        this(id, name, unitPrice);
        this.free = free;
        this.stock = stock;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void incStock(int amount) {
        stock = stock + amount;
    }

    public void decStock(int amount) {
        if (amount >= stock) {
            stock = stock - amount;
        }
    }

    @Override
    public String toString() {
        return "ID:         " + getId() + "\n"
                + "Name:       " + getName() + "\n"
                + "Unit Price: " + getUnitPrice() + "\n"
                + "Free:       " + (isFree() ? "YES" : "NO") + "\n"
                + "Stock:      " + getStock();
    }

}
