import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class Transform extends RecursiveAction {
    private int seqThreshold;
    private double[] data;
    private int start, end;

    Transform(double[] data, int start, int end, int seqThreshold) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.seqThreshold = seqThreshold;
    }

    @Override
    protected void compute() {
        if ((end - start) < seqThreshold) {
            for (int i = start; i < end; i++) {
                if ((data[i] % 2) == 0) data[i] = Math.sqrt(data[i]);
                else data[i] = Math.cbrt(data[i]);
            }
        }
        else {
            int middle = (start + end) / 2;
            invokeAll(new Transform(data, start, middle, seqThreshold) ,
                    new Transform(data, middle, end, seqThreshold));
        }
    }
}

public class FJExperiment {
    public static void main(String[] args) {
        int pLevel;
        int threshold;

        if (args.length != 2) {
            System.out.println("Usage: FJExperiment parallelism threshold ");
        }

        pLevel = 1;
        threshold = 1000;

        long beginT, endT;

        ForkJoinPool forkJoinPool = new ForkJoinPool(pLevel);

        double[] nums = new double[1000_000];

        for (int i = 0; i < nums.length; i++) {
            nums[i] = (double) i;
        }
        Transform task = new Transform(nums, 0, nums.length, threshold);

        beginT = System.nanoTime();

        forkJoinPool.invoke(task);

        endT = System.nanoTime();

        System.out.println("Parallelism level: " + pLevel);

        System.out.println("Threshold of sequential processing: " + threshold);

        System.out.println("Time: " + (endT - beginT) + " nanoseconds");
    }
}