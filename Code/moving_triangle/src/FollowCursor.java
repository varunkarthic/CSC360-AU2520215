import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FollowCursor extends JPanel {

    // Position of the centre of the triangle
    private int mouseX = 400;
    private int mouseY = 300;

    // Length of each side of the equilateral triangle
    private final int SIDE = 100;

    public FollowCursor() {

        // White background
        setBackground(Color.WHITE);

        // Listen for mouse movement
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Makes the edges look smoother
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Height of an equilateral triangle
        double height = Math.sqrt(3) / 2 * SIDE;

        /*
         * Position the triangle so that its CENTRE
         * is located at the mouse cursor.
         */

        int topX = mouseX;
        int topY = (int) (mouseY - (2.0 / 3.0) * height);

        int leftX = mouseX - SIDE / 2;
        int leftY = (int) (mouseY + (1.0 / 3.0) * height);

        int rightX = mouseX + SIDE / 2;
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

        // Fill triangle red
        g2.setColor(Color.RED);
        g2.fillPolygon(xPoints, yPoints, 3);

        // Draw black outline
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawPolygon(xPoints, yPoints, 3);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Moving Triangle");

        FollowCursor panel = new FollowCursor();

        frame.add(panel);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}