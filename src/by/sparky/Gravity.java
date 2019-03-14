package by.sparky;

public class Gravity implements Interaction {

    private static final double G = 6.674d * Math.pow(10,-11);

    @Override
    public void calculate(PhysicObject[] physicObjects, double dTime) {
        dTime = dTime;

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
