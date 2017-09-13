public class SynchBlocks {
    private static class CallMe {
        void call(String msg) {
            System.out.print("{" + msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Interrupted!!!");
            }
            System.out.println("}");
        }
    }

    private static class Caller implements Runnable {
        String msg;
        CallMe target;
        Thread thread;

        Caller(CallMe target, String msg) {
            this.target = target;
            this.msg = msg;
            thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            synchronized (target) {
                target.call(msg);
            }
        }
    }

    public static void main(String[] args) {
        CallMe target = new CallMe();
        Caller ob1 = new Caller(target, "Welcome");
        Caller ob2 = new Caller(target, "to synchronized");
        Caller ob3 = new Caller(target, "World!");

        try {
            ob1.thread.join();
            ob2.thread.join();
            ob3.thread.join();

        } catch (InterruptedException e) {
            System.err.println("Interrupted!");
        }


    }
}
