public class ProducerAndCustomerWellDoneRealisation {
    static class Query {
        int n;
        boolean valueSet = false;

        synchronized int get() {
            while (!valueSet)
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            System.out.println("Have got: " + n);
            valueSet = false;
            notify();
            return n;
        }

        synchronized void put(int n) {
            while (valueSet)
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            this.n = n;
            valueSet = true;
            System.out.println("Have sent: " + n);
            notify();
        }
    }

    static class Producer implements Runnable {
        Query query;

        Producer(Query query) {
            this.query = query;
            new Thread(this, "Producer").start();
        }

        @Override
        public void run() {
            int i = 0;
            while (true) query.put(i++);
        }
    }

    static class Customer implements Runnable {
        Query query;

        Customer(Query query) {
            this.query = query;
            new Thread(this, "Customer").start();
        }

        @Override
        public void run() {
            while (true) query.get();
        }
    }

    public static void main(String[] args) {
        Query query = new Query();
        new Producer(query);
        new Customer(query);
    }
}
