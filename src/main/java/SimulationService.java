import graphic.Rendered;
import physic.Gravity;
import physic.PhysicalAgent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimulationService implements Runnable {
    public final List<PhysicalAgent> physicalAgents = new ArrayList<>();
    private double dt = 1.0d;

    private final JPanel view;
    public final Collection<Rendered> objects2D = new ArrayList<>();

    private final ScheduledExecutorService ses;

    public SimulationService() {
        this.ses = Executors.newScheduledThreadPool(2);
        this.view = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                objects2D.forEach(o2d -> o2d.render(g2d));
            }
        };
    }

    public JPanel getView() {
        return view;
    }

    private void simulate() {
        dt += 1 / dt;
        Gravity.simpleAlgorithm(physicalAgents, dt);
    }

    public void add(List<PhysicalAgent> objects, Function<PhysicalAgent, Rendered> transformer) {
        physicalAgents.addAll(objects);
        physicalAgents.stream()
                .map(Objects.requireNonNull(transformer, "transformer is null"))
                .collect(Collectors.toCollection(() -> objects2D));
    }

    @Override
    public void run() {
        final int FRAME_RATE = 1000 / 60;
        ses.scheduleWithFixedDelay(this::simulate, 0, FRAME_RATE, TimeUnit.MILLISECONDS);
        ses.scheduleAtFixedRate(view::repaint, 0, FRAME_RATE, TimeUnit.MILLISECONDS);

        try {
            ses.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            ses.shutdownNow();
            System.err.println("Simulation error: " + e.getMessage());
        }
    }
}
