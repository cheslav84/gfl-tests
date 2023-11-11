package havryliuk;


import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
    private static final double EPS = 1e-6;
    private static final double A = 2.7;
    private static final double B = -0.3;
    private static final double C = 4.0;

    public static void main(String[] args) {

        Main solver = new Main();

        double start = 0;
        double end = 2;
        double interval = 0.005;

        double[] argumentsArray = solver.getArgumentsArray(start, end, interval);
        double[] functionValuesArray = solver.getFunctionValuesArray(argumentsArray);

        int maxValueIndex = solver.getMaxValueIndex(functionValuesArray);
        int minValueIndex = solver.getMinValueIndex(functionValuesArray);

        solver.printMaxValue(functionValuesArray, maxValueIndex);
        solver.printMinValue(functionValuesArray, minValueIndex);
        System.out.println("The sum of function values is " + solver.getSumOfValues(functionValuesArray));
        System.out.println("Average function value is " + solver.getAverage(functionValuesArray));


    }

    public double solveFunction(double x) {
        return x < 1.4 - EPS ? solveFirst(x) : x > 1.4 + EPS ? solveThird(x) : solveSecond(x);
    }



    public int getArraySize(double start, double end, double interval) {
        if (start > end) throw new IllegalArgumentException("The start of array can't be larger than the end of it.");
        if (interval <= 0) throw new IllegalArgumentException("Interval should be greater than 0.");
//        return (int) (Math.round(end - start)/interval) + 1;
        return (int) ((end - start)/interval + 1);
   }


    public double[] getArgumentsArray(double start, double end, double interval) {
        int size = getArraySize(start, end, interval);
        return IntStream.range(0, size)
                .mapToDouble(i -> start + i * interval)
                .toArray();
    }

    public double[] getFunctionValuesArray (double[] arguments) {
        if(arguments == null) throw new IllegalArgumentException("Array of arguments is null.");
        return Arrays.stream(arguments)
                .map(this::solveFunction)
                .toArray();
    }

    public int getMaxValueIndex(double[] functionValues) {
        if(functionValues == null) throw new IllegalArgumentException("Array of functionValues is null.");
        return IntStream.range(0, functionValues.length)
                .reduce((a,b) -> functionValues[a] < functionValues[b] ? b : a)
                .orElseThrow(() -> new IllegalArgumentException("Array is empty."));
    }


    public int getMinValueIndex(double[] functionValues) {
        if(functionValues == null) throw new IllegalArgumentException("Array of functionValues is null.");
        return IntStream.range(0, functionValues.length)
                .reduce((a,b) -> functionValues[a] > functionValues[b] ? b : a)
                .orElseThrow(() -> new IllegalArgumentException("Array is empty."));
    }

    public double getSumOfValues (double[] functionValues) {
        if(functionValues == null) throw new IllegalArgumentException("Array of functionValues is null.");
        return Arrays.stream(functionValues).sum();
    }

    public double getAverage (double[] functionValues) {
        if(functionValues == null) throw new IllegalArgumentException("Array of functionValues is null.");
        return Arrays.stream(functionValues).average().orElseThrow(() -> new IllegalArgumentException("Array is empty."));
    }


    public void printMaxValue(double[] functionValues, int index) {
        System.out.println("Maximum of function values is " + functionValues[index]);
    }


    public void printMinValue(double[] functionValues, int index) {
        System.out.println("Minimum of function values is " + functionValues[index]);
    }



    private double solveFirst(double x) {
        double minPossibleValue = -8.0e150;
        if (x < minPossibleValue) throw new IllegalArgumentException("The argument is too small to be evaluated.");
        return A * Math.pow(x, 2) + B * x + C;
    }


    private double solveSecond(double x) {
        return (A / x) + Math.sqrt(Math.pow(x, 2) + 1);
    }

    private double solveThird(double x) {
        double maxPossibleValue = 8.0e150;
        if (x > maxPossibleValue) throw new IllegalArgumentException("The argument is too large to be evaluated.");
        return (A + B * x) / Math.sqrt(Math.pow(x, 2) + 1);
    }




}
