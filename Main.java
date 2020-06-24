import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main
 */
public class Main {

    private static String inputString;
    private static String[] arrays_inputs;
    private static Scanner sc = new Scanner(System.in);
    private static Stack<String> rules = new Stack<>();
    private static String auxPrint = "";
    private static int contador;
    private static Pattern p;
    private static Matcher m;

    public static void main(final String[] args) {
        do {
            System.out.println("Ingrese la entrada a evaluar: ");
            inputString = sc.nextLine();
            if (inputString.isEmpty()) {
                System.out.println("Error, cadena vacia\nIntente con otra: ");
                inputString = sc.nextLine();
            }
        } while (inputString.isEmpty());
        sc.close();
        rules.push("PARAMETROS");
        rules.push("NOMBRE");
        rules.push("function");
        startEvaluate();
    }

    private static void startEvaluate() {
        System.out.println("<=====================>");
        arrays_inputs = inputString.split(" ");
        contador = 0;
        printStack();
        if (rules.peek().equals(arrays_inputs[contador])) {
            contador++;
            printStackOut();
            printStack();
            evaluateNombreRule();
            contador++;
            evaluateParametrosRule();
            write();
        } else {
            showMessageError(arrays_inputs[contador], rules.peek(), 50);
        }
    }

    private static void evaluateNombreRule() { // LETRA RESTOL
        printStackOut();
        for (int i = 0; i < arrays_inputs[contador].length(); i++) {
            p = Pattern.compile("\\p{Alpha}");
            m = p.matcher(String.valueOf(arrays_inputs[contador].charAt(i)));
            rules.push("RESTOL");
            rules.push("LETRA");
            if (m.find()) {
                printStack();
                printStackOut();
                printStackOut();
            } else {
                showMessageError(arrays_inputs[contador], rules.peek(), 66);
            }
        }
        printStack();
    }

    private static void evaluateParametrosRule() { // ( PARAMLIST )
        p = Pattern.compile("\\(");
        m = p.matcher(arrays_inputs[contador]);
        if (m.find()) {
            contador++;
            printStackOut();
            rules.push(")");
            rules.push("PARAMLIST");
            rules.push("(");
            printStack();
            printStackOut();
            evaluateParamlistRule();
        } else {
            showMessageError(arrays_inputs[contador], rules.peek(), 85);
        }
    }

    private static void evaluateParamlistRule() { // var VARIABLE LIST
        p = Pattern.compile("var");
        m = p.matcher(arrays_inputs[contador]);
        if (m.find()) {
            printStackOut();
            rules.push("LIST");
            rules.push("VARIABLE");
            rules.push("var");
            printStack();
            printStackOut();
            contador++;
            evaluateVariableRule();
            contador++;
            evaluateFinal();
        } else {
            showMessageError(arrays_inputs[contador], rules.peek(), 113);
        }
    }

    private static void evaluateVariableRule() { // NOMBRE : TIPO
        printStackOut();
        rules.push("TIPO");
        rules.push(":");
        rules.push("NOMBRE");
        printStack();
        evaluateNombreRule();
        contador++;
        p = Pattern.compile("\\:");
        m = p.matcher(arrays_inputs[contador]);
        if (m.find()) {
            contador++;
            printStackOut();
            printStack();
            evaluateTipoRule();
            printStackOut();
            printStack();
        } else {
            showMessageError(arrays_inputs[contador], rules.peek(), 140);
        }
    }

    private static void evaluateTipoRule() { // integer | real | boolean | string
        p = Pattern.compile("(integer|real|boolean|string)");
        m = p.matcher(arrays_inputs[contador]);
        if (!m.find()) {
            showMessageError(arrays_inputs[contador], rules.peek(), 148);
        }
    }

    private static void evaluateFinal() {
        try {
            do {
                if (arrays_inputs[contador].equals(";")) {
                    printStackOut();
                    rules.push("LIST");
                    rules.push("VARIABLE");
                    rules.push(";");
                    printStack();
                    evaluateListRule();
                } else if (arrays_inputs[contador].equals(")")) {
                    printStackOut();
                    printStack();
                    printStackOut();
                    printStack();
                    showMessageSuccessfully();
                }
                contador++;
            } while (!rules.isEmpty());
        } catch (Exception arrayIndexOutOfBoundsException) {
            showMessageError(arrayIndexOutOfBoundsException.toString(), rules.peek(), 158);
        }
    }

    private static void evaluateListRule() {
        p = Pattern.compile("\\;");
        m = p.matcher(arrays_inputs[contador]);
        if (m.find()) {
            printStackOut();
            printStack();
            contador++;
            evaluateVariableRule();
        } else {
            showMessageError(arrays_inputs[contador], rules.peek(), 161);
        }
    }

    private static void printStack() {
        auxPrint = auxPrint + "[";
        for (int i = 0; i < rules.size(); i++) {
            if (i == rules.size() - 1)
                auxPrint = auxPrint + rules.get(i);
            else
                auxPrint = auxPrint + rules.get(i) + ", ";
        }
        auxPrint = auxPrint + "]\n";
    }

    private static void printStackOut() {
        auxPrint = auxPrint + "Sale: " + rules.pop() + "\n";
    }

    private static void write() {
        System.out.println(auxPrint);
    }

    private static void showMessageSuccessfully() {
        write();
        System.out.println("Entrada correcta :)");
        System.exit(1);
    }

    private static void showMessageError(String data, String expectedString, int line) {
        write();
        System.out.println(
                "Linea #" + line + ":  Detectamos el error en " + data + "  Cuando se esperaba: " + expectedString);
        System.exit(1);
    }
}