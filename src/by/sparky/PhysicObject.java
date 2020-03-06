package by.sparky;

public class PhysicObject implements Point {
    protected double mass;
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;

    PhysicObject(double mass, double x, double y) {
        this.mass = mass;
        this.x = x;
        this.y = y;
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
