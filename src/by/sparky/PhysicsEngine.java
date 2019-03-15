package by.sparky;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PhysicsEngine {

    private PhysicObject[] physicObjects = new PhysicObject[0];
    private Interaction[] interactions = new Interaction[0];
    private SimulationTask simulationTask;

    PhysicsEngine() {
    }

    private void initTimer() {
        this.simulationTask = new SimulationTask(1 / 60d);
    }

    public void simulate() {
        initTimer();
        Timer timer = new Timer(false);
        timer.schedule(simulationTask, 0, 1000 / 60);
    }

    public void setPhysicObject(PhysicObject... physicObjects){
        this.physicObjects = physicObjects;
    }

    public void setInteractions(Interaction... interactions) {
       this.interactions = interactions;
    }

    private class SimulationTask extends TimerTask {
        private double frameRate; // 1 is equal
        private long counter = 0;

        SimulationTask(double frameRate) {
            this.frameRate = frameRate;
        }

        @Override
        public void run() {
            this.counter++;
            long start = System.currentTimeMillis();
            System.out.print("Start calculate interactions at " + new Date());
            for (Interaction interaction : interactions) {
                interaction.calculate(physicObjects, frameRate * counter);
            }
            System.out.println(" and end for " + (System.currentTimeMillis() - start) + " ms");
        }
    }
}
