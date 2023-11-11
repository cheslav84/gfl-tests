package havryliuk.function;

public class SecondFunction implements Function {

    private final double a;

    public SecondFunction(double a) {
        this.a = a;
    }

    @Override
    public double solve(double x) {
        Validator.validateArgument(x);
        return (a / x) + Math.sqrt(Math.pow(x, 2) + 1);
    }
}
