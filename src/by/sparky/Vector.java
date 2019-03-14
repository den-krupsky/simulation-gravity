package by.sparky;

public class Vector {
    private PointXY source;
    private PointXY destin;

    public Vector(PointXY source, PointXY destin) {
        this.source = source;
        this.destin = destin;
    }

    public static Vector sum(Vector u, Vector v) {
        Vector w = new Vector(new PointXY(u.source.x + v.source.x, u.source.y + v.source.y),
                new PointXY(u.destin.x + v.destin.x, u.destin.y + v.destin.y));
        return w;
    }

    public static Vector sub(Vector u, Vector v) {
        Vector w = new Vector(new PointXY(u.source.x - v.source.x, u.source.y - v.source.y),
                new PointXY(u.destin.x - v.destin.x, u.destin.y - v.destin.y));
        return w;
    }



}
