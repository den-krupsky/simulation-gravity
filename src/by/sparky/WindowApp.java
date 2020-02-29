package by.sparky;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WindowApp {
    private final JFrame frame;
    private final PhysicsEngine physicsEngine;
    private final ViewPhysicObject objectsView;
    private List<Interaction> interactionList = new ArrayList<>();

    public WindowApp(JFrame frame, int objectCount, Interaction... interactions) {
        this.frame = frame;

        PhysicObject[] generated = generate(objectCount);
        objectsView = new ViewPhysicObject(generated);
        physicsEngine = new PhysicsEngine();
        physicsEngine.setPhysicObject(generated);
        physicsEngine.setInteractions(interactions);
    }

    private PhysicObject[] generate(int count) {
        PhysicObject[] physicObjects = new PhysicObject[count];
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        double mass, initX, initY;
        for (int i = 0; i < physicObjects.length; i++) {
            mass = 10000.0d + tlr.nextDouble() * 20000;
            initX = tlr.nextDouble() * tlr.nextInt(frame.getWidth());
            initY = tlr.nextDouble() * tlr.nextInt(frame.getHeight());
            physicObjects[i] = new PhysicObject(mass, initX, initY);
        }
        return physicObjects;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gravitation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        WindowApp windowApp = new WindowApp(frame, 30, new Gravity());
        windowApp.addInteraction(new Gravity());
        windowApp.start();
    }

    private void addInteraction(Interaction interaction) {
        this.interactionList.add(interaction);
    }

    private void start() {
        objectsView.setSize(frame.getWidth(), frame.getHeight());
        frame.add(objectsView, BorderLayout.CENTER);
        frame.setVisible(true);

        physicsEngine.simulate();

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
