package by.sparky;

import java.util.ArrayList;
import java.util.Arrays;

public class Gravity implements Interaction {

    public static final double G = 6.674d * Math.pow(10, -11);
    private static ArrayList<Vector2D> gravityAccelerations = new ArrayList<>(100);

    @Override
    public void calculate(PhysicObject[] objects, double dt) {
        dt = dt * 10;
        simpleAlgorithm(objects, dt);
    }

    public static void simpleAlgorithm(PhysicObject[] objects, double dt) {
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

    public static void algorithmWithVectors(PhysicObject[] objects, double dt) {
        //expand cached calculations size
        gravityAccelerations.ensureCapacity(objects.length);

        //высчитываем силу, с которой объекты действуют друг на друга
        for (int i = 0; i < objects.length; i++) {
            Vector2D resultingForce = new Vector2D(0, 0); // for objects[i]
            for (int j = 0; j < objects.length; j++) {
                if (i == j) continue; // объекты не воздействую на себя, переходим к следующему
                resultingForce.add(gravityForce(objects[i].mass, objects[j].mass, new Vector2D(objects[i].x - objects[j].x, objects[i].y - objects[j].y)));
            }
            //a = Fr / m
            resultingForce.mul(Math.pow(objects[i].mass, -1)); // calculate acceleration
            gravityAccelerations.add(i, resultingForce);

            //TODO
            //resultingForce.mul(dt); // calculate to step by delta time
            objects[i].velocity.add(resultingForce); // vectors might be a negatives
        }

        for (int i = 0; i < objects.length; i++) {
            Vector2D point = new Vector2D(objects[i].x, objects[i].y);
            objects[i].velocity.mul(dt);
            point.add(objects[i].velocity);

            objects[i].x = point.x;
            objects[i].y = point.y;
        }
    }

    private static Vector2D gravityForce(double m1, double m2, Vector2D r) {
        //F = G * m1 * m2 / r^2
        Vector2D result = new Vector2D(Math.pow(Math.sqrt(r.x), -1), Math.pow(Math.sqrt(r.y), -1));
        result.mul(G * m1 * m2);
        return result;
    }

}
