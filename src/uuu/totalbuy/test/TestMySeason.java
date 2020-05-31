package uuu.totalbuy.test;

public class TestMySeason {

    public static void main(String[] args) {

        //Season.values()  => Season[]
        for ( Season s  :  Season.values()) {
            //System.out.println( s.getName() );
            System.out.println( s.name() );
            System.out.println( s.ordinal() );
            
            if (s == Season.SUMMER) {
                //
            }
        }
        

        
        //System.out.println( Season.SUMMER.getName() );
        //System.out.println( Season.SPRING.getName() );
        
        //MySeason.show( Season.SUMMER );
        //MySeason.show( Season.SPRING );
        //MySeason.show(Season.HELLO);
        
    }
    
}
