package havryliuk.function;

import havryliuk.TestFunctionDataProvider;
import org.assertj.core.data.Percentage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CompoundFunctionTest {
    private static final double EPS = 1e-6;
    private static final double A = 2.7;
    private static final double B = -0.3;
    private static final double C = 4.0;

    private Function function;

    @BeforeMethod
    public void setUp() {
        function = new CompoundFunction(A, B, C, EPS);
    }


    @Test(dataProvider = "testDataSolveFunction", dataProviderClass = TestFunctionDataProvider.class)
    public void solveFunctionPositive(double x, double y) {
        assertThat(function.solve(x)).isCloseTo(y, Percentage.withPercentage(0.3));
    }



    @Test
    public void testSolveFunctionTooSmall() {
        double minPossibleValue = -8.1e150;
        assertThatThrownBy(() -> function.solve(minPossibleValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The argument is too small to be evaluated.");
    }

    @Test
    public void testSolveFunctionTooLarge() {
        double maxPossibleValue = 8.1e150;
        assertThatThrownBy(() -> function.solve(maxPossibleValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The argument is too large to be evaluated.");
    }





}