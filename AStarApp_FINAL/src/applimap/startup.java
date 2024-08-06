package applimap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class startup extends JFrame {

    private JButton jButton1;
    private JButton jButton2;

    public startup() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800)); // Set initial larger size
        setResizable(true); // Allow window to be resizable
        getContentPane().setLayout(new BorderLayout());

        // Create a custom panel for background image
        BackgroundPanel backgroundPanel = new BackgroundPanel(new ImageIcon(getClass().getResource("/applimap/StartPage-Panel (1).png")));
        getContentPane().add(backgroundPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        selectionmap selectionmapFrame = new selectionmap();
        selectionmapFrame.setVisible(true);
        selectionmapFrame.pack();
        selectionmapFrame.setLocationRelativeTo(null);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(startup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new startup().setVisible(true);
        });
    }

    // Custom panel for background image
    private class BackgroundPanel extends JPanel {

        private final ImageIcon backgroundImage;

        public BackgroundPanel(ImageIcon image) {
            this.backgroundImage = image;
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            jButton1 = new JButton("START");
            jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 24));
            jButton1.setBackground(new java.awt.Color(204, 153, 0));
            jButton1.setForeground(new java.awt.Color(242, 242, 242));
            jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new java.awt.Insets(10, 10, 10, 10); // Add some padding
            add(jButton1, gbc);

            jButton2 = new JButton("EXIT");
            jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); 
            jButton2.setBackground(new java.awt.Color(204, 0, 0));
            jButton2.setForeground(new java.awt.Color(242, 242, 242));
            jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.insets = new java.awt.Insets(10, 10, 10, 10); // Add some padding
            add(jButton2, gbc);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
