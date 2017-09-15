import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();

        new LockThread(reentrantLock, "first");
        new LockThread(reentrantLock, "second");

    }
}

class LockThread implements Runnable {
    private String name;
    private ReentrantLock reentrantLock;

    LockThread(ReentrantLock reentrantLock, String name) {
        this.name = name;
        this.reentrantLock = reentrantLock;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Start the thread: " + name);

        try {
            System.out.println(name + " is waiting for the lock");
            reentrantLock.lock();
            System.out.println(name + " is blocking the counter");
            Shared.count++;
            System.out.println(name + ": " + Shared.count);
            System.out.println(name + " is waiting...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(name + " unblocks the counter.");
            reentrantLock.unlock();
        }
    }
}
