package uuu.totalbuy.test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * @author Administrator
 */
public class TestProperties {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("file.encoding"));
        Properties props = System.getProperties();
        File file = new File("totalbuy_zh_TW.properties");
        System.out.println(file.getAbsolutePath());
        try (FileReader reader = new FileReader(file)) {
            props.load(reader);
            
            Enumeration names = props.propertyNames();
            while(names.hasMoreElements()){
                String name = (String)names.nextElement();
                String value = props.getProperty(name);
                System.out.println(name + ": " + value);
            }
        }catch(IOException ex){
            System.out.println(ex);
        }
        
        try(FileWriter writer = new FileWriter("totalbuy_zh_TW.properties")){
            props.store(writer, "TotalBuy Comments");            
        }catch(IOException ex){
            System.out.println(ex);
        }
        
        ResourceBundle bundle = ResourceBundle.getBundle("uuu.totalbuy.test.totalbuy");
        System.out.println(bundle.getString("app.name"));
    }
}
