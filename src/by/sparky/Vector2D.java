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

    public void mul(double c) {
        this.x *= c;
        this.y *= c;
    }
}
