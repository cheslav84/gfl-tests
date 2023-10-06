package havryliuk;

import org.assertj.core.data.Percentage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import static org.testng.Assert.*;
import static org.assertj.core.api.Assertions.*;


public class MainTest {
    private static final double EPS = 10e-6;
    private Main solver;

    @BeforeMethod
    public void setUp() {
        solver = new Main();
    }


    @Test(dataProvider = "solveFunction")
    public void testSolveFunction(double x, double y) {
        assertThat(solver.solveFunction(x)).isCloseTo(y, Percentage.withPercentage(1));
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

    @DataProvider(name = "solveFunction")
    public Object[][] dataForRandomArguments() {
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




}