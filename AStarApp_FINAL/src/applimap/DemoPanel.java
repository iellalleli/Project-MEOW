package applimap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DemoPanel extends JPanel {

    private final int nodeSize;
    private final int maxCol;
    private final int maxRow;
    private final Node[][] nodes;
    private final ArrayList<Node> pathList;
    private Image mapImage;
    private Stroke stroke;

    public DemoPanel(int nodeSize, int maxCol, int maxRow, Node[][] nodes, ArrayList<Node> pathList) {
        this.nodeSize = nodeSize;
        this.maxCol = maxCol;
        this.maxRow = maxRow;
        this.nodes = nodes;
        this.pathList = pathList;
        this.setPreferredSize(new Dimension(maxCol * nodeSize, maxRow * nodeSize));
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw the map image
        if (mapImage != null) {
            g2.drawImage(mapImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Draw the nodes
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                nodes[col][row].paintComponent(g2);
            }
        }

        // Draw the path (if needed)
        if (!pathList.isEmpty()) {
            g2.setColor(Color.BLACK);
            g2.setStroke(stroke);
            for (int i = 1; i < pathList.size(); i++) {
                int x1 = pathList.get(i - 1).col * nodeSize + nodeSize / 2;
                int y1 = pathList.get(i - 1).row * nodeSize + nodeSize / 2;
                int x2 = pathList.get(i).col * nodeSize + nodeSize / 2;
                int y2 = pathList.get(i).row * nodeSize + nodeSize / 2;
                g2.drawLine(x1, y1, x2, y2);
            }
        }
    }
}
