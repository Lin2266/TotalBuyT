package uuu.totalbuy.test;

public class MyStatic {
    
    private int serial;
    private static int counter;
    
    static {
        // 從檔案讀取上次的值...
        // ...
        counter = 12;
        System.out.println("static block");
    }
    
    public MyStatic() {
        System.out.println("Constructor");
        counter++;
        serial = counter;
    }

    public int getSerial() {
        return serial;
    }
    
    public static int getCounter() {
        return counter;
    }
    
}
