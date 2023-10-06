package havryliuk;


public class Main {
    private static final double A = 2.7;
    private static final double B = -0.3;
    private static final double C = 4.0;

    public static void main(String[] args) {


    }

    public double solveFunction(double x) {
        return x < 1.4 ? solveFirst(x) : x == 1.4 ? solveSecond(x) : solveThird(x);
    }

    public int getArraySize(double start, double end, double interval) {
        if (start > end) throw new IllegalArgumentException("The start of array can't be larger than the end of it.");
        if (interval <= 0) throw new IllegalArgumentException("Interval should be greater than 0.");
        return (int) ((end - start)/interval + 1);
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
