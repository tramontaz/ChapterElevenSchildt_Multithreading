public class ExtendThread {
    //create second thread extending Thread class
    static class NewThread extends Thread {
        NewThread(){
            super("Demo thread");
            System.out.println("Second thread: " + this);
            start();
        }

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
