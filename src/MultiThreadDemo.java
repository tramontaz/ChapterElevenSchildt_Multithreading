public class MultiThreadDemo {
    //create a few threads
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
        new NewThread("One");
        new NewThread("Two");
        new NewThread("Three");

        try {
            //wait for completion of thread
            Thread.sleep(10000);
        } catch (InterruptedException e){
            e.printStackTrace();
            System.out.println("Main thread has interrupted!");
        }
        System.out.println("Main thread has ended.");
    }
}