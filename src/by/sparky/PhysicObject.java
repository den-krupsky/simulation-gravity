package by.sparky;

public class PhysicObject implements Point {
    protected double mass;
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;
    protected Vector2D point;
    protected Vector2D velocity;

    PhysicObject(double mass, double x, double y) {
        this.mass = mass;
        this.x = x;
        this.y = y;
        point = new Vector2D(x, y);
        velocity = new Vector2D(0, 0);
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getSize() {
        return mass / 2000.0d;
    }
}
