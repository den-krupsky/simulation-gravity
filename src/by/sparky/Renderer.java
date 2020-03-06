package by.sparky;

import by.sparky.graphic.Graphics2DObject;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class Renderer extends JPanel {
    private Collection<Graphics2DObject> objects2D;

    public Renderer(Collection<Graphics2DObject> objects2D) {
        this.objects2D = objects2D;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        objects2D.forEach(o2D -> o2D.render(g2d));
    }
}
