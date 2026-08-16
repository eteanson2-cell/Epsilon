package epsilon.model.enums;

public enum Operation{
    ADITTION('+'),
    SUBTRACTION('-'),
    MULTIPLICATION('*'),
    DIVISION('/'),
    POW('^');
    @SuppressWarnings("unused")
    private final char sign;
    private Operation(char sign){
        this.sign = sign;
    }
    public double solveOperation(double number1, double number2){
        switch (sign) {
            case '+' -> {return number1+number2;}
            case '-' -> {return number1-number2;}
            case '*' -> {return number1*number2;}
            case '/' -> {return number1/number2;}
            case '^' -> {return Math.pow(number1, number2) ;}
        }
        return 0.0;
    }
}