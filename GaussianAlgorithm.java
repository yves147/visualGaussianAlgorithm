/*
x1  x2  x3     r4
 2   2  -4  |  12
 1   3   1  |   4
-1  -1   3  |  -8

x1 = 0; x2 = 2; x3 = -2;
*/

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class GaussianUtilities {
    static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                System.out.print("\033\143");
            }
        } catch (final Exception e) {
        }
    }

    static void printToDifference(int diff, String val) {
        for (int i = 0; i < diff; i++) {
            System.out.print(val);
        }
    }

    static void printToDifference(int diff) {
        printToDifference(diff, " ");
    }
}

class GaussianSolvedItemList {
    List<GaussianSolvedItem> rawList = new ArrayList<GaussianSolvedItem>();

    GaussianLine visualStep(GaussianLine comparativeGaussianLine){
        
    }

    GaussianLine calcStep(GaussianLine comparativeGaussianLine){
        
    }

    void addSolvedItem(GaussianSolvedItem item){
        rawList.add(item);
    }
}

class GaussianSolvedItem {
    int index;
    double value;

    static GaussianSolvedItem build(int i, double v){
        GaussianSolvedItem t = new GaussianSolvedItem();
        t.index = i;
        t.value = v;
        return t;
    }
}

class GaussianAlignment {
    double xC = 0.0;
    double xV = 0.0;

    void setParticipants(double count, double value) {
        this.xC = count;
        this.xV = value;
    }

    double solve() {
        this.xV /= xC;
        return this.xV;
    }
}

class GaussianAlignmentBuilder {
    GaussianLine preparableAlignmentLine;
    GaussianSolvedItemList preparedSolvedItemList;
    HashMap<Integer, Double> solvedElements = new HashMap<Integer, Double>();

    double unknownCount = 0.0;

    boolean fast = true;

    void setPreparableAlignmentLine(GaussianLine preparableAlignmentLine) {
        this.preparableAlignmentLine = preparableAlignmentLine;
    }

    double purgeZeros() {
        for (int i = 0; i < this.preparableAlignmentLine.values.length; i++) {
            if (this.preparableAlignmentLine.values[i] != 0){
                return this.preparableAlignmentLine.values[i];
            };
        }
    }

    public void setPreparedSolvedItemList(GaussianSolvedItemList preparedSolvedItemList) {
        this.preparedSolvedItemList = preparedSolvedItemList;
    }

    double fillKnown() {
        this.preparedSolvedItemList.calculate(preparableAlignmentLine);
    }

    GaussianAlignment build() {
        GaussianAlignment newAlignment = new GaussianAlignment();
        double leftSideV, rightSideV = this.preparableAlignmentLine.result;
        if (this.fast == true) {
            leftSideV = this.purgeZeros();
        } else {
            double filled = this.fillKnown();
            leftSideV = this.unknownCount;
            rightSideV -= filled;
        }
        newAlignment.setParticipants(leftSideV, rightSideV);
        return newAlignment;
    }
}

class GaussianLine {
    double[] values;
    double result;

    void setValues(double[] newValues) {
        this.values = newValues;
    }

    void setResult(double res) {
        this.result = res;
    }

    GaussianLine multiplyBy(double multiplier) {
        GaussianLine newLine = new GaussianLine();
        double[] newValues = new double[this.values.length];
        for (int i = 0; i < this.values.length; i++) {
            newValues[i] = this.values[i] * multiplier;
        }
        newLine.setValues(newValues);
        double newResult = this.result * multiplier;
        newLine.setResult(newResult);
        return newLine;
    }

    GaussianLine sub(GaussianLine subtrahendLine) {
        GaussianLine newLine = new GaussianLine();
        double[] newValues = new double[this.values.length];
        for (int i = 0; i < this.values.length; i++) {
            newValues[i] = this.values[i] - subtrahendLine.values[i];
        }
        newLine.setValues(newValues);
        double newResult = this.result - subtrahendLine.result;
        newLine.setResult(newResult);
        return newLine;
    }

    void print() {
        for (int i = 0; i < this.values.length; i++) {
            System.out.print("x" + i + "=" + this.values[i] + ";");
        }
        System.out.print("r=" + this.result + "\n");
    }

    void prettyPrint(int biggestLen) {
        String[] prettyItems = new String[this.values.length];
        for (int i = 0; i < this.values.length; i++) {
            String strVal = String.valueOf(this.values[i]);
            prettyItems[i] = strVal;
            if (strVal.length() > biggestLen)
                biggestLen = strVal.length();
        }
        for (int i = 0; i < prettyItems.length; i++) {
            GaussianUtilities.printToDifference(biggestLen - prettyItems[i].length());
            System.out.print(prettyItems[i]);
            if (i != prettyItems.length - 1) {
                System.out.print("  ");
            }
        }
        System.out.print(" | " + this.result + "\n");
    }

    void prettyPrint() {
        prettyPrint(0);
    }

    boolean isLast() {
        boolean _result = true;
        for (int i = 1; i < this.values.length; i++) {
            if (this.values[i] == 0) {
                _result = false;
                break;
            }
        }
        return _result;
    }
}

class GaussianSystem {
    boolean hold = false;
    GaussianLine[] lines;

    void setLineCount(int i) {
        lines = new GaussianLine[i];
    }

    void setCoords(int x, int y, double v) {
        if (existsCoords(x, y)) {
            this.lines[y].values[x] = v;
        } else {
            GaussianUtilities.clearConsole();
            System.out.println("Error: No item at x=" + x + ";y=" + y);
        }
    }

    boolean existsCoords(int x, int y) {
        return y < this.lines.length && x < this.lines[y].values.length;
    }

    double getCoords(int x, int y) {
        if (existsCoords(x, y)) {
            return this.lines[y].values[x];
        } else {
            GaussianUtilities.clearConsole();
            System.out.println("Error: No item at x=" + x + ";y=" + y);
            return Math.E;
        }
    }
}

class GaussianSystemInput {
    int lineCount = 0;
    int varCount = 0;

    void setLineCount(int lines) {
        this.lineCount = lines;
    }

    void setVarCount(int vars) {
        this.varCount = vars;
    }
}

public class GaussianAlgorithm {

    public static void main(String[] args) {
        GaussianLine testLine1 = new GaussianLine();
        double[] testLine1Values = { 2.0, 2.0, -4.0 };
        testLine1.setValues(testLine1Values);
        testLine1.setResult(12.0);

        testLine1.prettyPrint(4);

        GaussianLine testLine2 = new GaussianLine();
        double[] testLine2Values = { 1.0, 3.0, 1.0 };
        testLine2.setValues(testLine2Values);
        testLine2.setResult(4.0);

        testLine2.prettyPrint(4);

        GaussianLine subLine = testLine2.multiplyBy(2.0 / 1.0);
        GaussianLine resultLine = testLine1.sub(subLine);

        resultLine.prettyPrint(4);
    }

}