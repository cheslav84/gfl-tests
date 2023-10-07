package havryliuk;

import org.assertj.core.data.Offset;
import org.assertj.core.data.Percentage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import static org.assertj.core.api.Assertions.*;


public class MainTest {
    private static final double EPS = 10e-6;
    private Main solver;

    @BeforeMethod
    public void setUp() {
        solver = new Main();
    }


    @Test(dataProvider = "testDataSolveFunction")
    public void solveFunctionPositive(double x, double y) {
        assertThat(solver.solveFunction(x)).isCloseTo(y, Percentage.withPercentage(0.3));
    }

    @Test
    public void testSolveFunctionTooSmall() {
        double minPossibleValue = -8.1e150;
        assertThatThrownBy(() -> solver.solveFunction(minPossibleValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The argument is too small to be evaluated.");
    }

    @Test
    public void testSolveFunctionTooLarge() {
        double maxPossibleValue = 8.1e150;
        assertThatThrownBy(() -> solver.solveFunction(maxPossibleValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The argument is too large to be evaluated.");
    }





    @Test(dataProvider = "testDataArraySize")
    public void testArraySize(double start, double end, double interval, int steps) {
        assertThat(solver.getArraySize(start, end, interval)).isEqualTo(steps);
    }

    @Test(dataProvider = "testDataArraySizeNegative")
    public void testArraySizeNegative(double start, double end, double interval, int steps) {
        assertThat(solver.getArraySize(start, end, interval)).isNotEqualTo(steps);
    }

    @Test(dataProvider = "testDataArraySizeStartLargerEndException")
    public void testArraySizeStartLargerEndException(double start, double end, double interval) {
        assertThatThrownBy(() -> solver.getArraySize(start, end, interval))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The start of array can't be larger than the end of it.");
    }

    @Test(dataProvider = "testDataArraySizeIntervalNegativeOrZeroException")
    public void testArraySizeIntervalNegativeOrZeroException(double start, double end, double interval) {
        assertThatThrownBy(() -> solver.getArraySize(start, end, interval))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Interval should be greater than 0.");
    }




    @Test(dataProvider = "testDataGetArgumentsArray")
    public void testGetArgumentsArray(double start, double end, double interval, double[] arr) {
        assertThat(solver.getArgumentsArray(start, end, interval))
                .containsExactly(arr, Offset.offset(EPS))
                .startsWith(start)
                .isSorted()
                .doesNotHaveDuplicates()
                .doesNotContain(start - interval, end + interval)
        ;
    }


    @Test(dataProvider = "testDataGetFunctionValuesArray")
    public void testGetFunctionValuesArray(double[] arguments, double[] functionValues) {
        assertThat(solver.getFunctionValuesArray(arguments))
                .containsExactly(functionValues, Offset.offset(EPS));
    }


    @Test
    public void testGetFunctionValuesArrayException() {
        assertThatThrownBy(() -> solver.getFunctionValuesArray(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Array of arguments is null.");
    }




    @DataProvider(name = "testDataSolveFunction")
    private Object[][] dataForSolveFunction() {
        return new Object[][] {
                { -1000000, 2700000300004.0 },
                { -100, 27034 },
                { -5, 73 },
                { 0, 4 },
                { 5, 0.235339362 },
                { 100, -0.272986351 },
                { 1000000, -0.2999973 },

                { -8.0e150, 1.728e+302 },
                { -1000000.5555559, 2700003300006.8600011 },
                { 0.01, 3.99727 },
                { 1.4, 3.649036482 },
                { 1000000.5555559, -0.2999973 },
                { 8.0e150, -0.299172257 },
        };
    }


    @DataProvider(name = "testDataArraySize")
    private Object[][] dataArraySize() {
        return new Object[][] {
//          start, end, interval, steps
                { 0, 1, 1, 2 },
                { 0, 2, 1, 3 },
                { 1, 10, 1, 10 },
                { 0, 1000, 1, 1001 },

                { 0, 10, 2, 6 },
                { 0, 11, 2, 6 },

                { -10, 0, 2, 6 },
                { -11, 0, 2, 6 },

                { -10, 10, 2, 11 },
                { -10, 11, 2, 11 },
                { -11, 11, 2, 12 },

                { 0, 0, 0.1, 1 },
                { 0, 5, 10, 1 },

                { 0, 1.1, 0.1, 12 },
                { 0, 1.1, 0.2, 6 },
                { 0, 1.1, 0.3, 4 },
                { 0, 1.1, 0.4, 3 },
                { 0, 1.1, 0.5, 3 },
                { 0, 1.1, 0.6, 2 },

                { -1, 0, 0.2, 6 },
                { -1, 1, 0.2, 11 },

                { 0, 2, 0.002, 1001 },

        };
    }



    @DataProvider(name = "testDataArraySizeNegative")
    private Object[][] dataArraySizeNegative() {
        return new Object[][] {
//          start, end, interval, steps
                { 0, 1, 1, 1 },
                { 0, 2, 1, 2 },
                { 1, 10, 1, 11 },
                { 0, 1000, 1, 1000 },

                { 0, 10, 2, 5 },
                { 0, 10, 2, 7 },
                { 0, 11, 2, 5 },
                { 0, 11, 2, 7 },

                { 0, 0, 0.1, 0 },
                { 0, 0, 0.1, 2 },

                { -1, 0, 0.2, 7 },
                { -1, 0, 0.2, 8 },

                { 0, 2, 0.002, 1000 },

        };
    }

    @DataProvider(name = "testDataArraySizeStartLargerEndException")
    private Object[][] dataArraySizeStartLargerEndException() {
        return new Object[][] {
//          start, end, interval,
                { 1, 0, 1},
                { 1, -1, 1},
                { 0, -1, 1},
                { 1, -1, 1},
        };
    }

    @DataProvider(name = "testDataArraySizeIntervalNegativeOrZeroException")
    private Object[][] dataArraySizeIntervalNegativeOrZeroException() {
        return new Object[][] {
//          start, end, interval,
                { 0, 1, 0},
                { 1, 2, -1},
                { 1, 5, -10},

        };
    }



    @DataProvider(name = "testDataGetArgumentsArray")
    private Object[][] dataGetArgumentsArray() {
        return new Object[][] {
//               start, end, interval, array
                { 0, 1, 1, new double[]{0, 1} },
                { 0, 2, 1, new double[]{0, 1, 2} },
                { 1, 10, 1, new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}  },

                { 0, 10, 2, new double[]{0, 2, 4, 6, 8, 10} },
                { 0, 11, 2, new double[]{0, 2, 4, 6, 8, 10} },

                { 0, 0, 0.1, new double[]{0} },
                { 0, 5, 10, new double[]{0} },

                { 0, 1.1, 0.1, new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1} },
                { 0, 1.1, 0.2, new double[]{0, 0.2, 0.4, 0.6, 0.8, 1.0} },
                { 0, 1.1, 0.3, new double[]{0, 0.3, 0.6, 0.9} },
                { 0, 1.1, 0.4, new double[]{0, 0.4, 0.8} },
                { 0, 1.1, 0.5, new double[]{0, 0.5, 1.0} },
                { 0, 1.1, 0.6, new double[]{0, 0.6} },

                { -1.1, 0, 0.1, new double[]{-1.1, -1.0, -0.9, -0.8, -0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1, 0} },
                { -1.1, 0, 0.2, new double[]{-1.1, -0.9, -0.7, -0.5, -0.3, -0.1} },
                { -1.1, 0, 0.3, new double[]{-1.1, -0.8, -0.5, -0.2} },
                { -1.1, 0, 0.4, new double[]{-1.1, -0.7, -0.3} },
                { -1.1, 0, 0.5, new double[]{-1.1, -0.6, -0.1} },
                { -1.1, 0, 0.6, new double[]{-1.1, -0.5} },
        };
    }


    @DataProvider(name = "testDataGetFunctionValuesArray")
    private Object[][] dataGetFunctionValuesArray() {
        return new Object[][] {
//               start, end, interval, array
                { new double[]{0, 1.4, 1.998}, new double[]{4, 3.649037, 0.940169} },
                { new double[]{0}, new double[]{4} },
                { new double[]{0, 1}, new double[]{4, 6.4} },
                { new double[]{0, 1, 1.4, 2}, new double[]{4, 6.4, 3.649037, 0.939148} },

                { new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                        new double[]{ 6.4, 0.939148, 0.569210, 0.363803, 0.235339, 0.147959, 0.084853, 0.037210, 0, -0.029851} },

                { new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1},
                        new double[]{4, 3.997, 4.048, 4.153, 4.312, 4.525, 4.792, 5.113, 5.488, 5.917, 6.4, 6.937} },

                { new double[]{-1.1, -1.0, -0.9, -0.8, -0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1, 0},
                        new double[]{7.597, 7, 6.457, 5.968, 5.533, 5.152, 4.825, 4.552, 4.333, 4.168, 4.057, 4} },
        };
    }



}