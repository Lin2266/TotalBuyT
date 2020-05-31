package uuu.totalbuy.test;

public class MySeason {
    
    public static void show( Season s ) {
        switch ( s ) {
            case SPRING:
                System.out.println("Spring");
                break;
            case SUMMER:
                System.out.println("Summer");
                break;
            case FALL:
                System.out.println("Fall");
                break;
            case WINTER:
                System.out.println("Winter");
                break;
        }
    }
    
    /*
    public static final int SPRING = 0;
    public static final int SUMMER = 1;
    public static final int FALL = 2;
    public static final int WINTER = 3;
    
    public static void show(int s) {
        switch (s) {
            case SPRING:
                System.out.println("Spring");
                break;
            case SUMMER:
                System.out.println("Summer");
                break;
            case FALL:
                System.out.println("Fall");
                break;
            case WINTER:
                System.out.println("Winter");
                break;
            default:
                System.out.println("Error");
                break;
        }
    }
    */
}
