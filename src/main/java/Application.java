import graphic.CircleShape;
import physic.PhysicalAgent;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Application {

    public static void main(String[] args) {
        DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

        int count = args.length > 0 ? Integer.parseInt(args[0]) : 100;
        SimulationService simulationService = initialize(count, dm.getWidth(), dm.getHeight());
        simulationService.run();
    }

    private static SimulationService initialize(int count, int width, int height) {
        List<PhysicalAgent> generated = Arrays.asList(generate(count, width, height));

        SimulationService simulationService = new SimulationService();
        simulationService.add(generated, (CircleShape::new));

        JFrame frame = new JFrame("Gravity simulator");
        frame.add(simulationService.getView(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        return simulationService;
    }

    private static PhysicalAgent[] generate(int count, int width, int height) {
        PhysicalAgent[] physicalAgents = new PhysicalAgent[count];
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        double mass, initX, initY;
        for (int i = 0; i < physicalAgents.length; i++) {
            mass = 20000.0d + tlr.nextDouble() * (20000 * 3);
            initX = tlr.nextDouble(0, width);
            initY = tlr.nextDouble(0, height);
            physicalAgents[i] = new PhysicalAgent(mass, initX, initY);
        }
        return physicalAgents;
    }

}
