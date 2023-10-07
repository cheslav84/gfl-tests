package havryliuk;

import org.assertj.core.data.Offset;
import org.assertj.core.data.Percentage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.assertj.core.api.Assertions.*;


public class MainTest {
    private static final double EPS = 10e-6;
    private Main solver;

    @BeforeMethod
    public void setUp() {
        solver = new Main();
    }


    @Test(dataProvider = "testDataSolveFunction", dataProviderClass = MainTestDataProvider.class)
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





    @Test(dataProvider = "testDataArraySize", dataProviderClass = MainTestDataProvider.class)
    public void testArraySize(double start, double end, double interval, int steps) {
        assertThat(solver.getArraySize(start, end, interval)).isEqualTo(steps);
    }

    @Test(dataProvider = "testDataArraySizeNegative", dataProviderClass = MainTestDataProvider.class)
    public void testArraySizeNegative(double start, double end, double interval, int steps) {
        assertThat(solver.getArraySize(start, end, interval)).isNotEqualTo(steps);
    }

    @Test(dataProvider = "testDataArraySizeStartLargerEndException", dataProviderClass = MainTestDataProvider.class)
    public void testArraySizeStartLargerEndException(double start, double end, double interval) {
        assertThatThrownBy(() -> solver.getArraySize(start, end, interval))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The start of array can't be larger than the end of it.");
    }

    @Test(dataProvider = "testDataArraySizeIntervalNegativeOrZeroException", dataProviderClass = MainTestDataProvider.class)
    public void testArraySizeIntervalNegativeOrZeroException(double start, double end, double interval) {
        assertThatThrownBy(() -> solver.getArraySize(start, end, interval))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Interval should be greater than 0.");
    }




    @Test(dataProvider = "testDataGetArgumentsArray", dataProviderClass = MainTestDataProvider.class)
    public void testGetArgumentsArray(double start, double end, double interval, double[] arr) {
        assertThat(solver.getArgumentsArray(start, end, interval))
                .containsExactly(arr, Offset.offset(EPS))
                .startsWith(start)
                .isSorted()
                .doesNotHaveDuplicates()
                .doesNotContain(start - interval, end + interval)
        ;
    }


    @Test(dataProvider = "testDataGetFunctionValuesArray", dataProviderClass = MainTestDataProvider.class)
    public void testGetFunctionValuesArray(double[] arguments, double[] functionValues) {
        assertThat(solver.getFunctionValuesArray(arguments))
                .containsExactly(functionValues, Offset.offset(EPS));
    }


    @Test
    public void testGetFunctionValuesArrayExceptionNull() {
        assertThatThrownBy(() -> solver.getFunctionValuesArray(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Array of arguments is null.");
    }


    @Test(dataProvider = "testDataGetMaxValue", dataProviderClass = MainTestDataProvider.class)
    public void testGetMaxValue(double[] functionValues, double maxValue) {
        assertThat(solver.getMaxValue(functionValues)).isCloseTo(maxValue, Percentage.withPercentage(0.3));
    }

    @Test
    public void testGetMaxValueExceptionNull() {
        assertThatThrownBy(() -> solver.getMaxValue(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Array of functionValues is null.");
    }

    @Test
    public void testGetMaxValueExceptionEmpty() {
        assertThatThrownBy(() -> solver.getMaxValue(new double[]{}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Array is empty.");
    }

    @Test(dataProvider = "testDataGetMinValue", dataProviderClass = MainTestDataProvider.class)
    public void testGetMinValue(double[] functionValues, double maxValue) {
        assertThat(solver.getMinValue(functionValues)).isCloseTo(maxValue, Percentage.withPercentage(0.3));
    }

    @Test
    public void testGetMinValueExceptionNull() {
        assertThatThrownBy(() -> solver.getMinValue(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Array of functionValues is null.");
    }

    @Test
    public void testGetMinValueExceptionEmpty() {
        assertThatThrownBy(() -> solver.getMinValue(new double[]{}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Array is empty.");
    }



    @Test(dataProvider = "testDataGetSumOfValues", dataProviderClass = MainTestDataProvider.class)
    public void testGetSumOfValues(double[] functionValues, double sum) {
        assertThat(solver.getSumOfValues(functionValues)).isCloseTo(sum, Percentage.withPercentage(0.3));
    }

    @Test
    public void testGetSumOfValuesExceptionNull() {
        assertThatThrownBy(() -> solver.getSumOfValues(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Array of functionValues is null.");
    }



    @Test(dataProvider = "testDataGetAvgValue", dataProviderClass = MainTestDataProvider.class)
    public void testGetAvgValue(double[] functionValues, double maxValue) {
        assertThat(solver.getAverage(functionValues)).isCloseTo(maxValue, Percentage.withPercentage(0.3));
    }

    @Test
    public void testGetAvgValueExceptionNull() {
        assertThatThrownBy(() -> solver.getAverage(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Array of functionValues is null.");
    }

    @Test
    public void testGetAvgValueExceptionEmpty() {
        assertThatThrownBy(() -> solver.getAverage(new double[]{}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Array is empty.");
    }




}
