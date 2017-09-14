import java.util.concurrent.Semaphore;

public class ProducerAndCustomerSemaphoreRealisation {
    public static void main(String[] args) {
        Queue queue = new Queue();
        new Customer(queue);
        new Producer(queue);
    }
}

class Queue {
    private int n;

    //create two semaphores for customer and producer. For customer semaphore is not available now:
    private static Semaphore semaphoreForCustomer = new Semaphore(0);
    private static Semaphore semaphoreForProducer = new Semaphore(1);

    void get() {
        try {
            semaphoreForCustomer.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Have got: " + n);
        semaphoreForProducer.release();
    }

    void put(int n) {
        try {
            semaphoreForProducer.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.n = n;
        System.out.println("Have sent: " + n);
        semaphoreForCustomer.release();
    }
}

class Producer implements Runnable {
    private Queue queue;

    Producer(Queue queue) {
        this.queue = queue;
        new Thread(this, "Producer").start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            queue.put(i);
        }
    }
}

class Customer implements Runnable {
    private Queue queue;

    Customer(Queue queue) {
        this.queue = queue;
        new Thread(this, "Customer").start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 20 ; i++) {
            queue.get();
        }
    }
}