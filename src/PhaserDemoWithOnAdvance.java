import java.util.concurrent.Phaser;

public class PhaserDemoWithOnAdvance {
    public static void main(String[] args) {
        MyPhaser myPhaser = new MyPhaser(1, 4);
        System.out.println("Launching threads\n");

        new MyPhaserThreadOnAdvance(myPhaser, "First");
        new MyPhaserThreadOnAdvance(myPhaser, "Second");
        new MyPhaserThreadOnAdvance(myPhaser, "Third");

        while (!myPhaser.isTerminated()) {
            myPhaser.arriveAndAwaitAdvance();
        }

        System.out.println("Phaser have finished");
    }
}

class MyPhaser extends Phaser {
    private int numberOfPhases;

    MyPhaser(int parties, int phaseCount) {
        super(parties);
        numberOfPhases = phaseCount - 1;
    }

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        System.out.println(phase + " phase have finished");
        return phase == numberOfPhases || registeredParties == 0;
    }
}

class MyPhaserThreadOnAdvance implements Runnable {
    private Phaser phaser;
    private String name;

    MyPhaserThreadOnAdvance(Phaser phaser, String name) {
        this.phaser = phaser;
        this.name = name;
        phaser.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!phaser.isTerminated()) {
            System.out.println(name + " thread is starting " + phaser.getPhase() + " phase");
            phaser.arriveAndAwaitAdvance();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}