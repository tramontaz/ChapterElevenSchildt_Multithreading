import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        System.out.println("Have started thread");

        new MyThread(countDownLatch);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Have ended thread.");
    }
}

class MyThread implements Runnable {
     private CountDownLatch countDownLatch;

    MyThread(CountDownLatch c){
        this.countDownLatch = c;
        new Thread(this).start();
    }
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            countDownLatch.countDown(); // countdown
        }
    }
}