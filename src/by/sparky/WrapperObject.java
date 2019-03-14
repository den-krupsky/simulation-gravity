package by.sparky;

import java.awt.*;

public class WrapperObject {
    protected PhysicObject physicObject;
    protected Color color;
    protected int size;

    WrapperObject(PhysicObject physicObject) {
        this.physicObject = physicObject;
        color = Color.red;
        size = 10;
    }

}
