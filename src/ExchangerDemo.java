import java.util.concurrent.Exchanger;

public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new UseString(exchanger);
        new MakeString(exchanger);
    }
}

class MakeString implements Runnable {
    private Exchanger<String> exchanger;
    private String string;

    MakeString(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        string = "";
        new Thread(this).start();
    }

    @Override
    public void run() {
        char ch = 'A';
        for (int i = 0; i < 3; i++) {
            //fill buffer
            for (int j = 0; j < 5; j++)
                string += (char) ch++;
            try {
                //replace full buffer by empty
                string = exchanger.exchange(string);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class UseString implements Runnable {
    private Exchanger<String> exchanger;

    UseString(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                String line = exchanger.exchange("");
                System.out.println("Have got: " + line);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}