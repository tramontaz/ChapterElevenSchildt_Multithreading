public class ProducerAndCustomerBadRealisation {
    static class Query {
        int n;

        synchronized int get() {
            System.out.println("Have got: " + n);
            return n;
        }

        synchronized void put(int n) {
            this.n = n;
            System.out.println("Send: " + n);
        }
    }

    static class Producer implements Runnable {
        Query query;

        Producer(Query query) {
            this.query = query;
            new Thread(this, "Supplier").start();
        }

        @Override
        public void run() {
            int i = 0;

            while (true) {
                query.put(i++);
            }
        }
    }

    static class Customer implements Runnable {
        Query query;

        Customer(Query query) {
            this.query = query;
            new Thread(this, "Consumer").start();
        }

        @Override
        public void run() {
            while (true) {
                query.get();
            }
        }
    }

    public static void main(String[] args) {
        Query query = new Query();
        new Producer(query);
        new Customer(query);

        System.out.println("Press \"ctrl + c\" for stop.");

    }
}
