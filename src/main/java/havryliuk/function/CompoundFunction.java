package havryliuk.function;

public class CompoundFunction implements Function {
    private static final double COMPOUND_CONDITION = 1.4;
    private final double eps;

    private final Function firstFunction;
    private final Function secondFunction;
    private final Function thirdFunction;


    public CompoundFunction(double a, double b, double c, double eps) {
        this.eps = eps;
        firstFunction = new FirstFunction(a, b, c);
        secondFunction = new SecondFunction(a);
        thirdFunction = new ThirdFunction(a, b);
    }


    @Override
    public double solve(double x) {
        return (x < COMPOUND_CONDITION - eps) ? firstFunction.solve(x)
                : (x > COMPOUND_CONDITION + eps)
                ? secondFunction.solve(x) : thirdFunction.solve(x);
    }
}
