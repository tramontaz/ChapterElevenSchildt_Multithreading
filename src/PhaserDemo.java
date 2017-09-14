import java.util.concurrent.Phaser;

public class PhaserDemo {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);
        int currentPhase;

        System.out.println("Launching threads");

        new MyPhaserThread(phaser, "First");
        new MyPhaserThread(phaser, "Second");
        new MyPhaserThread(phaser, "Third");

        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase: " + currentPhase + " have finished.");

        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase: " + currentPhase + " have finished.");

        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase: " + currentPhase + " have finished.");

        phaser.arriveAndDeregister();

        if (phaser.isTerminated()){
            System.out.println("Phaser have finished");
        }
    }
}

class MyPhaserThread implements Runnable {
    private Phaser phaser;
    private String name;

    MyPhaserThread(Phaser phaser, String name) {
        this.phaser = phaser;
        this.name = name;
        phaser.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println(name + " thread is starting first phase");
        phaser.arriveAndAwaitAdvance(); //tell about arriving phase

        //small pause for demonstration input.

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " thread is starting second phase");
        phaser.arriveAndAwaitAdvance(); //tell about arriving phase

        //small pause for demonstration input.

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " thread is starting third phase");
        phaser.arriveAndDeregister(); //tell about arriving phase

        //small pause for demonstration input.

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
