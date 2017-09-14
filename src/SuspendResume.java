public class SuspendResume {
    class NewThread implements Runnable {
        String threadName;
        Thread thread;
        boolean suspendFlag;

        NewThread(String tName) {
            threadName = tName;
            thread = new Thread(this, threadName);
            System.out.println("The name of New thread is " + threadName);
            suspendFlag = false;
            thread.start();
        }

        @Override
        public void run() {
            try {
                for (int i = 15; i > 0; i--) {
                    System.out.println(threadName + ": " + i);
                    Thread.sleep(200);
                    synchronized (this) {
                        while (suspendFlag) {
                            wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.err.println(threadName + " have interrupted.");
            }
            System.out.println(threadName + " have finished");
        }

        synchronized void mySuspend() {
            suspendFlag = true;
        }

        synchronized void myResume() {
            suspendFlag = false;
            notify();
        }
    }

    public static void main(String[] args) {
        NewThread ob1 = new NewThread("One");
        NewThread ob2 = new NewThread("Two");

        try {
            Thread.sleep(1000);
            ob1.mySuspend();
            System.out.println("Thread 'one' have paused");
            Thread.sleep(1000);
            ob1.myResume();
            System.out.println("Thread 'one' have resumed");
            ob2.mySuspend();
            System.out.println("Thread 'two' have paused");
            Thread.sleep(1000);
            ob2.myResume();
            System.out.println("Thread 'two' have resumed");
        } catch (InterruptedException e) {
            System.err.println("Main thread is interrupted");
        }

        System.out.println("Wait when threads will end");

        try {
            ob1.thread.join();
            ob2.thread.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread is interrupted");
        }

        System.out.println("The Main thread have ended.");
    }
}

