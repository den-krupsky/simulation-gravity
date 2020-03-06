package by.sparky;

import java.util.*;

public class GravitySimulation {
    public static final double G = 6.674d * Math.pow(10, -11);

    public final List<PhysicObject> physicObjects;
    private TimerTask timeline;
    private long counter = 0;

    public GravitySimulation() {
        this(1 / 60d);
    }

    public GravitySimulation(double frameRate) {
        physicObjects = new ArrayList<>();
        timeline = new TimerTask() {
            @Override
            public void run() {
                simpleAlgorithm(physicObjects.toArray(new PhysicObject[0]), frameRate * ++counter * 10);
            }
        };
    }

    public List<PhysicObject> getPhysicObjects() {
        return physicObjects;
    }

    public void simulate() {
        Timer timer = new Timer(false);
        timer.schedule(timeline, 0, 1000 / 60);
    }

    protected static void simpleAlgorithm(PhysicObject[] objects, double dt) {
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects.length; j++) {
                if (i == j) continue;
                setVelocity(dt, objects[i], objects[j]);
            }
        }

        Arrays.stream(objects).parallel().forEach(object -> {
            object.x += dt * object.vx;
            object.y += dt * object.vy;
        });
    }

    private static void setVelocity(double dt, PhysicObject basic, PhysicObject other) {
        double dx = other.x - basic.x;
        double dy = other.y - basic.y;

        double r = Math.hypot(dx, dy);
        double a = G * other.mass / r;

        r = Math.sqrt(r);

        double ax = a * dx / r;
        double ay = a * dy / r;

        basic.vx += ax * dt;
        basic.vy += ay * dt;
    }

}
