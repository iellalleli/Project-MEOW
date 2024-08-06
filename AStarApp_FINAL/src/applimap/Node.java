package applimap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import javax.swing.JButton;

// Class representing a node in a grid, which extends JButton to be displayed in a GUI
public class Node extends JButton { 
    int col;
    int row;
    double gCost;
    double hCost;
    double fCost;
    Node parent;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;
    boolean path;
    boolean closed;

    // Inner class to represent a line for visualizing the path
    private static class Line {
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }
    
    // List to store lines for path visualization
    private final LinkedList<Line> lines = new LinkedList<>();
    
    // Method to add a line with a specified color for path visualization
    public void addLine(int x1, int y1, int x2, int y2, Color color) {
        lines.add(new Line(x1, y1, x2, y2, color));
        repaint();
    }
    
    // Method to clear all lines
    public void clearLines() {
        lines.clear();
        repaint();
    }
    
    // Constructor to initialize a node with column and row position
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
        this.gCost = 0;
        this.hCost = 0;
        this.fCost = 0;
        this.parent = null;

        setBackground(new Color(255, 255, 255, 0)); // Transparent background
        setBorderPainted(false);
        setFocusPainted(false);
    }

    @Override
    public boolean contains(int x, int y) {
        // Disable hover effect by always returning false
        return false;
    }

    
    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Set the color based on the state
    if (solid) {
        g2.setColor(new Color(255, 255, 255, 0)); // Semi-transparent black
        g2.fillRect(0, 0, getWidth(), getHeight());
    } else if (start || goal) {
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 0, getWidth(), getHeight());
    } else if (checked || open) {
        g2.setColor(new Color(255, 255, 255, 0)); // Fully transparent
        g2.fillRect(0, 0, getWidth(), getHeight());
    } else if (path) {
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, getWidth(), getHeight());
    } else {
        g2.setColor(new Color(255, 255, 255, 0)); // Fully transparent
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    // Draw lines for the path
    for (Line line : lines) {
        g2.setColor(line.color);
        g2.drawLine(line.x1, line.y1, line.x2, line.y2);
    }

    // Set the text for start and goal nodes
    if (start || goal) {
        g2.setColor(Color.WHITE); // Set text color to white for visibility
        g2.setFont(new Font("Serif", Font.BOLD, 16));
        String text = start ? "Start" : "Goal";
        int textWidth = g2.getFontMetrics().stringWidth(text);
        int textHeight = g2.getFontMetrics().getHeight();
        // Draw the text in the center of the node
        g2.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - g2.getFontMetrics().getDescent());
    }

    // Display fCost and gCost
    if (!start && !goal) {
        String fCostStr = String.format("%.3f", fCost);
        String gCostStr = String.format("%.3f", gCost);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
      //  g2.drawString("F: " + fCostStr, 2, 12);
      //  g2.drawString("G: " + gCostStr, 2, 24);
      }
}

    // Method to calculate gCost, hCost, and fCost for pathfinding
    public void calculateCost(Node startNode, Node goalNode) {
        // Calculate gCost (distance from startNode)
        this.gCost = calculateEuclideanDistance(this, startNode);

        // Calculate hCost (distance to goalNode)
        this.hCost = calculateEuclideanDistance(this, goalNode);

        // Calculate fCost
        this.fCost = this.gCost + this.hCost;
    }

    public void setAsStart() {
        start = true;
        repaint();
    }

    public void setAsGoal() {
        goal = true;
        repaint();
    }

    public void setAsSolid() {
        solid = true;
        repaint();
    }

    public void setAsOpen() {
        open = true;
        repaint();
    }

    public void setAsChecked() {
        checked = true;
        repaint();
    }

    public void setAsPath() {
        path = true;
        if (parent != null) {
            // Remove existing lines (if any) before drawing the path line
            clearLines();
            addLine(getWidth() / 2, getHeight() / 2,
                    parent.getWidth() / 2, parent.getHeight() / 2, Color.YELLOW);
        }
        repaint();
    }

    public boolean isSolid() {
        return this.solid;
    }

    public void clearState() {
        start = false;
        goal = false;
        closed = false;
        gCost = Double.POSITIVE_INFINITY;
        hCost = 0;
        fCost = 0;
        parent = null;
    }

    // Method to calculate Euclidean distance
    private static double calculateEuclideanDistance(Node node1, Node node2) {
        double xDistance = Math.abs(node1.col - node2.col);
        double yDistance = Math.abs(node1.row - node2.row);
        return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }
    
    // Method to calculate the Euclidean distance to another node
    Double getEuclideanDistance(Node target) {
    int dx = this.col - target.col;
    int dy = this.row - target.row;
    return Math.sqrt(dx * dx + dy * dy);
}
    

}