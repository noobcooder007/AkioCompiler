/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.LinkedList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import vistas.Principal;

/**
 *
 * @author charg
 */
public class Sintax {

    Principal principal;

    public Sintax(Principal principal) {
        this.principal = principal;
    }

    public void compile(String code) {
        
        byte errores = 0;
    LinkedList<String> ENT = new LinkedList<>();
    LinkedList<String> DEC = new LinkedList<>();
    LinkedList<String> TEXT = new LinkedList<>();
    LinkedList<String> TAKE = new LinkedList<>();

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
            start3 = "((\\s)*STOP(\\s)*(\\}))",
            when2 = "((\\s)*WHILE(\\s)*(\\()(\\s)*" + condicion + "(\\s)*(\\))(\\s)*(\\{))",
            when3 = "((\\s)*EWHILE(\\s)*(\\}))",
            it2 = "((\\s)*IF(\\s)*(\\()(\\s)*" + condicion + "(\\s)*(\\))(\\s)*(\\{))",
            it3 = "((\\s)*EIF(\\s)*(\\}))",
            entero = "[0-9]*",
            decimal = "[0-9]*.[0-9]+";

        LinkedList<Integer> error = new LinkedList<>();
        StringTokenizer st = new StringTokenizer(code, ";{}", true);
        String token = "", txt = "", e;
        int i = 1, mainE = 0, start = 0, when = 0, it = 0, eB = 0;

        if (code.matches(main)) {

            while (st.hasMoreTokens()) {
                token = st.nextToken();
                if (st.hasMoreTokens()) {
                    token = token + st.nextToken();
                }
                if (token.matches("[.\\W\\w\\s]*(\\})") && st.countTokens() == 1) {
                    String auxTok = st.nextToken();
                    token = token + (auxTok.substring(0, auxTok.indexOf("\n")));
                }
                StringTokenizer lin = new StringTokenizer(token, "\n", true);
                while (lin.hasMoreTokens()) {
                    e = lin.nextToken();
                    if ("\n".equals(e)) {
                        i++;
                    }
                }

                if (token.matches(start2)) {
                    start++;
                }
                if (token.matches(start3)) {
                    start--;
                }
                if (token.matches(when2)) {
                    when++;
                }
                if (token.matches(when3)) {
                    when--;
                }
                if (token.matches(it2)) {
                    it++;
                }
                if (token.matches(it3)) {
                    it--;
                }
                if ((st.hasMoreTokens() == false && (start > 0 || when > 0 || it > 0)) || (start < 0 || when < 0 || it < 0)) {
                    eB = 1;
                }

                if ((token.matches(send) || token.matches(take) || token.matches(var) || token.matches(defVal) || token.matches(main2) || token.matches(main3) || token.matches("(\\s)*(\\$)") || token.matches(start2) || token.matches(start3) || token.matches(when2) || token.matches(when3) || token.matches(it2) || token.matches(it3)) && eB == 0) {
                    if (token.matches(take)) {

                    }
                    if (token.matches(var)) {
                        StringTokenizer stTipo = new StringTokenizer(token, " ,;");
                        String tipo = stTipo.nextToken();

                        if (tipo.contains("INT")) {

                            while (stTipo.hasMoreTokens()) {
                                tipo = stTipo.nextToken();

                                if (ENT.contains(tipo) || DEC.contains(tipo) || TEXT.contains(tipo) || TAKE.contains(tipo)) {
                                    JOptionPane.showMessageDialog(null, "La Variable esta repetida (" + tipo + ") " + i + ": \n"
                                            + "________________________________________________________________________\n" + token);
                                    errores = 1;
                                    break;
                                }

                                ENT.add(tipo);
                            }
                        }
                        if (tipo.contains("DOU")) {

                            while (stTipo.hasMoreTokens()) {
                                tipo = stTipo.nextToken();

                                if (ENT.contains(tipo) || DEC.contains(tipo) || TEXT.contains(tipo) || TAKE.contains(tipo)) {
                                    JOptionPane.showMessageDialog(null, "La Variable esta repetida (" + tipo + ") " + i + ": \n"
                                            + "________________________________________________________________________\n" + token);
                                    errores = 1;
                                    break;
                                }

                                DEC.add(tipo);
                            }
                        }
                        if (tipo.contains("SCAN")) {

                            while (stTipo.hasMoreTokens()) {
                                tipo = stTipo.nextToken();

                                if (ENT.contains(tipo) || DEC.contains(tipo) || TEXT.contains(tipo) || TAKE.contains(tipo)) {
                                    JOptionPane.showMessageDialog(null, "La Variable esta repetida (" + tipo + ") " + i + ": \n"
                                            + "________________________________________________________________________\n" + token);
                                    errores = 1;
                                    break;
                                }

                                TAKE.add(tipo);
                            }
                        }
                        if (tipo.contains("TEXT")) {

                            while (stTipo.hasMoreTokens()) {
                                tipo = stTipo.nextToken();

                                if (ENT.contains(tipo) || DEC.contains(tipo) || TEXT.contains(tipo) || TAKE.contains(tipo)) {
                                    JOptionPane.showMessageDialog(null, "La Variable esta repetida (" + tipo + ") " + i + ": \n"
                                            + "________________________________________________________________________\n" + token);
                                    errores = 1;
                                    break;
                                }

                                TEXT.add(tipo);
                            }
                        }
                    }
                    if (token.matches(defVal)) {
                        StringTokenizer stComprobar = new StringTokenizer(token, " \n\t=;");
                        String ID = stComprobar.nextToken(), comprobar = "", tok = "";
                        //System.out.print(ID);
                        while (stComprobar.hasMoreTokens()) {
                            comprobar += stComprobar.nextToken();
                        }

                        if (ENT.contains(ID)) {
                            StringTokenizer stComprobarE = new StringTokenizer(comprobar, "+*/-");
                            while (stComprobarE.hasMoreTokens()) {
                                tok = stComprobarE.nextToken();

                                if (tok.matches(id)) {
                                    if (ENT.contains(tok)); else {
                                        JOptionPane.showMessageDialog(null, "Tipos Incompatibles (" + tok + ") " + i + ": \n"
                                                + "________________________________________________________________________\n" + token);
                                        errores = 1;
                                        break;
                                    }
                                } else {
                                    if (tok.matches(entero)); else {
                                        JOptionPane.showMessageDialog(null, "Tipos Incompatibles (" + tok + ") " + i + ": \n"
                                                + "________________________________________________________________________\n" + token);
                                        errores = 1;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (DEC.contains(ID)) {
                                StringTokenizer stComprobarD = new StringTokenizer(comprobar, "+*/-");
                                while (stComprobarD.hasMoreTokens()) {
                                    tok = stComprobarD.nextToken();

                                    if (tok.matches(id)) {
                                        if (DEC.contains(tok)); else {
                                            JOptionPane.showMessageDialog(null, "Tipos Incompatibles (" + tok + ") " + i + ": \n"
                                                    + "________________________________________________________________________\n" + token);
                                            errores = 1;
                                            break;
                                        }
                                    } else {
                                        if (tok.matches(decimal)); else {
                                            JOptionPane.showMessageDialog(null, "Tipos Incompatibles (" + tok + ") " + i + ": \n"
                                                    + "________________________________________________________________________\n" + token);
                                            errores = 1;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                if (TEXT.contains(ID)) {
                                    if (comprobar.matches("((((\")[.\\W\\w\\s]*(\"))|(" + id + "))((\\s)*(\\+)((\\s)*((\")[.\\W\\w\\s]*(\"))|(" + id + ")))*)")); else {
                                        JOptionPane.showMessageDialog(null, "Tipos Incompatibles (" + tok + ") " + i + ": \n"
                                                + "________________________________________________________________________\n" + token);
                                        errores = 1;
                                        break;
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Variable no declarada " + i + ": \n"
                                            + "________________________________________________________________________\n" + token);
                                    errores = 1;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    if (token.contains("PRINT")) {
                        JOptionPane.showMessageDialog(null, "Error al declarar sentencia PRINT en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("INT") || token.contains("DOU") || token.contains("TEXT")) {
                        JOptionPane.showMessageDialog(null, "Error en declaracion de variables en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("SCAN")) {
                        JOptionPane.showMessageDialog(null, "Error en lectura de valor SCAN en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("STOP}")) {
                        JOptionPane.showMessageDialog(null, "Cierre de Ciclo FOR incorrecto  en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("FOR")) {
                        JOptionPane.showMessageDialog(null, "Inicio de Ciclo FOR incorrecto  en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("EWHILE")) {
                        JOptionPane.showMessageDialog(null, "Cierre de ciclo WHILE incorrecto en la linea " + i + ": \n"
                                + "\n" + token);
                        break;
                    }
                    if (token.contains("WHILE")) {
                        JOptionPane.showMessageDialog(null, "Inicio de ciclo WHILE incorrecto en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("EIF")) {
                        JOptionPane.showMessageDialog(null, "Cierre de condicion IF incorrecto en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("IF")) {
                        JOptionPane.showMessageDialog(null, "Inicio de IF incorrecto; en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Sintaxis Erronea en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                }
            }
        } else {
            st = new StringTokenizer(code, ";{}", true);
            while (st.hasMoreTokens()) {
                token = st.nextToken();
                if (st.hasMoreTokens()) {
                    token = token + st.nextToken();
                }
                if (token.matches("[.\\W\\w\\s]*(\\})") && st.countTokens() == 1) {
                    String auxTok = st.nextToken();
                    token = token + (auxTok.substring(0, auxTok.indexOf("\n")));
                }
                StringTokenizer lin = new StringTokenizer(token, "\n", true);
                while (lin.hasMoreTokens()) {
                    e = lin.nextToken();
                    if ("\n".equals(e)) {
                        i++;
                    }
                }
                if (eB == 1) {
                    break;
                }
                if (token.matches(start2)) {
                    start++;
                }
                if (token.matches(start3)) {
                    start--;
                }
                if (token.matches(when2)) {
                    when++;
                }
                if (token.matches(when3)) {
                    when--;
                }
                if (token.matches(it2)) {
                    it++;
                }
                if (token.matches(it3)) {
                    it--;
                }
                if ((st.hasMoreTokens() == false && (start > 0 || when > 0 || it > 0)) || (start < 0 || when < 0 || it < 0)) {
                    eB = 1;
                }

                if ((token.matches(send) || token.matches(take) || token.matches(var) || token.matches(defVal) || token.matches(main2) || token.matches(main3) || token.matches("(\\s)*(\\$)") || token.matches(start2) || token.matches(start3) || token.matches(when2) || token.matches(when3) || token.matches(it2) || token.matches(it3)) && eB == 0) {
                    if (token.matches(main3)) {
                        eB = 1;
                    }
                } else {
                    if (token.contains("PRINT")) {
                        JOptionPane.showMessageDialog(null, "Error al declarar sentencia PRINT en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("INT") || token.contains("DOU") || token.contains("TEXT")) {
                        JOptionPane.showMessageDialog(null, "Error en declaracion de variables  en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("SCAN")) {
                        JOptionPane.showMessageDialog(null, "Error en lectura de valor SCAN en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("STOP}")) {
                        JOptionPane.showMessageDialog(null, "Cierre de Ciclo FOR incorrecto en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("FOR")) {
                        JOptionPane.showMessageDialog(null, "Inicio de Ciclo FOR incorrecto  en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("EWHILE")) {
                        JOptionPane.showMessageDialog(null, "Cierre de ciclo WHILE incorrecto  en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("WHILE")) {
                        JOptionPane.showMessageDialog(null, "Inicio de ciclo WHILE incorrecto  en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("EIF")) {
                        JOptionPane.showMessageDialog(null, "Cierre de condicion IF incorrecto; en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                    if (token.contains("IF")) {
                        JOptionPane.showMessageDialog(null, "Inicio de IF incorrecto en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Sintaxis Erronea en la linea " + i + ": \n"
                                + "\n" + token);
                        errores = 1;
                        break;
                    }
                }
            }
            if (mainE == 0) {
                JOptionPane.showMessageDialog(null, "Cierre de Clase incorrecto en la Linea " + i + ": \n"
                        + "\n" + token);
                errores = 1;
            }
        }
        principal.FinishSintax = !(errores>0);
    }
}
