package applimap;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class selectionmap extends JFrame {

    private JComboBox<String> jComboBox1;
    private JComboBox<String> jComboBox2;
    private JPanel cards;
    private pupmap pupmapPanel;

    public selectionmap() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));
        getContentPane().setLayout(new BorderLayout());

        // Create a panel to hold different views
        cards = new JPanel(new CardLayout());

        // Create selection panel with background image
        BackgroundPanel selectionPanel = new BackgroundPanel("C:/Users/Mariella Prado/OneDrive/Documents/NetBeansProjects/AStarApp/src/applimap/MapSelectPanel (3).png");
        selectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        jComboBox1 = new JComboBox<>();
        jComboBox1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); 
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "Starting Point (Main Gate)", "Amphitheater", "Apolinario Mabini Shrine", "Campus Development and Maintenance Building",
            "Charlie del Rosario Hall", "Community Building Gabriela Silang", "Covered Walkway", "Dome", "East Wing", "Flag Pole",
            "Freedom Park", "Gazebo (Souvenir Shop)", "Grandstand", "Guard House (Entrance)", "Guard House (Exit)",
            "Gymnasium and Sports Center", "Interfaith Chapel", "Laboratory High School", "Lagoon", "Linear Park", "Main Building",
            "Mabini Monument P.E. Obelisk", "Ninoy Aquino Library and Learning Resources Center",
            "North Wing", "Nutrition and Food Science Building", "Physical Education Building",
            "Printing Press Building", "PUP Sta. Mesa Ferry Station", "Property Building",
            "South Wing", "Student Canteen", "Swimming Pool",
            "Tahanan ng Alumni", "Tennis Court", "Track and Football Oval", "University Canteen Sampaguita Building", "Visitor Information Center",
            "West Wing"
        }));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 20; // Horizontal padding
        gbc.ipady = 10; // Vertical padding
        gbc.insets = new Insets(500, 0, 10, 0); // Increase top inset to push down
        selectionPanel.add(jComboBox1, gbc);

        jComboBox2 = new JComboBox<>();
        jComboBox2.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); 
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "Main Building", "Starting Point (Main Gate)", "Amphitheater", "Apolinario Mabini Shrine", "Campus Development and Maintenance Building",
            "Charlie del Rosario Hall", "Community Building Gabriela Silang", "Covered Walkway", "Dome", "East Wing", "Flag Pole",
            "Freedom Park", "Gazebo (Souvenir Shop)", "Grandstand", "Guard House (Entrance)", "Guard House (Exit)",
            "Gymnasium and Sports Center", "Interfaith Chapel", "Laboratory High School", "Lagoon", "Linear Park",
            "Mabini Monument P.E. Obelisk", "Ninoy Aquino Library and Learning Resources Center",
            "North Wing", "Nutrition and Food Science Building", "Physical Education Building",
            "Printing Press Building", "PUP Sta. Mesa Ferry Station", "Property Building",
            "South Wing", "Student Canteen", "Swimming Pool",
            "Tahanan ng Alumni", "Tennis Court", "Track and Football Oval", "University Canteen Sampaguita Building", "Visitor Information Center",
            "West Wing"
        }));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(500, 0, 10, 0); // Increase top inset to push down
        selectionPanel.add(jComboBox2, gbc);

        JButton startButton = new JButton("START");
        startButton.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); 
        startButton.setBackground(new java.awt.Color(204, 153, 0));
        startButton.setForeground(Color.white);
        startButton.addActionListener(this::startButtonActionPerformed);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0); // Adjust insets to minimize the gap
        selectionPanel.add(startButton, gbc);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setBackground(new java.awt.Color(255, 0, 0));
        cancelButton.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); 
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this::cancelButtonActionPerformed);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0); // Adjust insets to minimize the gap
        selectionPanel.add(cancelButton, gbc);

        // Add selection panel to cards panel
        cards.add(selectionPanel, "SelectionPanel");

        // Create pupmap panel
        pupmapPanel = new pupmap();
        cards.add(pupmapPanel, "PupmapPanel");

        getContentPane().add(cards, BorderLayout.CENTER);

        pack(); 
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void startButtonActionPerformed(ActionEvent evt) {
        String startLocation = (String) jComboBox1.getSelectedItem();
        String goalLocation = (String) jComboBox2.getSelectedItem();

        pupmapPanel.setNodesBasedOnSelection(startLocation, goalLocation);

        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, "PupmapPanel");
        pupmapPanel.requestFocus(); // Request focus for keyboard events
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose(); // Close the frame
    }

    public void showSelectionPanel() {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, "SelectionPanel");
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
            java.util.logging.Logger.getLogger(selectionmap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(() -> {
            new selectionmap().setVisible(true);
        });
    }

    // BackgroundPanel class to display a background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String filePath) {
            // Load the background image
            backgroundImage = new ImageIcon(filePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the background image
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
