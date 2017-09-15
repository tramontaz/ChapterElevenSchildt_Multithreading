import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<Integer> futureSum;
        Future<Double> futureLengthOfTheHypotenuse;
        Future<Integer> futureFactorial;

        System.out.println("Launching");

        futureSum = executorService.submit(new Sum(10));
        futureLengthOfTheHypotenuse = executorService.submit(new Hypotenuse(3, 4));
        futureFactorial = executorService.submit(new Factorial(5));

        try {
            System.out.println(futureSum.get(10, TimeUnit.MILLISECONDS));
            System.out.println(futureLengthOfTheHypotenuse.get(10, TimeUnit.MILLISECONDS));
            System.out.println(futureFactorial.get(10, TimeUnit.MILLISECONDS));
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("Completion.");
    }
}

class Sum implements Callable<Integer> {
    private int stop;

    Sum(int stop) {
        this.stop = stop;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < stop; i++) {
            sum += i;
        }
        return sum;
    }
}

class Hypotenuse implements Callable<Double> {
    private double theFirstSide, theSecondSide;

    Hypotenuse(double theFirstSide, double theSecondSide) {
        this.theFirstSide = theFirstSide;
        this.theSecondSide = theSecondSide;
    }

    @Override
    public Double call() throws Exception {
        return Math.sqrt((theFirstSide * theFirstSide) + (theSecondSide*theSecondSide));
    }
}

class Factorial implements Callable<Integer> {
    private int stop;

    Factorial(int stop) {this.stop = stop;}

    @Override
    public Integer call() throws Exception {
        int factorial = 1;
        for (int i = 2; i <= stop; i++) {
            factorial *= i;
        }
        return factorial;
    }
}