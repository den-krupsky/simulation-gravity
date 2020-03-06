package physic;

import java.util.List;

public final class Gravity {
    public static final double G = 6.674d * Math.pow(10, -11);

    public static void simpleAlgorithm(List<PhysicalAgent> objects, double dt) {
        for (int i = 0; i < objects.size(); i++) {
            for (int j = 0; j < objects.size(); j++) {
                if (i == j) continue; //exclude self
                setVelocity(dt, objects.get(i), objects.get(j));
            }
        }

        objects.parallelStream()
                .forEach(object -> {
                    object.x += dt * object.vx;
                    object.y += dt * object.vy;
                });
    }

    private static void setVelocity(double dt, PhysicalAgent basic, PhysicalAgent other) {
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

    private Gravity() {
    }
}
