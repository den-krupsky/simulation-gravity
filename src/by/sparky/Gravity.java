package by.sparky;

public class Gravity implements Interaction {

    private static final double gravityConst = 6.674d * Math.pow(10,-11);

    @Override
    public void calculate(PhysicObject[] physicObjects) {
        for (int i = 0; i < physicObjects.length; i++) {
            for (int j = i; j < physicObjects.length; i++) {

            }
        }
    }

}
