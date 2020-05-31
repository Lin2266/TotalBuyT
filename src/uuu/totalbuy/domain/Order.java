package uuu.totalbuy.domain;

import java.util.*;

public class Order {

    private int id;
    private Customer customer;
    private Date orderTime;
    private PaymentType paymentType; //0:面交,1:ATM,2:便利店,3:貨到付款
    private double paymentAmount;
    private String paymentNote;
    private ShippingType shippingType; //0:自取,1:便利店取貨,2:送貨到府
    private double shippingAmount;
    private String shippingNote;
    private String receiverName;
    private String receiverEmail;
    private String receiverPhone;
    private String shippingAddress;
    private Status status; //0:新訂單 1:已付款 2:處理中 3.已出貨, 4.已到貨, 5.已簽收 6.已結案
    private BadStatus badStatus; //0:無 1:要求取消 2:已取消, 3.要求退貨 4.已退貨, 5.要求退款, 6.已退款

    private List<OrderItem> orderItems;
    private double totalAmount;

    { //non-static initializer
        orderTime = new Date();
        orderItems = new ArrayList<>();        
    }
    
    public Order() {        
    }

    public Order(Customer customer) { //查詢客戶的一筆歷史訂單
        this.customer = customer;
    }

    public Order(Customer customer, ShoppingCart cart) { //結帳時建立新訂單
        this.customer = customer;
        this.add(cart);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentNote() {
        return paymentNote;
    }

    public void setPaymentNote(String paymentNote) {
        this.paymentNote = paymentNote;
    }

    public ShippingType getShippingType() {
        return shippingType;
    }

    public void setShippingType(ShippingType shippingType) {
        this.shippingType = shippingType;
    }

    public double getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public String getShippingNote() {
        return shippingNote;
    }

    public void setShippingNote(String shippingNote) {
        this.shippingNote = shippingNote;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String recieverPhone) {
        this.receiverPhone = recieverPhone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BadStatus getBadStatus() {
        return badStatus;
    }

    public void setBadStatus(BadStatus badStatus) {
        this.badStatus = badStatus;
    }

    //訂單明細的管理程式
    public void add(OrderItem item){ //從資料庫查詢明細時使用
        orderItems.add(item);
    }
    
    public void add(ShoppingCart cart){//從結帳功能傳入購物車時使用
        for(Product p:cart.getKeySet()){
            OrderItem item = new OrderItem();
            item.setProduct(p);
            if(!(p instanceof Outlet) && this.customer instanceof VIP){
                item.setPrice(p.getUnitPrice() * (100-((VIP)this.customer).getDiscount()) / 100);
            }else{
                item.setPrice(p.getUnitPrice());
            }
            
            item.setQuantity(cart.getQuentity(p));
            item.setFree(false);            
            orderItems.add(item);
        }
    }
    
    //delegate methods by List<OrderItem>
    public int size() {
        return orderItems.size();
    }

    public boolean isEmpty() {
        return orderItems.isEmpty();
    }
    
    public double getTotalAmount(){
        if(orderItems!=null && orderItems.size()>0){
            double sum = 0;
            for(OrderItem item:orderItems){
                sum += (item.isFree()?0:item.getPrice() * item.getQuantity());
            }
            sum += paymentAmount + shippingAmount;            
            return sum;
        }else{
            return totalAmount + paymentAmount + shippingAmount;
        }        
    }    
    
    public void setTotalAmount(double amount){ //computed column from DB
        this.totalAmount = amount;
    }
    
    public List<OrderItem> getItems(){
        //return Collections.unmodifiableList(this.orderItems); //回傳readonly的集合
        return new ArrayList<>(this.orderItems); //回傳原集合的副本
    }
    
    public enum PaymentType {

        FACE("面交", new ShippingType[]{ShippingType.SELF}),
        ATM("ATM轉帳", new ShippingType[]{ShippingType.STORE, ShippingType.HOME}),
        STORE("便利店付款", new ShippingType[]{ShippingType.STORE}),
        HOME("貨到付款", 50, new ShippingType[]{ShippingType.HOME});

        private final String description; //ReadOnly
        private final double amount; //ReadOnly
        private final ShippingType[] shippingArray;

        private PaymentType(String description, ShippingType[] shippingArray) {
            this(description, 0, shippingArray);
        }

        private PaymentType(String description, double amount, ShippingType[] shippingArray) {
            this.description = description;
            this.shippingArray = shippingArray;
            this.amount = amount;
        }

        public String getDescription() {
            return this.description;
        }

        public double getAmount() {
            return amount;
        }

        public ShippingType[] getShippingArray() {
            return shippingArray.clone();
        }

        @Override
        public String toString() {
            return  String.format("%s-%s", this.name(), description);
        }        
        
    }

    public enum ShippingType {

        SELF("自取"), STORE("便利店取貨", 60), HOME("送貨到府", 100);

        private final String description; //ReadOnly
        private final double amount; //ReadOnly

        private ShippingType(String description) {
            this(description, 0);
        }

        private ShippingType(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }

        public String getDescription() {
            return this.description;
        }

        public double getAmount() {
            return amount;
        }
        
        @Override
        public String toString() {
            return  String.format("%s-%s", this.name(), description);
        }        
        
    }

    public enum Status {
        NEW("新訂單"), PAID("已付款"), PROCESS("處理中"), SHIPPED("已出貨"), ARRIVED("已到貨"), CHECKED("已簽收"), CLOSED("已結案");

        private final String description;

        private Status(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
        
        @Override
        public String toString() {
            return  String.format("%s-%s", this.name(), description);
        }        
        
    }

    public enum BadStatus {
        NONE("無"), CANCEL("要求取消"), CANCELED("已取消"), REJECT("要求退貨"), REJECTED("已退貨"), REFUND("要求退款"), REFUNDED("已退款");

        private final String description;

        private BadStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
        
        @Override
        public String toString() {
            return  String.format("%s-%s", this.name(), description);
        }        
        
    }

}
