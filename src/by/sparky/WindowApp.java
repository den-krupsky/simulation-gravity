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
    private final JFrame frame;
    private final GravitySimulation gravitySimulation;
    private final Renderer objectsView;

    public WindowApp(JFrame frame, int objectCount) {
        this.frame = frame;

        PhysicObject[] generated = generate(objectCount);
        List<Graphics2DObject> shapes = Stream.of(generated)
                .map(CircleObject::new)
                .collect(Collectors.toList());

        objectsView = new Renderer(shapes);
        gravitySimulation = new GravitySimulation();
        objectsView.setSize(frame.getWidth(), frame.getHeight());
        frame.add(objectsView, BorderLayout.CENTER);
        frame.setVisible(true);
        gravitySimulation.getPhysicObjects().addAll(Arrays.asList(generated));
    }

    private PhysicObject[] generate(int count) {
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

        WindowApp windowApp = new WindowApp(frame, 30);
        windowApp.start();
    }

    private void start() {
        gravitySimulation.simulate();

        while (true) {
            objectsView.repaint();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
