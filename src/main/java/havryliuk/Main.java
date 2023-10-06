package havryliuk;

public class Main {
    private static final double A = 2.7;
    private static final double B = -0.3;
    private static final double C = 4.0;

    public static void main(String[] args) {
        double max = (Math.sqrt(Double.MAX_VALUE)) ;
        System.out.println(Math.pow(8.0E150, 2));

    }

    public Double solveFunction(double x) {
        return x < 1.4 ? solveFirst(x) : x == 1.4 ? solveSecond(x) : solveThird(x);
    }

    private Double solveFirst(double x) {
        double minPossibleValue = -8.0e150;
        if (x < minPossibleValue) throw new IllegalArgumentException("The argument is too small to be evaluated.");
        return A * Math.pow(x, 2) + B * x + C;
    }

    private Double solveSecond(double x) {
        return (A / x) + Math.sqrt(Math.pow(x, 2) + 1);
    }

    private Double solveThird(double x) {
        double maxPossibleValue = 8.0e150;
        if (x > maxPossibleValue) throw new IllegalArgumentException("The argument is too large to be evaluated.");
        return (A + B * x) / Math.sqrt(Math.pow(x, 2) + 1);
    }

}
