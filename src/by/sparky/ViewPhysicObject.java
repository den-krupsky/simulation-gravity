package by.sparky;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ViewPhysicObject extends JPanel {
    private PhysicObject[] physicObjects;

    ViewPhysicObject(PhysicObject... physicObjects) {
        this.physicObjects = physicObjects;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderObjects((Graphics2D)g);
    }

    private void renderObjects(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        for (PhysicObject physicObject : physicObjects) {
            int size = (int)Math.round(physicObject.mass / 2000.0d);
            g2.fill(new Ellipse2D.Double(physicObject.x, physicObject.y, size, size));
        }

    }
}
