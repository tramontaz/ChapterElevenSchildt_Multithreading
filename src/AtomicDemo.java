import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    public static void main(String[] args) {
        new AtomicThread("First");
        new AtomicThread("Second");
        new AtomicThread("Third");
    }
}

class AtomicThread implements Runnable {
    String name;

    AtomicThread(String name) {
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Running " + name);
        for (int i = 0; i <= 3; i++) {
            System.out.println("In " + name + " thread is got: " + AtomicShared.atomicInteger.getAndSet(i));
        }
    }
}

class AtomicShared {
    static AtomicInteger atomicInteger = new AtomicInteger(0);
}