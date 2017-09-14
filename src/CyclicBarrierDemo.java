import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new CyclicBarrierAction());

        System.out.println("Running threads...");

        new MyThready(cyclicBarrier, "First");
        new MyThready(cyclicBarrier, "Second");
        new MyThready(cyclicBarrier, "Third");
    }

}

class MyThready implements Runnable {
    private CyclicBarrier cyclicBarrier;
    private String name;

    MyThready(CyclicBarrier cyclicBarrier, String name) {
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println(name);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class CyclicBarrierAction implements Runnable {

    @Override
    public void run() {
        System.out.println("The barrier has reached");
    }
}