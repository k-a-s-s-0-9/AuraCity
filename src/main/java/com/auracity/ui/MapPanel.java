package ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MapPanel extends JPanel {

    private final int TILE_SIZE = 32;
    private final int GRID_WIDTH = 50;
    private final int GRID_HEIGHT = 50;

    public MapPanel() {
        // Essential for smooth custom painting in Swing
        setDoubleBuffered(true);
        setBackground(new Color(30, 30, 34)); // Dark mode base
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the screen
        
        Graphics2D g2d = (Graphics2D) g;
        
        // Anti-aliasing for smooth citizen rendering later
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        renderGridLayer(g2d);
        renderBuilding(g2d);
        renderCitizen(g2d);
    }

    private void renderGridLayer(Graphics2D g2d) {
        g2d.setColor(new Color(50, 50, 55));
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                g2d.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void renderBuilding(Graphics2D g2d) {
        // Placeholder: Loop through your 2D array of buildings and draw them
        // Example building footprint
        g2d.setColor(new Color(100, 150, 200));
        g2d.fillRect(5 * TILE_SIZE, 5 * TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2);
    }

    private void renderCitizen(Graphics2D g2d) {
        // Placeholder: Loop through active citizens and draw small circles based on their current coords
        g2d.setColor(Color.ORANGE);
        g2d.fillOval((5 * TILE_SIZE) - 5, (5 * TILE_SIZE) + 10, 10, 10);
    }
    
    // This will be called by your Game Loop (e.g., a Swing Timer or your Tick Thread)
    public void refreshFrame() {
        repaint();
    }
}