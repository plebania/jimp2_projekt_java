package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class PrzyciskOkragly extends JButton {


    private boolean mouseOver = false;
    private boolean mousePressed = false;

    public PrzyciskOkragly() {
        //super("test");
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);

        MouseAdapter mouseListener = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent me) {
                if (contains(me.getX(), me.getY())) {
                    mousePressed = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                mousePressed = false;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                mousePressed = false;
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                mouseOver = contains(me.getX(), me.getY());
                repaint();
            }
        };

        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    private int getSrednica() {
        return Math.min(getWidth(), getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        FontMetrics metrics = getGraphics().getFontMetrics(getFont());
        int minDiameter = 10 + Math.max(metrics.stringWidth(getText()), metrics.getHeight());
        return new Dimension(minDiameter, minDiameter);
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = getSrednica() / 2;
        return Point2D.distance(x, y, getWidth() / 2, getHeight() / 2) < radius;
    }

    @Override
    public void paintComponent(Graphics g) {

        int diameter = getSrednica();
        int radius = diameter / 2;

        Ellipse2D.Double kolko = new Ellipse2D.Double(getWidth() / 2 - radius + 2, getHeight() / 2 - radius + 2, diameter - 4, diameter - 4);
        Graphics2D test = (Graphics2D) g;

        if (mousePressed) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(Color.WHITE);
        }
        Ellipse2D.Double kolko1 = new Ellipse2D.Double(getWidth() / 2 - radius, getHeight() / 2 - radius, diameter, diameter);
        test.fill(kolko1);

        if (mouseOver) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.BLACK);
        }

        test.fill(kolko);
    }
}
