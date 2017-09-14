import java.util.concurrent.*;

public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        new IncrementThread(semaphore, "First");
        new DecrementThread(semaphore, "Second");
    }
}

class Shared {
    static int count = 0;
}

class IncrementThread implements Runnable {
    private String name;
    private Semaphore semaphore;

    IncrementThread(Semaphore semaphore, String name) {
        this.semaphore = semaphore;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println(name + " is starting");
        try {
            //get a permission
            System.out.println(name + " thread is waiting a permission");
            semaphore.acquire();
            System.out.println(name + " have got a permission");

            //get common access to share
            for (int i = 0; i < 5; i++) {
                Shared.count++;
                System.out.println(name + ": " + Shared.count);

                //allow if possible
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " is realises a permission");
        semaphore.release();
    }
}

class DecrementThread implements Runnable {
    private String name;
    private Semaphore semaphore;

    DecrementThread(Semaphore semaphore, String name) {
        this.semaphore = semaphore;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println(name + " is started");

        try {
            System.out.println(name + " thread is waiting a permission");
            semaphore.acquire();
            System.out.println(name + " have got a permission");
            for (int i = 0; i < 5; i++) {
                Shared.count--;
                System.out.println(name + ": " + Shared.count);

                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " is realises a permission");
        semaphore.release();
    }
}
