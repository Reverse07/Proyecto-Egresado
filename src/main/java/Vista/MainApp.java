
package Vista;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class MainApp {
    
public static void main(String[] args) {
        try {
     
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            System.err.println("❌ No se pudo cargar FlatLaf: " + e);
        }

       
        SwingUtilities.invokeLater(() -> {
            LoginForm login = new LoginForm();  
            login.setVisible(true);
        });
    }
}
