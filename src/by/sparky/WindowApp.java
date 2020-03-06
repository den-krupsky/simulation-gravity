package by.sparky;

import by.sparky.graphic.CircleObject;
import by.sparky.graphic.Graphics2DObject;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WindowApp {

    private static PhysicObject[] generate(int count) {
        PhysicObject[] physicObjects = new PhysicObject[count];
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        double mass, initX, initY;
        for (int i = 0; i < physicObjects.length; i++) {
            mass = 1000.0d + tlr.nextDouble() * 200000;
            initX = tlr.nextDouble() * tlr.nextInt(1366);
            initY = tlr.nextDouble() * tlr.nextInt(768);
            physicObjects[i] = new PhysicObject(mass, initX, initY);
        }
        return physicObjects;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gravity simulator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        PhysicObject[] generated = generate(30);

        GravitySimulation gravitySimulation = new GravitySimulation();
        gravitySimulation.physicObjects.addAll(Arrays.asList(generated));

        List<Graphics2DObject> shapes = Stream.of(generated)
                .map(CircleObject::new)
                .collect(Collectors.toList());
        Renderer renderer = new Renderer(shapes);
        renderer.setSize(frame.getWidth(), frame.getHeight());

        frame.add(renderer, BorderLayout.CENTER);
        frame.setVisible(true);

        gravitySimulation.simulate();

        while (true) {
            renderer.repaint();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
