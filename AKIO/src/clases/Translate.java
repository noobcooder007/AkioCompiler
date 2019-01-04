/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.StringTokenizer;
import vistas.Principal;

/**
 *
 * @author charg
 */
public class Translate {

    Principal principal;

    public Translate(Principal principal) {
        this.principal = principal;
    }

    public void compile(String code) {
        principal.bas = "";
        String simbolo = "([=<>])",
                id = "(@[(a-z)(A-Z)](\\w)*)",
                id2 = "([(a-z)(A-Z)](\\w)*)",
                num = "((\\d)+)",
                dec = "((\\d)+(\\.)(\\d)+)",
                text = "((((')[.\\W\\w\\s]*('))|(" + id + "))((\\s)*(\\+)((\\s)*((')[.\\W\\w\\s]*('))|(" + id + ")))*)",
                send = "((\\s)*PRINT(\\s)*(\\()(\\s)*((((')[.\\W\\w\\s]*('))|(" + id + "))((\\s)*(\\+)((\\s)*((')[.\\W\\w\\s]*('))|(" + id + ")))*)(\\s)*(\\))(\\s)*(;))",
                //take = "((\\s)*TAKE(\\b)(\\s)*"+id+"((\\s)*(,(\\s)*"+id+"))*(\\s)*(;))",
                take = "((\\s)*SCAN(\\s)*(\\()(\\s)*((((')[.\\W\\w\\s]*('))|(" + id + "))((\\s)*(\\+)((\\s)*((')[.\\W\\w\\s]*('))|(" + id + ")))*)(\\s)*(\\))(\\s)*(;))",
                operaciones = "((" + id + "|" + num + "|" + dec + ")(\\s)*([+-/*](\\s)*(" + id + "|" + num + "|" + dec + "))+)",
                defVal = "((\\s)*" + id + "(\\s)*=(\\s)*(" + id + "|" + text + "|" + operaciones + "|" + num + "|" + dec + ")(\\s)*(;))",
                defValVar = "((\\s)*" + id + "(\\s)*=(\\s)*(" + id + "|" + text + "|" + operaciones + "|" + num + "|" + dec + ")(\\s)*)",
                condicion = id + "(\\s)*" + simbolo + "(\\s)*(" + id + "|" + num + "|" + dec + ")((\\s)*([(&&)(||)](\\s)*" + id + "(\\s)*" + simbolo + "(\\s)*(" + id + "|" + num + "|" + dec + ")))*",
                var = "((\\s)*((INT)|(DOU)|(TEXT))(\\b)(\\s)*(" + id + "|" + defValVar + ")((\\s)*(,(\\s)*(" + id + "|" + defValVar + ")))*(\\s)*(;))",
                main = "((\\s)*" + id2 + "(\\b)(\\s)*BEGIN(\\s)*(\\{)[.\\W\\w\\s]*(END(\\s)*(\\})(\\s)*)$)",
                main2 = "((\\s)*" + id2 + "(\\b)(\\s)*BEGIN(\\s)*(\\{))",
                main3 = "((\\s)*END(\\s)*(\\})(\\s)*)",
                start2 = "((\\s)*FOR(\\b)(\\s)*(" + id + "|" + num + ")(\\b)(\\s)*(=)(" + id + "|" + num + ")(\\b)(\\s)*(INC)(\\b)(\\s)*" + num + "(\\s)*[+-]?(\\s)*(\\b)(TO)(\\b)(\\s)*(" + id + "|" + num + ")(\\s)*(\\{))",
                foresito = "((\\s)*FOR(\\b)(\\s)*(" + id + "|" + num + ")(\\b)(\\s)*(TO)(\\b)(\\s)*(" + id + "|" + num + ")(\\s)*)",
                start3 = "((\\s)*STOP(\\s)*(\\}))",
                when2 = "((\\s)*WHILE(\\s)*(\\()(\\s)*" + condicion + "(\\s)*(\\))(\\s)*(\\{))",
                when3 = "((\\s)*EWHILE(\\s)*(\\}))",
                it2 = "((\\s)*IF(\\s)*(\\()(\\s)*" + condicion + "(\\s)*(\\))(\\s)*(\\{))",
                it3 = "((\\s)*EIF(\\s)*(\\}))",
                entero = "[0-9]*",
                step = "(INC)(\\b)(\\s)*" + num + "(\\s)*[+-]?(\\s)*(\\b)",
                to = "TO(\\b)(\\s)*(" + id + "|" + num + ")(\\s)*(\\{)",
                decimal = "[0-9]*.[0-9]+";

        StringTokenizer st = new StringTokenizer(code, "\n");
        String token;
        while (st.hasMoreTokens()) {
            //JOptionPane.showMessageDialog(this,"Identificando Variables...");
            token = st.nextToken();

            if (token.matches(main2)) {

                String tokinn = "'";
                StringTokenizer tokin = new StringTokenizer(token, " \n");
                while (tokin.hasMoreTokens()) {
                    String testo = "";
                    testo = testo + principal.bas;
                    tokinn = tokin.nextToken();
                    if (tokinn.contains("BEGIN")) {
                        principal.bas = testo + "REM ";

                    }
                    if (tokinn.matches(id2) && tokinn.contains("BEGIN") == false && tokinn.contains("{") == false) {
                        principal.bas = "REM LENGUAJES Y AUTOMATAS II;\nREM " + testo + tokinn;
                    }
                    if (tokinn.contains("{")) {
                        principal.bas = testo + " ;\n";
                    }
                }

            }

            if (token.matches(var)) {
                String a = "";
                a = principal.bas;
                a = a + "DIM  ";
                principal.bas = a;
                String tokinn = "";
                StringTokenizer tokin = new StringTokenizer(token, " \n,;");
                while (tokin.hasMoreTokens()) {
                    String testo = "";
                    testo = testo + principal.bas;
                    tokinn = tokin.nextToken();

                    if (tokinn.contains("INT") || tokinn.contains("DOU") || tokinn.contains("TEXT")) {
                        String enteros = "";
                        if (tokinn.contains("INT")) {
                            enteros = " AS INTEGER";
                        }
                        if (tokinn.contains("DOU")) {
                            enteros = " AS DOUBLE";
                        }
                        if (tokinn.contains("TEXT")) {
                            enteros = " AS STRING";
                        }

                        int contador = 0;
                        while (tokin.hasMoreTokens()) {
                            tokinn = tokin.nextToken();
                            if (tokinn.equals(";")) {

                            } else {
                                if (contador >= 1) {
                                    enteros = tokinn + "," + enteros;
                                } else {
                                    enteros = tokinn + enteros;
                                }
                            }
                            contador += 1;
                        }
                        principal.bas = testo + enteros + "\n";
                    }

                }
            }

        }
        String b = principal.bas;
        b = b + "  \n";
        principal.bas = b;

        StringTokenizer st1 = new StringTokenizer(code, "\n");
        String token1;
        while (st1.hasMoreTokens()) {
            token1 = st1.nextToken();
            if (token1.matches(start3)) {
                String a = principal.bas + "\nNEXT\n";
                principal.bas = a;
            }
            if (token1.matches(when2)) {
                StringTokenizer st2 = new StringTokenizer(token1, "()");
                while (st2.hasMoreTokens()) { // poner primero por espacio con tokens y luego dentro de espacio hacer ciclo y hacer tokens por cada uno
                    String tuken = st2.nextToken();
                    if (tuken.contains("=") || tuken.contains("<") || tuken.contains(">")) {
                        if (tuken.contains("=")) {
                            StringTokenizer st3 = new StringTokenizer(tuken, "=");
                            while (st3.hasMoreTokens()) {
                                String tuken2 = st3.nextToken();

                                if (st3.hasMoreTokens() == true) {
                                    String a = principal.bas + tuken2 + "=";
                                    principal.bas = a;
                                } else {
                                    String a = principal.bas + tuken2;
                                    principal.bas = a;
                                }
                            }
                        } else {
                            String a = principal.bas + tuken;
                            principal.bas = a;
                        }
                    }

                    if (tuken.contains("WHILE")) {
                        String a = principal.bas + "\nWHILE ";
                        principal.bas = a;
                    }
                    if (tuken.contains("{")) {
                        String a = principal.bas + "\n";
                        principal.bas = a;
                    }
                }
            }
            if (token1.matches(when3)) {
                String a = principal.bas + "\nWEND \n";
                principal.bas = a;
            }
            if (token1.matches(it2)) {
                StringTokenizer st2 = new StringTokenizer(token1, "()");
                while (st2.hasMoreTokens()) { // poner primero por espacio con tokens y luego dentro de espacio hacer ciclo y hacer tokens por cada uno
                    String tuken = st2.nextToken();
                    if (tuken.contains("=") || tuken.contains("<") || tuken.contains(">")) {
                        if (tuken.contains("=")) {
                            StringTokenizer st3 = new StringTokenizer(tuken, "=");
                            while (st3.hasMoreTokens()) {
                                String tuken2 = st3.nextToken();

                                if (st3.hasMoreTokens() == true) {
                                    String a = principal.bas + tuken2 + "=";
                                    principal.bas = a;
                                } else {
                                    String a = principal.bas + tuken2;
                                    principal.bas = a;
                                }

                            }
                        } else {
                            String a = principal.bas + tuken;
                            principal.bas = a;
                        }
                    }

                    if (tuken.contains("IF")) {
                        String a = principal.bas + "\nIF ";
                        principal.bas = a;
                    }
                    if (tuken.contains("{")) {
                        String a = principal.bas + " THEN\n";
                        principal.bas = a;
                    }
                }
            }
            if (token1.matches(it3)) {
                String a = principal.bas + "\nEND IF \n";
                principal.bas = a;
            }
            if (token1.matches(main3)) {

                String c = principal.bas + "\n";
                principal.bas = (c);
            }
            if (token1.matches(take)) {
                StringTokenizer st2 = new StringTokenizer(token1, "()");
                while (st2.hasMoreTokens()) {
                    String tuken = st2.nextToken();
                    if (tuken.contains("SCAN")) {

                        String a = principal.bas + "\nINPUT  ";
                        a = a.replace('\'', '"');
                        principal.bas = a;
                    }
                    if (tuken.contains("+")) {
                        String tokesito;
                        StringTokenizer tuk = new StringTokenizer(tuken, "+");
                        while (tuk.hasMoreTokens()) {
                            tokesito = tuk.nextToken();
                            if (tuk.hasMoreTokens()) {
                                String a = principal.bas + tokesito + ",";
                                a = a.replace('\'', '"');
                                principal.bas = a;
                            } else {
                                String a = principal.bas + tokesito;
                                a = a.replace('\'', '"');

                                principal.bas = a;
                            }
                        }
                    }

                    if (tuken.contains(";")) {
                        String a = principal.bas + "\n";
                        a = a.replace('\'', '"');
                        principal.bas = a;

                    }
                    if (tuken.contains("SCAN") == false && tuken.contains("+") == false && tuken.contains(";") == false) {
                        String a = principal.bas + tuken;
                        a = a.replace('\'', '"');
                        principal.bas = a;
                    }
                }
            }

            if (token1.matches(send)) {
                StringTokenizer st2 = new StringTokenizer(token1, "()");
                while (st2.hasMoreTokens()) {
                    String tuken = st2.nextToken();
                    if (tuken.contains("PRINT")) {

                        String a = principal.bas + "\nPRINT  ";
                        a = a.replace('\'', '"');

                        principal.bas = a;
                    }
                    if (tuken.contains("+")) {
                        String tokesito;
                        StringTokenizer tuk = new StringTokenizer(tuken, "+");
                        while (tuk.hasMoreTokens()) {
                            tokesito = tuk.nextToken();
                            if (tuk.hasMoreTokens()) {
                                String a = principal.bas + tokesito + ",";
                                a = a.replace('\'', '"');
                                principal.bas = a;
                            } else {
                                String a = principal.bas + tokesito;
                                a = a.replace('\'', '"');
                                principal.bas = a;
                            }
                        }
                    }

                    if (tuken.contains(";")) {
                        String a = principal.bas + " \n";
                        a = a.replace('\'', '"');
                        principal.bas = a;
                    }
                    if (tuken.contains("PRINT") == false && tuken.contains("+") == false && tuken.contains(";") == false) {
                        String a = principal.bas + tuken;
                        a = a.replace('\'', '"');
                        principal.bas = a;
                    }
                }
            }

            if (token1.matches(defVal)) {
                String tokesito;
                StringTokenizer tuk = new StringTokenizer(token1);
                while (tuk.hasMoreTokens()) {
                    tokesito = tuk.nextToken();
                    String a = principal.bas + tokesito + "\n";
                    a = a.replace(';', ' ');
                    principal.bas = a;
                }
            }

            if (token1.matches(start2)) { //Compara si esta el matches dentro del token
                String tokesito = "";
                String texto = "";
                StringTokenizer tuk = new StringTokenizer(token1, "\\s");
                while (tuk.hasMoreTokens()) {
                    tokesito = tuk.nextToken();
                    String a = principal.bas + "\n";//almacenoo todo
                    tokesito = tokesito.replace("FOR", "FOR");//reemplazo
                    tokesito = tokesito.replaceAll(step, " ");
                    tokesito = tokesito.replace("{", "\n");
                    texto += a + tokesito;//se guarda en el acumulador de lo reemplazado
                    principal.bas = texto;

                }
            }
        }
    }
}
