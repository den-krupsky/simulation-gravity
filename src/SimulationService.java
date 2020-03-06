import graphic.Graphics2DObject;
import physic.Gravity;
import physic.PhysicObject;

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
    public final List<PhysicObject> physicObjects = new ArrayList<>();
    private final AtomicInteger dt = new AtomicInteger(0);

    private final JPanel view;
    public final Collection<Graphics2DObject> objects2D = new ArrayList<>();
    private static int FRAME_RATE = 1000 / 60;

    private final ScheduledExecutorService ses;


    public SimulationService() {
        this.ses = Executors.newScheduledThreadPool(2);
        this.view = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                objects2D.forEach(o2d -> o2d.render(g2d));
            }
        };
    }

    public JPanel getView() {
        return view;
    }

    private void simulate() {
        System.out.println("dt: " + dt.get());

        Gravity.simpleAlgorithm(physicObjects, dt.getAndUpdate(i -> ++i));
    }

    public void add(List<PhysicObject> objects, Function<PhysicObject, Graphics2DObject> transformer) {
        physicObjects.addAll(objects);
        physicObjects.parallelStream()
                .map(Objects.requireNonNull(transformer, "transformer is null"))
                .collect(Collectors.toCollection(() -> objects2D));
    }

    @Override
    public void run() {
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
