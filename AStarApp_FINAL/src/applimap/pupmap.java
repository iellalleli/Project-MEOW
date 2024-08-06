package applimap;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class pupmap extends JPanel {

    // SCREEN SETTINGS
    final int maxCol = 63;
    final int maxRow = 37;
    final int nodeSize = 30;

    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;

    // NODE
    Node[][] nodes = new Node[maxCol][maxRow]; // 2D array to store nodes
    Node startNode, goalNode; // Start and goal nodes
    ArrayList<Node> pathList = new ArrayList<>(); // List to store the path

    // OTHERS
    Image mapImage;
    
    int[][] solidNodes = {
            {0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}, {9, 0}, {10, 0}, {11, 0}, {12, 0}, {13, 0}, {14, 0}, {15, 0}, {16, 0}, {17, 0}, {24, 0}, {25, 0}, {26, 0}, {27, 0}, {28, 0}, {29, 0}, {30, 0}, {31, 0}, {32, 0}, {33, 0}, {34, 0}, {35, 0}, {36, 0}, {37, 0}, {38, 0}, {39, 0}, {40, 0}, {41, 0}, {42, 0}, {43, 0}, {44, 0}, {45, 0}, {46, 0}, {47, 0}, {48, 0}, {49, 0}, {50, 0}, {51, 0}, {52, 0}, {53, 0}, {54, 0}, {55, 0}, {56, 0}, {57, 0}, {58, 0}, {59, 0}, {60, 0}, {61, 0}, {62, 0}, {0, 1}, {1, 1}, {2, 1}, {3, 1}, {20, 1}, {21, 1}, {22, 1}, {31, 1}, {32, 1}, {35, 1}, {42, 1}, {43, 1}, {57, 1}, {58, 1}, {59, 1}, {60, 1}, {61, 1}, {62, 1}, {0, 2}, {1, 2}, {2, 2}, {3, 2}, {4, 2}, {20, 2}, {23, 2}, {31, 2}, {32, 2},  
            {35, 2}, {42, 2}, {43, 2}, {45, 2}, {46, 2}, {47, 2}, {48, 2}, {50, 2}, {54, 2}, {55, 2}, {56, 2}, {57, 2}, {58, 2}, {59, 2}, {60, 2}, {61, 2}, {62, 2}, {0, 3}, {1, 3}, {2, 3}, {3, 3}, {4, 3}, {21, 3}, {23, 3}, {31, 3}, {32, 3}, {35, 3}, {42, 3}, {43, 3}, {54, 3}, {57, 3}, {58, 3}, {59, 3}, {60, 3}, {61, 3}, {62, 3}, {0, 4}, {1, 4}, {2, 4}, {3, 4}, {4, 4}, {5, 4}, {16, 4}, {21, 4}, {24, 4}, {26, 4}, {27, 4}, {28, 4}, {29, 4}, {31, 4}, {35, 4}, {42, 4}, {43, 4}, {50, 4}, {57, 4}, {58, 4}, {59, 4}, {60, 4}, {61, 4}, {62, 4}, {0, 5}, {1, 5}, {2, 5}, {3, 5}, {4, 5}, {5, 5}, {6, 5}, {16, 5}, {17, 5}, {22, 5}, {24, 5}, {25, 5}, {29, 5}, {31, 5}, {32, 5},  
            {37, 5}, {38, 5}, {39, 5}, {40, 5}, {41, 5}, {42, 5}, {43, 5}, {44, 5}, {46, 5}, {47, 5}, {48, 5}, {50, 5}, {57, 5}, {58, 5},  {59, 5}, {60, 5}, {61, 5}, {62, 5}, {0, 6}, {1, 6}, {2, 6}, {3, 6}, {4, 6}, {5, 6}, {6, 6}, {16, 6}, {17, 6}, {26, 6}, {27, 6}, {28, 6}, {29, 6}, {31, 6}, {32, 6},  {57, 6}, {58, 6}, {59, 6}, {60, 6}, {61, 6}, {62, 6}, {0, 7}, {1, 7}, {2, 7}, {3, 7}, {4, 7}, {5, 7}, {6, 7}, {16, 7}, {17, 7}, {25, 7}, {29, 7}, {31, 7}, {32, 7}, {38, 7}, {39, 7}, {40, 7}, {42, 7}, {43, 7}, {44, 7}, {45, 7}, {46, 7}, {47, 7},  {48, 7}, {49, 7}, {50, 7}, {57, 7}, {58, 7}, {59, 7}, {60, 7}, {61, 7}, {62, 7}, {0, 8}, {1, 8}, {2, 8}, {3, 8}, {4, 8}, {5, 8}, {6, 8}, {7, 8},  {23, 8}, {25, 8}, {29, 8}, {38, 8}, {44, 8}, {45, 8}, {46, 8}, 
            {47, 8}, {48, 8}, {49, 8}, {50, 8}, {57, 8}, {58, 8}, {59, 8}, {60, 8}, {61, 8}, {62, 8}, {0, 9}, {1, 9}, {2, 9}, {3, 9}, {4, 9}, {5, 9}, {6, 9}, {7, 9}, {23, 9}, {25, 9}, {27, 9}, {30, 9}, {31, 9}, {38, 9}, {44, 9}, {45, 9}, {46, 9}, {47, 9}, {48, 9}, {49, 9}, {50, 9}, {57, 9}, {58, 9}, {59, 9}, {60, 9}, {61, 9}, {62, 9}, {0, 10}, {1, 10}, {2, 10}, {3, 10}, {4, 10}, {5, 10}, {6, 10},  {7, 10}, {23, 10}, {26, 10}, {27, 10}, {28, 10}, {32, 10}, {38, 10}, {44, 10}, {45, 10}, {46, 10}, {47, 10}, {48, 10}, {49, 10}, {50, 10}, {57, 10}, {58, 10}, {59, 10}, {60, 10}, {61, 10}, {62, 10}, {0, 11}, {1, 11}, {2, 11}, {3, 11}, {4, 11}, {5, 11}, {6, 11}, {7, 11}, {8, 11}, {24, 11}, {26, 11}, {27, 11}, {28, 11}, {29, 11}, {30, 11},  {32, 11}, {40, 11}, {41, 11}, {42, 11}, {44, 11}, {45, 11}, {46, 11},  
            {47, 11}, {48, 11}, {49, 11}, {50, 11}, {52, 11}, {53, 11}, {54, 11},  {57, 11}, {58, 11}, {59, 11}, {60, 11}, {61, 11}, {62, 11}, {0, 12}, {1, 12}, {2, 12}, {3, 12}, {4, 12}, {5, 12}, {6, 12}, {7, 12}, {8, 12}, {24, 12}, {25, 12}, {26, 12}, {27, 12}, {28, 12}, {29, 12}, {32, 12}, {58, 12}, {59, 12}, {60, 12},  {61, 12}, {62, 12}, {0, 13}, {1, 13}, {2, 13}, {3, 13}, {4, 13}, {5, 13}, {6, 13}, {7, 13}, {8, 13}, {23, 13}, {24, 13}, {25, 13}, {26, 13}, {27, 13}, {28, 13}, {32, 13}, {35, 13}, {36, 13}, {37, 13}, {52, 14}, {53, 14}, {54, 14}, {55, 14}, {57, 13}, {58, 13}, {59, 13}, {60, 13}, {61, 13}, {62, 13}, {0, 14}, {1, 14}, {2, 14}, {3, 14}, {4, 14}, {5, 14}, {6, 14}, {7, 14},  {8, 14}, {9, 14}, {22, 14}, {24, 14}, {25, 14}, {26, 14}, {27, 14},  
            {28, 14}, {32, 14}, {35, 14}, {36, 14}, {37, 14}, {57, 14}, {58, 14},  {59, 14}, {60, 14}, {61, 14}, {62, 14}, {0, 15}, {1, 15}, {2, 15}, {3, 15}, {4, 15}, {5, 15}, {6, 15}, {7, 15},  {8, 15}, {9, 15}, {24, 15}, {25, 15}, {26, 15}, {27, 15}, {28, 15}, {50, 16}, {51, 16}, {52, 16}, {53, 16}, {56, 15}, {57, 15}, {58, 15}, {59, 15}, {60, 15}, {61, 15}, {62, 15}, {0, 16}, {1, 16}, {2, 16}, {3, 16}, {4, 16}, {5, 16}, {6, 16}, {7, 16}, {8, 16}, {9, 16}, {21, 16}, {24, 16}, {25, 16}, {26, 16}, {32, 16}, {40, 16}, {49, 16}, {58, 16}, {59, 16}, {60, 16}, {61, 16}, {62, 16}, {0, 17}, {1, 17}, {2, 17}, {3, 17}, {4, 17}, {5, 17}, {6, 17}, {7, 17}, {8, 17},  {9, 17}, {10, 17}, {21, 17}, {24, 17}, {25, 17},  
            {41, 17}, {58, 17}, {59, 17}, {60, 17}, {61, 17}, {62, 17},{0, 18}, {1, 18}, {2, 18}, {3, 18}, {4, 18}, {5, 18}, {6, 18}, {7, 18}, {8, 18}, {9, 18}, {10, 18}, {21, 18}, {37, 18}, {40, 18}, {58, 18}, {59, 18}, {60, 18}, {61, 18}, {62, 18}, {0, 19}, {1, 19}, {2, 19}, {3, 19}, {4, 19}, {5, 19}, {6, 19}, {7, 19}, {8, 19},  {9, 19}, {10, 19}, {22, 19}, {31, 19}, {32, 19}, {40, 19}, {43, 19}, {58, 19}, {59, 19}, {60, 19}, {61, 19}, {62, 19},{0, 20}, {1, 20}, {2, 20}, {3, 20}, {4, 20}, {5, 20}, {6, 20}, {7, 20}, {8, 20},  {9, 20}, {10, 20}, {11, 20}, {16, 20}, {18, 20}, {19, 20}, {30, 20}, {31, 20}, {32, 20}, {33, 20}, {37, 20}, {39, 20}, {44, 20}, {47,14}, {48,14}, {49,14}, {50,14}, {51,14},
            {58, 20}, {59, 20}, {60, 20}, {61, 20}, {62, 20}, {0, 21}, {1, 21}, {2, 21}, {3, 21}, {4, 21}, {5, 21}, {6, 21}, {7, 21}, {8, 21}, {9, 21}, {10, 21}, {11, 21}, {28, 21}, {29, 21}, {30, 21}, {31, 21}, {32, 21}, {33, 21}, {38, 21}, {43, 21}, {58, 21}, {59, 21}, {60, 21}, {61, 21}, {62, 21}, {0, 22}, {1, 22}, {2, 22}, {3, 22}, {4, 22}, {5, 22}, {6, 22}, {7, 22}, {8, 22}, {9, 22}, {10, 22}, {11, 22}, {16, 22}, {21, 22}, {22, 22}, {26, 22}, {27, 22}, {28, 22}, {29, 22}, {30, 22}, {31, 22}, {32, 22}, {33, 22}, {40, 23}, {58, 22}, {59, 22}, {60, 22}, {61, 22}, {62, 22}, {0, 23}, {1, 23}, {2, 23}, {3, 23}, {4, 23}, {5, 23}, {6, 23}, {7, 23}, {8, 23}, {9, 23}, {10, 23}, {11, 23}, {12, 23}, {18, 23}, {26, 23}, {27, 23}, {28, 23}, {29, 23}, {30, 23}, {31, 23}, {32, 23}, {41, 23}, {58, 23}, {59, 23}, {60, 23},  
            {61, 23}, {62, 23}, {0, 24}, {1, 24}, {2, 24}, {3, 24}, {4, 24}, {5, 24}, {6, 24}, {7, 24}, {8, 24}, {9, 24}, {10, 24}, {11, 24}, {12, 24}, {19, 24}, {27, 24}, {28, 24}, {29, 24}, {30, 24}, {39, 24}, {58, 24}, {59, 24}, {60, 24}, {61, 24}, {62, 24}, {0, 25}, {1, 25}, {2, 25}, {3, 25}, {4, 25}, {5, 25}, {6, 25}, {7, 25}, {8, 25}, {9, 25}, {10, 25}, {11, 25}, {12, 25}, {21, 25}, {22, 25}, {27, 25}, {28, 25}, {40, 25}, {58, 25}, {59, 25}, {60, 25}, {61, 25}, {62, 25}, {0, 26}, {1, 26}, {2, 26}, {3, 26}, {4, 26}, {5, 26}, {6, 26}, {7, 26}, {8, 26}, {9, 26}, {10, 26}, {11, 26}, {12, 26}, {13, 26}, {39, 26}, {58, 26}, {59, 26}, {60, 26}, {61, 26}, {62, 26}, {0, 27}, {1, 27}, {2, 27}, {3, 27}, {4, 27}, {5, 27}, {6, 27}, {7, 27}, {8, 27}, {9, 27}, {10, 27}, {11, 27}, {12, 27}, {13, 27}, {23, 27}, {26, 27}, {58, 27}, {59, 27}, {60, 27}, {61, 27}, {62, 27}, 
            {0, 28}, {1, 28}, {2, 28}, {3, 28}, {4, 28}, {5, 28}, {6, 28}, {7, 28}, {8, 28},  {9, 28}, {10, 28}, {11, 28}, {12, 28}, {13, 28}, {23, 28}, {26, 28}, {58, 28}, {59, 28}, {60, 28}, {61, 28}, {62, 28}, {0, 29}, {1, 29}, {2, 29}, {3, 29}, {4, 29}, {5, 29}, {6, 29}, {7, 29}, {8, 29}, {9, 29}, {10, 29}, {11, 29}, {12, 29}, {13, 29}, {14, 29}, {23, 29}, {26, 29}, {58, 29},   {59, 29}, {60, 29}, {61, 29}, {62, 29}, {0, 30}, {1, 30}, {2, 30}, {3, 30}, {4, 30}, {5, 30}, {6, 30}, {7, 30}, {8, 30}, {9, 30}, {10, 30}, {11, 30}, {12, 30}, {13, 30}, {14, 30}, {57, 30}, {58, 30}, {59, 30}, {60, 30}, {61, 30}, {62, 30}, {0, 31}, {1, 31}, {2, 31}, {3, 31}, {4, 31}, {5, 31}, {6, 31}, {7, 31}, {8, 31},  {9, 31}, {10, 31}, {11, 31}, {12, 31}, {13, 31}, {14, 31}, {15, 31}, {23, 31}, {26, 31}, {38, 31}, {57, 31}, {58, 31}, {59, 31}, {60, 31}, {61, 31}, {62, 31}, 
            {0, 32}, {1, 32}, {2, 32}, {3, 32}, {4, 32}, {5, 32}, {6, 32}, {7, 32}, {8, 32}, {9, 32}, {10, 32}, {11, 32}, {12, 32}, {13, 32}, {14, 32}, {15, 32}, {23, 32}, {26, 32}, {38, 32}, {57, 32}, {58, 32}, {59, 32}, {60, 32}, {61, 32}, {62, 32}, {0, 33}, {1, 33}, {2, 33}, {3, 33}, {4, 33}, {5, 33}, {6, 33}, {7, 33}, {8, 33}, {9, 33},  {10, 33}, {11, 33}, {12, 33}, {13, 33}, {14, 33}, {15, 33}, {37, 33}, {57, 33}, {58, 33}, {59, 33}, {60, 33}, {61, 33}, {62, 33}, {0, 34}, {1, 34}, {2, 34}, {3, 34}, {4, 34}, {5, 34}, {6, 34}, {7, 34}, {8, 34}, {9, 34}, {10, 34}, {11, 34}, {12, 34}, {13, 34}, {14, 34}, {15, 34}, {16, 34}, {24, 34}, {25, 34}, {56, 34}, {57, 34}, {58, 34}, {59, 34}, {60, 34}, {61, 34}, {62, 34}, {0, 35}, {1, 35}, {2, 35}, {3, 35}, {4, 35}, {5, 35}, {6, 35}, {7, 35}, {8, 35}, {9, 35}, {10, 35}, {11, 35}, {12, 35}, {13, 35}, {14, 35}, {15, 35},
            {16, 35}, {56, 35}, {57, 35}, {58, 35}, {59, 35}, {60, 35}, {61, 35}, {62, 35}, {0, 36}, {1, 36}, {2, 36}, {3, 36}, {4, 36}, {5, 36}, {6, 36}, {7, 36}, {8, 36}, {9, 36}, {10, 36}, {11, 36}, {12, 36}, {13, 36}, {14, 36}, {15, 36}, {16, 36}, {17, 36}, {18, 36}, {19, 36}, {20, 36}, {21, 36}, {25, 36}, {26, 36}, {27, 36}, {28, 36}, {29, 36}, {30, 36}, {31, 36}, {32, 36}, {33, 36}, {34, 36}, {35, 36}, {36, 36}, {37, 36}, {38, 36}, {39, 36}, {40, 36}, {41, 36}, {42, 36}, {43, 36}, {44, 36}, {45, 36}, {46, 36}, {47, 36}, {48, 36}, {49, 36}, {50, 36}, {51, 36}, {52, 36}, {53, 36}, {54, 36}, {55, 36}, {56, 36}, {57, 36}, {58, 36}, {59, 36}, {60, 36}, {61, 36}, {62, 36}, {33,18}, {33,19}, {34,23}, {34,22}, {34,21}, {34,20}, {34,19}, {34,18}, {34,17}, {34,22}, {35,22}, {35,21}, {35,20}, {35,19}, {35,18}, {35,17}, {40,24}, {29, 28}, {29, 29}, {29, 30}, {29, 31}, {29, 32},
            {30,27}, {30,28}, {30,29}, {30,30}, {30,31}, {30,32}, {30,33}, {31,27}, {31,28}, {31,29}, {31,30}, {31,31}, {31,32}, {31,33}, {32,27}, {32,28}, {32,29}, {32,30}, {32,31}, {32,32}, {32,33}, {32,34}, {33,27}, {33,28}, {33,29}, {33,30}, {33,31}, {33,32}, {33,33}, {33,34}, {34,27}, {34,28}, {34,29}, {34,30}, {34,31}, {34,32}, {34,33}, {35,27}, {35,28}, {35,29}, {35,30}, {35,31}, {35,32}, {35,33}, {36,28}, {36,29}, {36,30}, {36,31}, {36,32}, {38,33}, {27,28}, {27,29}, {27,30}, {27,31}, {27,32}, {27,33}, {28,28}, {28,29}, {28,30}, {28,31}, {28,32}, {28,33}, {29,33}, {26,34}, {37,21}, {37,22}, {37,23}, {37,24},{36,20},{36,21}, {36,22}, {36,23}, {36,24}, {36,25}, {26,9}, {22,13}, {29,9},{30,8}, {24,10}, {23,17}, {37,7},
            {36,17}, {36,18}, {36,19}, {34,16}, {33,16}, {33,17}, {32,17}, {32,18}, {31,18}, {30,19}, {28,20}, {29,20}, {27,21}, {46,14}, {22,20}, {17,20}, {26,21}, {39,16}, {39,17}, {39,18}, {39,19}, {38,18}, {38,19}, {38,20}, {40,17}, {41,16}, {42,16}, {44,16}, {45,16}, {43,16}, {37,19}, {37,17}, {56,14}, {48,17}, {47,17}, {46,18}, {45,19}, {44,19}, {43,20}, {49,17}, {47,19}, {46,19}, {47,18}, {42,21}, {41,22}, {40,23}, {39,25}, {38,26}, {20,14}, {20,15}, {20,16}, {18,17}, {18,18}, {19,18}, {20,18}, {42,22}, {55,11}, {56,11}, {43,11},{39,11}, {38,11}, {41,7}, {32,9}, {32,8}, {31,8}, {26,7}, {25,6}, {23,11}, {22,12}, {23,12}, {22,11}, {22,10}, {15,20}, {20,20}, {21,20}, {23,16}, {52,10}, {21,7}, {21,8}, {22,8},{22,7}, {35,5},{36,5}, {33,23},{31,24}, {38,22}
        }; 

    public pupmap() {
        // Constructor: initialize map and event listeners
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setLayout(new BorderLayout());

        try {
            mapImage = ImageIO.read(getClass().getResource("/applimap/PUP Map.jpg"));

            if (mapImage != null) {
                System.out.println("Image loaded successfully."); // Check if this prints
                // Scale the image
                mapImage = mapImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
            } else {
                System.err.println("Failed to load image."); // Check if this prints
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize nodes
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw map image
                if (mapImage != null) {
                    g.drawImage(mapImage, 0, 0, null);
                }

                // Draw nodes
                for (int col = 0; col < maxCol; col++) {
                    for (int row = 0; row < maxRow; row++) {
                        nodes[col][row].paintComponent(g);
                    }
                }

                // Draw path
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setStroke(new BasicStroke(3)); // Set path line thickness
                g2.setColor(Color.BLUE); // Set path color
                for (int i = 0; i < pathList.size() - 1; i++) {
                    Node current = pathList.get(i);
                    Node next = pathList.get(i + 1);
                    g2.drawLine(current.col * nodeSize + nodeSize / 2, current.row * nodeSize + nodeSize / 2,
                            next.col * nodeSize + nodeSize / 2, next.row * nodeSize + nodeSize / 2);
                }
                g2.dispose();
            }
        };
        centerPanel.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Ensure the panel size matches the map size
        centerPanel.setOpaque(false); // Make the panel transparent to see the background
        centerPanel.setLayout(null); // Use null layout for precise node positioning
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                nodes[col][row] = new Node(col, row);
                nodes[col][row].setBounds(col * nodeSize, row * nodeSize, nodeSize, nodeSize);
                centerPanel.add(nodes[col][row]);
            }
        }

        
        // SET SOLID NODES
        setSolidNodes(solidNodes);

        // Add key listener for triggering pathfinding
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    autoSearch();
                }
            }
        });

        // Add centerPanel to this panel with a scrollbar
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setPreferredSize(new Dimension(screenWidth, screenHeight));
        add(scrollPane, BorderLayout.CENTER);

        // Add "Again" and "End" buttons together in a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        JButton againButton = new JButton("AGAIN");
        againButton.setFont(new java.awt.Font("Segoe UI Black", 0, 14));
        againButton.setBackground(new java.awt.Color(255, 153, 0));
        againButton.setForeground(Color.white);
        againButton.addActionListener((ActionEvent e) -> {
            reset();
        });
        buttonPanel.add(againButton);

        JButton endButton = new JButton("END");
        endButton.setBackground(new java.awt.Color(255, 0, 0));
        endButton.setFont(new java.awt.Font("Segoe UI Black", 0, 14));
        endButton.setForeground(Color.WHITE);
        endButton.addActionListener((ActionEvent e) -> {
            System.exit(0); // Exit the program
        });
        buttonPanel.add(endButton);

        // Add the button panel to the south of pupmap
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void setNodesBasedOnSelection(String startLocation, String goalLocation) {
        switch (startLocation) {
            case "Starting Point (Main Gate)":
                setStartNode(56,13);
                break;
            case "Amphitheater":
                setStartNode(25,19);
                break;
            case "Property Building":
                setStartNode(6,0);
                break;
            case "Printing Press Building":
                setStartNode(5,3);
                break;
            case "Laboratory High School":
                setStartNode(18,9);
                break;
            case "Charlie del Rosario Hall":
                setStartNode(18,15);
                break;
            case "Student Canteen":
                setStartNode(15,16);
                break;
            case "University Canteen Sampaguita Building":
                setStartNode(14,18);
                break;
            case "Linear Park":
                setStartNode(12,21);
                break;
            case "PUP Sta. Mesa Ferry Station":
                setStartNode(17,34);
                break;
            case "Campus Development and Maintenance Building":
                setStartNode(23,36);
                break;
            case "Interfaith Chapel":
                setStartNode(34,26);
                break;
            case "Main Building":
                setStartNode(25,25); //
                break;
            case "East Wing":
                setStartNode(24,30);
                break;
            case "South Wing":
                setStartNode(18,28);
                break;
            case "North Wing":
                setStartNode(30,22);
                break;
            case "West Wing":
                setStartNode(19,22);
                break;
            case "Dome":
                setStartNode(24,25);
                break;
            case "Ninoy Aquino Library and Learning Resources Center":
                setStartNode(22,9);
                break;
            case "Lagoon":
                setStartNode(29,13);
                break;
            case "Physical Education Building":
                setStartNode(32,4);
                break;
            case "Tahanan ng Alumni":
                setStartNode(34,3);
                break;
            case "Swimming Pool":
                setStartNode(35,6);
                break;
             case "Gymnasium and Sports Center":
                setStartNode(50,6);
                break;
            case "Tennis Court":
                setStartNode(41,15);
                break;
            case "Visitor Information Center":
                setStartNode(53,13);
                break;
            case "Covered Walkway":
                setStartNode(51,15);
                break;
            case "Guard House (Entrance)":
                setStartNode(56,13);
                break;
            case "Guard House (Exit)":
                setStartNode(55,16);
                break;
            case "Community Building Gabriela Silang":
                setStartNode(54,29);
                break;
            case "Track and Football Oval":
                setStartNode(48,26);
                break;
            case "Grandstand":
                setStartNode(43,22);
                break;
            case "Gazebo (Souvenir Shop)":
                setStartNode(42,15);
                break;
            case "Apolinario Mabini Shrine":
                setStartNode(38,17);
                break;
            case "Freedom Park":
                setStartNode(35,17);
                break;
            case "Flag Pole":
                setStartNode(34,19);
                break;
            case "Mabini Monument P.E. Obelisk":
                setStartNode(38,14);
                break;
            case "Nutrition and Food Science Building":
                setStartNode(33,35);
                break;
            default:
                setStartNode(56,13);
                break;
        }

        switch (goalLocation) {
            case "Starting Point (Main Gate)":
                setGoalNode(56,13);
                break;
            case "Amphitheater":
                setGoalNode(25,19);
                break;
            case "Property Building":
                setGoalNode(6,0);
                break;
            case "Printing Press Building":
                setGoalNode(5,3);
                break;
            case "Laboratory High School":
                setGoalNode(18,9);
                break;
            case "Charlie del Rosario Hall":
                setGoalNode(18,15);
                break;
            case "Student Canteen":
                setGoalNode(15,16);
                break;
            case "University Canteen Sampaguita Building":
                setGoalNode(14,18);
                break;
            case "Linear Park":
                setGoalNode(12,21);
                break;
            case "PUP Sta. Mesa Ferry Station":
                setGoalNode(17,34);
                break;
            case "Campus Development and Maintenance Building":
                setGoalNode(23,36);
                break;
            case "Interfaith Chapel":
                setGoalNode(34,26);
                break;
            case "Main Building":
                setGoalNode(25,25); //
                break;
            case "East Wing":
                setGoalNode(24,30);
                break;
            case "South Wing":
                setGoalNode(18,28);
                break;
            case "North Wing":
                setGoalNode(30,22);
                break;
            case "West Wing":
                setGoalNode(19,22);
                break;
            case "Dome":
                setGoalNode(24,25);
                break;
            case "Ninoy Aquino Library and Learning Resources Center":
                setGoalNode(22,9);
                break;
            case "Lagoon":
                setGoalNode(29,13);
                break;
            case "Physical Education Building":
                setGoalNode(32,4);
                break;
            case "Tahanan ng Alumni":
                setGoalNode(34,3);
                break;
            case "Swimming Pool":
                setGoalNode(35,6);
                break;
             case "Gymnasium and Sports Center":
                setGoalNode(50,6);
                break;
            case "Tennis Court":
                setGoalNode(41,15);
                break;
            case "Visitor Information Center":
                setGoalNode(53,13);
                break;
            case "Covered Walkway":
                setGoalNode(51,15);
                break;
            case "Guard House (Entrance)":
                setGoalNode(56,13);
                break;
            case "Guard House (Exit)":
                setGoalNode(55,16);
                break;
            case "Community Building Gabriela Silang":
                setGoalNode(54,29);
                break;
            case "Track and Football Oval":
                setGoalNode(48,26);
                break;
            case "Grandstand":
                setGoalNode(43,22);
                break;
            case "Gazebo (Souvenir Shop)":
                setGoalNode(42,15);
                break;
            case "Apolinario Mabini Shrine":
                setGoalNode(38,17);
                break;
            case "Freedom Park":
                setGoalNode(35,17);
                break;
            case "Flag Pole":
                setGoalNode(34,19);
                break;
            case "Mabini Monument P.E. Obelisk":
                setGoalNode(38,14);
                break;
            case "Nutrition and Food Science Building":
                setGoalNode(33,35);
                break;
            default:
                setGoalNode(24,23);
                break;
        }

        autoSearch();
    }

    public void autoSearch() {
        calculateCosts();
        pathList = new ArrayList<>(aStarSearch());
        repaint();
    }

public void reset() {
    // Switch to selectionmap panel
    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
    if (frame instanceof selectionmap) {
        ((selectionmap) frame).showSelectionPanel(); // Custom method to switch to selection panel
    }

    // Reset the start and goal nodes
    if (startNode != null) {
        startNode.clearState();
        startNode = null;
    }
    if (goalNode != null) {
        goalNode.clearState();
        goalNode = null;
    }

    // Reset all nodes in the grid
    for (int col = 0; col < nodes.length; col++) {
        for (int row = 0; row < nodes[col].length; row++) {
            nodes[col][row].clearState(); // Assuming clearState method is defined in Node class
            // Reapply solid state if it was originally solid
            for (int[] solidNode : solidNodes) {
                if (solidNode[0] == col && solidNode[1] == row) {
                    nodes[col][row].setAsSolid();
                }
            }
        }
    }

    // Recalculate the path if both start and goal nodes are set
    if (startNode != null && goalNode != null) {
        autoSearch();
    }
    // Repaint the panel to reflect changes
    repaint();
}

    private void setStartNode(int col, int row) {
        if (isValidNode(col, row)) {
            if (startNode != null) {
                startNode.clearState();
            }
            startNode = nodes[col][row];
            startNode.setAsStart();
        }
    }

    private void setGoalNode(int col, int row) {
        if (isValidNode(col, row)) {
            if (goalNode != null) {
                goalNode.clearState();
            }
            goalNode = nodes[col][row];
            goalNode.setAsGoal();
        }
    }

    private void setSolidNodes(int[][] solidNodes) {
        for (int[] solidNode : solidNodes) {
            int col = solidNode[0];
            int row = solidNode[1];
            nodes[col][row].setAsSolid();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("PUP Map");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLayout(new BorderLayout());
            pupmap panel = new pupmap();
            frame.add(panel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            panel.requestFocus();
        });
    }

    

    // ASTAR COMPUTATION
    
    // Calculate costs for all nodes
    private void calculateCosts() {
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                nodes[col][row].calculateCost(startNode, goalNode);
            }
        }
    }

    // Implementation of the A* Algorithm
    public List<Node> aStarSearch() {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(node -> node.fCost));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();

            if (currentNode == goalNode) {
                return reconstructPath(currentNode);
            }

            currentNode.closed = true;
            getNeighbors(currentNode).stream().filter((neighbor) -> !(neighbor.closed || neighbor.solid)).forEachOrdered((neighbor) -> {
                double tentativeGCost = currentNode.gCost + calculateDistance(currentNode, neighbor);
                if (tentativeGCost < neighbor.gCost || !openList.contains(neighbor)) {
                    neighbor.parent = currentNode;
                    neighbor.gCost = tentativeGCost;
                    neighbor.calculateCost(startNode, goalNode);
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            });
        }

        return Collections.emptyList();
    }

    // Establishing the Shortest Path
    private List<Node> reconstructPath(Node goalNode) {
        List<Node> path = new ArrayList<>();
        Node currentNode = goalNode;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }

        Collections.reverse(path);
        return path;
    }
    
    // Calculating the Heuristic (Euclidean Distance)
    private double calculateDistance(Node node1, Node node2) {
        int dx = Math.abs(node2.col - node1.col);
        int dy = Math.abs(node2.row - node1.row);
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    // Checking / Evaluation of the Neighbor Nodes
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int col = node.col;
        int row = node.row;

        if (isValidNode(col, row - 1)) {
            neighbors.add(nodes[col][row - 1]);
        }
        if (isValidNode(col, row + 1)) {
            neighbors.add(nodes[col][row + 1]);
        }
        if (isValidNode(col - 1, row)) {
            neighbors.add(nodes[col - 1][row]);
        }
        if (isValidNode(col + 1, row)) {
            neighbors.add(nodes[col + 1][row]);
        }

        if (isValidNode(col - 1, row - 1)) {
            neighbors.add(nodes[col - 1][row - 1]);
        }
        if (isValidNode(col + 1, row - 1)) {
            neighbors.add(nodes[col + 1][row - 1]);
        }
        if (isValidNode(col - 1, row + 1)) {
            neighbors.add(nodes[col - 1][row + 1]);
        }
        if (isValidNode(col + 1, row + 1)) {
            neighbors.add(nodes[col + 1][row + 1]);
        }

        return neighbors;
    }
    
    // Node Validation
    private boolean isValidNode(int col, int row) {
        return col >= 0 && col < maxCol && row >= 0 && row < maxRow;
    }
}