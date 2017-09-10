public class ThreadDemo {
    //create the second Thread
    static class NewThread implements Runnable{
        Thread t;

        NewThread() {
            //create new second thread
            t = new Thread(this, "Demo thread");
            System.out.println("Thread has created: " + t);
            t.start();
        }
        //point of entry of second thread
        @Override
        public void run() {
            try {
                for (int i = 5; i > 0 ; i--) {
                    System.out.println("Main thread: " + i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Second thread has interrupted!");
            }
            System.out.println("Second thread has ended.");
        }

        public static void main(String[] args) {
            new NewThread();

            try {
                for (int i = 5; i > 0 ; i--) {
                    System.out.println("Main thread: " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Main thread has interrupted!");
            }
            System.out.println("Main thread has ended.");
        }
    }
}