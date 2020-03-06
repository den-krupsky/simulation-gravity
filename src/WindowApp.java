import graphic.CircleObject;
import physic.PhysicObject;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WindowApp {

    public static void main(String[] args) {
        DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

        int count = args.length > 0 ? Integer.parseInt(args[0]) : 10;
        SimulationService simulationService = initialize(count, dm.getWidth(), dm.getHeight());
        simulationService.run();
    }

    private static SimulationService initialize(int count, int width, int height) {
        List<PhysicObject> generated = Arrays.asList(generate(count, width, height));

        SimulationService simulationService = new SimulationService();
        simulationService.add(generated, (CircleObject::new));

        JFrame frame = new JFrame("Gravity simulator");
        frame.add(simulationService.getView(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        return simulationService;
    }

    private static PhysicObject[] generate(int count, int width, int height) {
        PhysicObject[] physicObjects = new PhysicObject[count];
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        double mass, initX, initY;
        for (int i = 0; i < physicObjects.length; i++) {
            mass = 20000.0d + tlr.nextDouble() * (20000 * 3);
            initX = tlr.nextInt(width / 4, width / 4 * 3) * tlr.nextDouble();
            initY = tlr.nextInt(height / 4, height / 4 * 3) * tlr.nextDouble();
            physicObjects[i] = new PhysicObject(mass, initX, initY);
        }
        return physicObjects;
    }

}
