package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PrzyciskKrawedz extends JButton {

    private int x1, y1, x2, y2, grubosc;
    private boolean mouseOver = false;
    private boolean mousePressed = false;
    private Color kolorDomyslny = Color.WHITE, kolor = null;

    public PrzyciskKrawedz() {
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


    void setKolorDomyslny(Color k) {
        kolorDomyslny = k;
    }

    void setKolor(Color k) {
        kolor = k;
    }

    void clearKolor() {
        kolor = null;
    }

    public void setWymiary(int x1, int y1, int x2, int y2, int grubosc) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.grubosc = grubosc;
        this.setBounds(x1 - grubosc, y1 - grubosc, x2 - x1 + 2 * grubosc, y2 - y1 + 2 * grubosc);
    }

    /*
        @Override
        public Dimension getPreferredSize() {
            FontMetrics metrics = getGraphics().getFontMetrics(getFont());
            int minDiameter = 10 + Math.max(metrics.stringWidth(getText()), metrics.getHeight());
            return new Dimension(minDiameter, minDiameter);
        }
*/
    @Override
    public boolean contains(int x, int y) {
        if (x1 == x2)
            return (y <= y2 && y >= y1 && x >= x1 - grubosc / 2 && x <= x1 + grubosc / 2);
        if (y1 == y2)
            return (x <= x2 && x >= x1 && y >= y1 - grubosc / 2 && y <= y1 + grubosc / 2);

        double A1 = (y2 - y1) / (x2 - x1), B1 = -1, C1 = y1 - A1 * x1,
                A2 = -1 / A1, B2 = -1, C2 = (y2 + y1) / 2 - A2 * (x2 + x1) / 2;
        double polDl = Math.sqrt((x2 - x1) * (x2 - x1) / 4 + (y2 - y1) * (y2 - y1) / 4);
        return Math.abs(A1 * x + B1 * y + C1) / Math.sqrt(A1 * A1 + B1 * B1) < grubosc / 2 && Math.abs(A2 * x + B2 * y + C2) / Math.sqrt(A2 * A2 + B2 * B2) < polDl;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D test = (Graphics2D) g;

        /*if (mousePressed) {
            test.setColor(Color.RED);
        } else {
            test.setColor(Color.BLUE);
        }*/
        if (kolor != null)
            test.setColor(kolor);
        else
            test.setColor(kolorDomyslny);
        test.setStroke(new BasicStroke(grubosc));
        test.drawLine(grubosc, grubosc, x2 - x1 + grubosc, y2 - y1 + grubosc);
        //test.drawLine(x1, y1, x2, y2);
        //test.drawLine(x1, y1, x2, y2);


        /*if (mouseOver) {
            test.setColor(Color.BLUE);
        } else {
            test.setColor(Color.YELLOW);
        }*/

        //test.fill(kolko);
    }
}
