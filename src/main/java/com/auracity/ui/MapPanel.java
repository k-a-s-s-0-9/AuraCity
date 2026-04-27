package com.auracity.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MapPanel extends JPanel {

    private int TILE_SIZE = 32;
    private final int GRID_WIDTH = 50;
    private final int GRID_HEIGHT = 50;

    private double zoom = 1.0;

    private final List<RenderableBuilding> buildings = new ArrayList<>();
    private RenderableBuilding selectedBuilding = null;

    private Point hoveredTile = null;

    private final InfoPanel infoPanel;

    public MapPanel(InfoPanel panel) {

        this.infoPanel = panel;

        setDoubleBuffered(true);
        setBackground(new Color(20, 22, 28));

        // ---------------- MOUSE MOVE (HOVER) ----------------
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                hoveredTile = getTileFromMouse(e.getX(), e.getY());
                repaint();
            }
        });

        // ---------------- CLICK (SELECT BUILDING) ----------------
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                Point tile = getTileFromMouse(e.getX(), e.getY());

                selectedBuilding = null;

                for (RenderableBuilding b : buildings) {
                    if (b.contains(tile.x, tile.y)) {
                        selectedBuilding = b;
                        break;
                    }
                }

                if (selectedBuilding != null) {
                    infoPanel.showBuilding(selectedBuilding.building);
                } else {
                    infoPanel.showBuilding(null);
                }

                repaint();
            }
        });

        // ---------------- ZOOM ----------------
        addMouseWheelListener(e -> {

            if (e.getWheelRotation() < 0) {
                zoom += 0.1;
            } else {
                zoom -= 0.1;
            }

            zoom = Math.max(0.5, Math.min(2.0, zoom));

            repaint();
        });
    }

    // ---------------- ADD REAL BUILDINGS ----------------
    public void addBuilding(RenderableBuilding b) {
        buildings.add(b);
    }

    // ---------------- TILE CALCULATION ----------------
    private Point getTileFromMouse(int mx, int my) {

        int size = (int) (TILE_SIZE * zoom);

        int x = mx / size;
        int y = my / size;

        return new Point(x, y);
    }

    // ---------------- PAINT ----------------
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.scale(zoom, zoom);

        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        renderGrid(g2d);
        renderBuildings(g2d);
        renderHover(g2d);
        renderSelection(g2d);
        renderOverlay(g2d);
    }

    // ---------------- GRID ----------------
    private void renderGrid(Graphics2D g2d) {

        g2d.setColor(new Color(255, 255, 255, 20));

        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                g2d.drawRect(
                    x * TILE_SIZE,
                    y * TILE_SIZE,
                    TILE_SIZE,
                    TILE_SIZE
                );
            }
        }
    }

    // ---------------- BUILDINGS ----------------
    private void renderBuildings(Graphics2D g2d) {

        for (RenderableBuilding b : buildings) {

            int x = b.gridX * TILE_SIZE;
            int y = b.gridY * TILE_SIZE;

            // Building base
            g2d.setColor(new Color(80, 130, 200));
            g2d.fillRoundRect(x, y, TILE_SIZE * 2, TILE_SIZE * 2, 10, 10);

            // Border
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, y, TILE_SIZE * 2, TILE_SIZE * 2, 10, 10);

            // Label
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            g2d.drawString(
                b.building.getType().toString(),
                x + 5,
                y + 15
            );
        }
    }

    // ---------------- HOVER ----------------
    private void renderHover(Graphics2D g2d) {

        if (hoveredTile == null) return;

        g2d.setColor(new Color(255, 255, 255, 40));

        g2d.fillRect(
            hoveredTile.x * TILE_SIZE,
            hoveredTile.y * TILE_SIZE,
            TILE_SIZE,
            TILE_SIZE
        );
    }

    // ---------------- SELECTION ----------------
    private void renderSelection(Graphics2D g2d) {

        if (selectedBuilding == null) return;

        int x = selectedBuilding.gridX * TILE_SIZE;
        int y = selectedBuilding.gridY * TILE_SIZE;

        g2d.setColor(new Color(0, 255, 100));
        g2d.setStroke(new BasicStroke(3));

        g2d.drawRect(
            x,
            y,
            TILE_SIZE * 2,
            TILE_SIZE * 2
        );
    }

    // ---------------- OVERLAY ----------------
    private void renderOverlay(Graphics2D g2d) {

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Consolas", Font.BOLD, 14));

        g2d.drawString("AuraCity Simulation", 10, 20);
    }

    // ---------------- REFRESH ----------------
    public void refreshFrame() {
        repaint();
    }
}
