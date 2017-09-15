import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleExecutor {
    public static void main(String[] args) {
        CountDownLatch countDownLatch1 = new CountDownLatch(5);
        CountDownLatch countDownLatch2 = new CountDownLatch(5);
        CountDownLatch countDownLatch3 = new CountDownLatch(5);
        CountDownLatch countDownLatch4 = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        System.out.println("Launching threads\n");

        executorService.execute(new MyExecutorThread(countDownLatch1, "First"));
        executorService.execute(new MyExecutorThread(countDownLatch2, "Second"));
        executorService.execute(new MyExecutorThread(countDownLatch3, "Third"));
        executorService.execute(new MyExecutorThread(countDownLatch4, "Fifth"));

        try {
            countDownLatch1.await();
            countDownLatch2.await();
            countDownLatch3.await();
            countDownLatch4.await();

        } catch (InterruptedException e){
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("Completion of threads");
    }
}

class MyExecutorThread implements Runnable {
    private String name;
    private CountDownLatch countDownLatch;

    MyExecutorThread(CountDownLatch countDownLatch, String name) {
        this.countDownLatch = countDownLatch;
        this.name = name;
        new Thread(this);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + ": " + i);
            countDownLatch.countDown();
        }
    }
}
