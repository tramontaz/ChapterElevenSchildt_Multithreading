public class DemoJoin {
    private static class NewThread implements Runnable{
        String name; //name of thread
        Thread thread;

        NewThread(String threadName){
            name = threadName;
            thread = new Thread(this, name);
            System.out.println("New thread: " + thread);
            thread.start();
        }
        @Override
        public void run() {
            try {
                for (int i = 5; i > 0 ; i--) {
                    System.out.println("Main thread: " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(name + " thread has interrupted!");
            }
            System.out.println(name + " thread has ended.");
        }
    }

    public static void main(String[] args) {
        NewThread ob1 = new NewThread("One");
        NewThread ob2 = new NewThread("Two");
        NewThread ob3 = new NewThread("Three");

        System.out.println("Thread 'one' is started: " + ob1.thread.isAlive());
        System.out.println("Thread 'two' is started: " + ob2.thread.isAlive());
        System.out.println("Thread 'three' is started: " + ob3.thread.isAlive());

        try {
            System.out.println("Wait when all threads will ended.");
            ob1.thread.join();
            ob2.thread.join();
            ob3.thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
            System.out.println("Main thread was interrupted");
        }

        System.out.println("Thread 'one' is started: " + ob1.thread.isAlive());
        System.out.println("Thread 'two' is started: " + ob2.thread.isAlive());
        System.out.println("Thread 'three' is started: " + ob3.thread.isAlive());

        System.out.println("Main thread has ended.");
    }
}
