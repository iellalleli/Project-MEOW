package applimap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Applimap {

    public static void main(String[] args) {
        startup startupFrame = new startup();
        startupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startupFrame.setSize(1200, 800); // Set an initial size for the startup frame
        startupFrame.setLocationRelativeTo(null);
        startupFrame.setVisible(true);
    }
}
