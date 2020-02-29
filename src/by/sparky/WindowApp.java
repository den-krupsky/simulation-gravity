package by.sparky;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class WindowApp {
    private final JFrame frame;

    public WindowApp(JFrame frame) {
        this.frame = frame;
    }

    private PhysicObject[] generate() {
        PhysicObject[] physicObjects = new PhysicObject[30];
        Random random = new Random();
        Random random1 = new Random();
        for (int i = 0; i < physicObjects.length; i++) {
            physicObjects[i] = new PhysicObject(10000.0d + random.nextDouble() * 20000, random.nextDouble() * random1.nextInt(1366), random1.nextDouble() * random.nextInt(768));
        }
        return physicObjects;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gravitation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        WindowApp windowApp = new WindowApp(frame);
        windowApp.start();
    }

    private void start() {
        PhysicObject[] physicObjects = generate();

        PhysicsEngine physicsEngine = new PhysicsEngine();
        physicsEngine.setPhysicObject(physicObjects);
        physicsEngine.setInteractions(new Gravity());

        ViewPhysicObject view = new ViewPhysicObject(physicObjects);

        view.setSize(frame.getWidth(), frame.getHeight());
        frame.add(view, BorderLayout.CENTER);
        frame.setVisible(true);

        physicsEngine.simulate();

        while (true) {
            view.repaint();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
