package by.sparky;

import java.util.ArrayList;

public class Gravity implements Interaction {

    private static final double G = 6.674d * Math.pow(10,-11);
    private ArrayList<Vector2D> gravityAccelerations = new ArrayList<>(100);

    @Override
    public void calculate(PhysicObject[] physicObjects, double dTime) {
        dTime = dTime * 1000;

        if (true) {
                calculateDiff(physicObjects, dTime);
        } else {
            for (int i = 0; i < physicObjects.length; i++) {
                for (int j = 0; j < physicObjects.length; j++) {
                    if (i == j) continue;

                    double dx = physicObjects[j].x - physicObjects[i].x;
                    double dy = physicObjects[j].y - physicObjects[i].y;

                    double r = Math.hypot(dx, dy);
                    double a = G * physicObjects[j].mass / r;

                    r = Math.sqrt(r);

                    double ax = a * dx / r;
                    double ay = a * dy / r;

                    physicObjects[i].vx += ax * dTime;
                    physicObjects[i].vy += ay * dTime;
//                //F = G * m1 * m2 / r^2
//                //сила на проекцию оси Х
//                double r = physicObjects[i].x + physicObjects[j].x;
//                double preCalculate = G * physicObjects[i].mass * physicObjects[j].mass; // обобщить подсчёт
//                powersX[i] += preCalculate / r * r * Math.signum(r);
//                //сила на проекцию оси Y
//                r = physicObjects[i].y + physicObjects[j].y;
//                powersY[i] += preCalculate / r * r * Math.signum(r);
                }
            }

            for (int i = 0; i < physicObjects.length; i++) {
                physicObjects[i].x += dTime * physicObjects[i].vx;
                physicObjects[i].y += dTime * physicObjects[i].vy;
            }
        }

    }

    public void calculateDiff(PhysicObject[] physObj, double dTime) {

        //expand cached calculations size
        gravityAccelerations.ensureCapacity(physObj.length);

        //высчитываем силу, с которой объекты действуют друг на друга
        for (int i = 0; i < physObj.length; i++) {
            Vector2D gravityPowerResult = new Vector2D(0, 0); // for physObj[i]
            for (int j = 0; j < physObj.length; j++) {
                if (i == j) continue; // объекты не воздействую на себя, переходим к следующему
                gravityPowerResult.add(gravityPower(physObj[i].mass, physObj[j].mass, new Vector2D(physObj[i].x - physObj[j].x, physObj[i].y - physObj[j].y)));

            }
            //a = Fr / m
            gravityPowerResult.mul(Math.pow(physObj[i].mass, -1)); // calculate acceleration
            gravityAccelerations.add(i, gravityPowerResult);

            //TODO
            //gravityPowerResult.mul(dTime); // calculate to step by delta time
            physObj[i].velocity.add(gravityPowerResult); // vectors might be a negatives
        }

        for (int i = 0; i < physObj.length; i++) {
            Vector2D point = new Vector2D(physObj[i].x, physObj[i].y);
            physObj[i].velocity.mul(dTime);
            point.add(physObj[i].velocity);

            physObj[i].x = point.x;
            physObj[i].y = point.y;
        }
    }

    private Vector2D gravityPower(double m1, double m2, Vector2D r) {
        //F = G * m1 * m2 / r^2
        double c = G * m1 * m2;
        Vector2D gravPower = new Vector2D(1 / (r.x * r.x), 1/ (r.y * 1 /r.y));
        gravPower.mul(c);
        return gravPower;
    }

}
