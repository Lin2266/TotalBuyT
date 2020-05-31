package uuu.totalbuy.domain;

public class VIP extends Customer {

    private int discount = 10;

    public VIP() {
    }

    public VIP(String id, String name) {
        super(id, name);
    }

    public VIP(String id, String name, String password, String email) {
        super(id, name, password, email);
    }

    public VIP(String id, String name, String password) {
        super(id, name, password);
    }

    public VIP(int discount, String id, String name, String password, String email) {
        super(id, name, password, email);
        this.discount = discount;
    }

    /* 這是測試 p9-29, ...*/
    public VIP(int discount) {
        super();
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        if (discount >= 0 && discount <= 95) {
            this.discount = discount;
        } else {
            //throw new IllegalArgumentException("discount必須在0~95之間");
            System.out.println("discount必須在0~95之間");
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\n"
                + "Discount:  " + getDiscount();
    }

    public double getRate() {
        return (100 - getDiscount()) / 100.0;
    }

}
