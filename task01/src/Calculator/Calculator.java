package Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    private BigDecimal firstOperand;
    private String comparator;
    private BigDecimal secondOperand;
    private BigDecimal lastSave;
    private static final String GET_LAST = "$last";

    public Calculator() {
        this.lastSave = new BigDecimal(0);
    }

    public String getComparator() {
        return comparator;
    }

    private boolean isValidComparator(String string) {
        switch (string) {
            case "+": return true;
            case "-": return true;
            case "*": return true;
            case "/": return true;
            default: return false;
        }
    }

    public boolean isValidStringInput(String string) {
        boolean result = true;
        // parse the string
        String[] arrStrings = string.toLowerCase().split(" ");
        try {
            // check for 3 components
            if (arrStrings.length != 3) {
                result = false;
            }
            // check and assign valid comparator
            if (!isValidComparator(arrStrings[1])) {
                result = false;
            } else {
                this.comparator = arrStrings[1];
            }
            // check and assign if first operand is valid number OR $last
            if (arrStrings[0].equals(GET_LAST)) {
                this.firstOperand = this.lastSave;
            } else {
                try {
                    this.firstOperand = new BigDecimal(arrStrings[0]);
                } catch (Exception e) {
                    result = false;
                }
            }
            // check and assign if second operand is valid number OR $last
            if (arrStrings[2].equals(GET_LAST)) {
                this.secondOperand = this.lastSave;
            } else {
                try {
                    this.secondOperand = new BigDecimal(arrStrings[2]);
                } catch (Exception e) {
                    result = false;
                }
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public BigDecimal calculate(String string) {
        switch (getComparator()) {
            case "+": 
                return this.lastSave = this.firstOperand.add(this.secondOperand);
            case "-": 
                return this.lastSave = this.firstOperand.subtract(this.secondOperand);
            case "*":
                return this.lastSave = this.firstOperand.multiply(this.secondOperand);
            case "/":
                return this.lastSave = this.firstOperand.divide(this.secondOperand,2, RoundingMode.HALF_UP);
            default: 
                return this.lastSave;
        }
    }
}