package by.sparky;

public class Vector {
    private Vector2D source;
    private Vector2D destin;

    public Vector(Vector2D source, Vector2D destin) {
        this.source = source;
        this.destin = destin;
    }

    public static Vector sum(Vector u, Vector v) {
        Vector w = new Vector(new Vector2D(u.source.x + v.source.x, u.source.y + v.source.y),
                new Vector2D(u.destin.x + v.destin.x, u.destin.y + v.destin.y));
        return w;
    }

    public static Vector sub(Vector u, Vector v) {
        Vector w = new Vector(new Vector2D(u.source.x - v.source.x, u.source.y - v.source.y),
                new Vector2D(u.destin.x - v.destin.x, u.destin.y - v.destin.y));
        return w;
    }

    @Override
    public String toString() {
        return "vector: {" + source.toString() + ", "+ destin.toString() + "}";
    }
}
