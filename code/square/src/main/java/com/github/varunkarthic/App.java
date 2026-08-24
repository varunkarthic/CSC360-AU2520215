package com.github.varunkarthic;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class App extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Draw a blue square outline
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(3));

        // (x, y, width, height)
        g2d.drawRect(200, 100, 200, 200);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Square");
        App panel = new App();

        frame.add(panel);
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}