package by.sparky;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D() {
        this.x = 0.0d;
        this.y = 0.0d;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "{x = " + x + ", y = " + y + "}";
    }

    public void add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
    }

    public static Vector2D sum(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }

    public static double value(Vector2D v) {
        return Math.hypot(v.x, v.y);
    }

    public static Vector2D velocity(Vector2D v, Vector2D a, double deltaTime) {
        Vector2D velocity = new Vector2D();
        velocity.add(a);
        velocity.mul(deltaTime);
        velocity.add(v);
        return velocity;
    }

    public static Vector2D position(Vector2D oldPosition, Vector2D velocity, double deltaTime) {
        Vector2D position = new Vector2D();
        position.add(velocity);
        position.mul(deltaTime);
        position.add(oldPosition);
        return position;
    }

    public static Vector2D resultingForces(PhysicObject[] objects) {
        Vector2D[] forceVectors = new Vector2D[objects.length];

        double GN = Gravity.G * objects.length;

        for (int i = 0; i < objects.length; i++) {
            for (int n = 0; n < objects.length; n++) {
                if (i == n) continue; //object not interacting with self
                Vector2D radiusVector = new Vector2D(objects[n].x - objects[i].x, objects[n].y - objects[i].y); // radius vector
                double r3 = Math.pow(Vector2D.value(radiusVector), 3);
                double calculation = objects[i].mass * objects[n].mass / r3;
                radiusVector.mul(calculation);
                forceVectors[i].add(radiusVector);
            }
            forceVectors[i].mul(GN);
        }
        return forceVectors[0];
    }

    public void mul(double c) {
        this.x *= c;
        this.y *= c;
    }
}
