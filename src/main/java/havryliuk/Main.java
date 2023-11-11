package havryliuk;


import havryliuk.function.CompoundFunction;
import havryliuk.function.Function;

public class Main {
    private static final double EPS = 1e-6;
    private static final double A = 2.7;
    private static final double B = -0.3;
    private static final double C = 4.0;

    public static void main(String[] args) {

        Main solver = new Main();
        solver.run();

    }

    private void run() {
        Function function = new CompoundFunction(A, B, C, EPS);
        FunctionSolver solver = new FunctionSolver(function);

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


}
