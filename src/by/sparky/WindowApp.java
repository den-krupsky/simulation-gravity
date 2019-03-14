package by.sparky;

import javafx.scene.shape.Ellipse;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

public class WindowApp extends JFrame {

    public void drawShapes() {
        setSize(new Dimension(320, 320));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel p = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                Shape circle = new Ellipse2D.Double(100, 100, 10, 10);
                g2.draw(circle);
            }
        };
        setTitle("My Shapes");
        this.getContentPane().add(p);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                new WindowApp().drawShapes();
            }
        });
    }
}
