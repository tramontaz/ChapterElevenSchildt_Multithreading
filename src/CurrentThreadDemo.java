public class CurrentThreadDemo {
    public static void main(String[] args) {
        Thread curentThread = Thread.currentThread();

        System.out.println("Name of current thread: " + curentThread);

        //rename thread
        curentThread.setName("My thread");
        System.out.println("Current thread has renamed: " + curentThread);

        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(i);
                Thread.sleep(1000);
            }
        } catch(InterruptedException e){
            e.printStackTrace();
            System.out.println("Thread: " + Thread.currentThread() + " has interrupted.");
            }
        }
    }