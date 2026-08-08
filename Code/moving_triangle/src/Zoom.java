import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Zoom extends JPanel {

    // Current Y position of the mouse
    private int mouseY = 300;

    // Minimum and maximum triangle side lengths
    private final int MIN_SIZE = 30;
    private final int MAX_SIZE = 400;

    public Zoom() {

        setBackground(Color.WHITE);

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseY = e.getY();
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseY = e.getY();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Smooth edges
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int panelHeight = getHeight();

        // Prevent division by zero
        if (panelHeight <= 0) {
            return;
        }

        /*
         * Convert mouse Y position into triangle size.
         *
         * Top:
         * mouseY = 0
         * size = MAX_SIZE
         *
         * Bottom:
         * mouseY = panelHeight
         * size = MIN_SIZE
         */

        double ratio = (double) mouseY / panelHeight;

        // Keep ratio between 0 and 1
        ratio = Math.max(0, Math.min(1, ratio));

        int side = (int) (
                MAX_SIZE -
                ratio * (MAX_SIZE - MIN_SIZE)
        );

        // Height of an equilateral triangle
        double triangleHeight = Math.sqrt(3) / 2 * side;

        // Keep triangle centred in the window
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Calculate vertices using centroid as centre
        int topX = centerX;
        int topY = (int) (
                centerY - (2.0 / 3.0) * triangleHeight
        );

        int leftX = centerX - side / 2;
        int leftY = (int) (
                centerY + (1.0 / 3.0) * triangleHeight
        );

        int rightX = centerX + side / 2;
        int rightY = leftY;

        int[] xPoints = {
                topX,
                leftX,
                rightX
        };

        int[] yPoints = {
                topY,
                leftY,
                rightY
        };

        // Green fill
        g2.setColor(Color.GREEN);
        g2.fillPolygon(xPoints, yPoints, 3);

        // Black outline
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawPolygon(xPoints, yPoints, 3);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Triangle Zoom");

        Zoom panel = new Zoom();

        frame.add(panel);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}