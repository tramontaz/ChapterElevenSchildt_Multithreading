public class Deadlock implements Runnable {
    private First first = new First();
    private Second second = new Second();

    private Deadlock() {
        Thread.currentThread().setName("Main thread");
        Thread thread = new Thread(this, "Thread which try to rival with main thread");
        thread.start();


        first.foo(second); //have got a block for 'First object' in this thread
        System.out.println("Back to the 'Main thread'");
    }

    @Override
    public void run() {
        second.bar(first); //have got a block for 'Second object' in other thread
        System.out.println("Back to the 'Main thread'");
    }

    public static void main(String[] args) {
        new Deadlock();
    }

    static class First {
        synchronized void foo(Second second) {
            String name = Thread.currentThread().getName();

            System.out.println(name + " has interred to 'First.foo()'");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("First class is interrupted.");
            }
            System.out.println(name + " is trying to call the 'Second.last()'");
            second.last();
        }

        synchronized void last() {
            System.out.println("We are in 'First.last()'");
        }
    }

    class Second {
        synchronized void bar (First first) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " has interred to 'Second.bar()'");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("Second class is interrupted.");
            }

            System.out.println(name + " is trying to call the 'First.last()'");
            first.last();
        }

        synchronized void last() {
            System.out.println("We are in 'First.last()'");
        }
    }
}