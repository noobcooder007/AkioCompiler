/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vistas.Principal;

/**
 *
 * @author charg
 */
public class Lexer {

    int var = 1, com = 1, wd = 1, num = 1, err = 0;
    int cont = 0, saltos = 1, chars = 0, lineChars = 0, linePunts = 0;
    String alias;
    String parse = "";
    String patron = ("(BEGIN|END|TEXT|INT|DOU|BOL|TRUE|FALSE|PRINT|SCAN|IF|FOR|WHILE|INC|TO|STOP|EWHILE|EIF)"
            + "|([<|>])|([+|\\-|*|/])|([=|,|;|'])|([{|}|(|)])|(@[a-zA-Z0-9]+)|(#[^#|\n]+)|([:|^|%|&|°|¬|\\\\|¿|?|!|$|¡|~|´|¨|`])|([a-zA-Z]+)|(([0-9]+(\\.)[0-9]+)|([0-9]+))|([\n])");
    Pattern p = Pattern.compile(patron);
    Token token;
    Principal principal;

    public Lexer(Principal principal) {
        this.principal = principal;
    }

    public String compile(String code) {
        Matcher m = p.matcher(code);
        while (m.find()) {
            if (m.group(1) != null) {
                alias = getAlias(m.toMatchResult().group(0), 0);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "PR", alias, String.valueOf(saltos), String.valueOf(chars + 2));
                principal.object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(2) != null) {
                alias = getAlias(m.toMatchResult().group(0), 1);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "OR", alias, String.valueOf(saltos), String.valueOf(chars + 1));
                principal.object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(3) != null) {
                alias = getAlias(m.toMatchResult().group(0), 2);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "OA", alias, String.valueOf(saltos), String.valueOf(chars + 1));
                principal.object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(4) != null) {
                alias = getAlias(m.toMatchResult().group(0), 3);
                if ("SP03".equals(alias)) {
                    linePunts = lineChars + 3;
                    lineChars = m.end() + 2;
                    chars = lineChars - linePunts;
                } else {
                    chars = m.start() - lineChars;
                }
                token = new Token(m.toMatchResult().group(0), "SP", alias, String.valueOf(saltos), String.valueOf(chars + 1));
                principal.object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(5) != null) {
                alias = getAlias(m.toMatchResult().group(0), 4);
                if ("SA01".equals(alias) | "SA02".equals(alias)) {
                    linePunts = lineChars + 3;
                    lineChars = m.end() + 2;
                    chars = lineChars - linePunts;
                } else {
                    chars = m.start() - lineChars;
                }
                token = new Token(m.toMatchResult().group(0), "SA", alias, String.valueOf(saltos), String.valueOf(chars + 1));
                principal.object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(6) != null) {
                alias = getAlias(m.toMatchResult().group(0), 5);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "VAR", alias, String.valueOf(saltos), String.valueOf(chars + 1));
                principal.object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(7) != null) {
                //System.out.println("COMENTARIO: " + m.toMatchResult().group());
                code = code.replaceAll(m.toMatchResult().group(), "");
            } else if (m.group(8) != null) {
                int row = 1, col = 1;
                if (principal.object.size() > 0) {
                    row = Integer.parseInt(principal.object.get(principal.object.size() - 1).getRow());
                    col = Integer.parseInt(principal.object.get(principal.object.size() - 1).getCol());
                }
                System.out.println("Caracter erroneo: [" + row + ", " + col + "]");
                err++;
                break;
            } else if (m.group(9) != null) {
                alias = getAlias(m.toMatchResult().group(0), 6);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "PAL", alias, String.valueOf(saltos), String.valueOf(chars + 1));
                principal.object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(10) != null) {
                alias = getAlias(m.toMatchResult().group(0), 7);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "NUM", alias, String.valueOf(saltos), String.valueOf(chars + 1));
                principal.object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(11) != null) {
                cont++;
                saltos++;
            }

        }
        parse = (cont > 0) ? parse.substring(0, parse.length() - 1) + "$" : "$";
        lineChars = 0;
        cont = 0;
        saltos = 1;
        var = 1;
        wd = 1;
        num = 1;
        principal.FinishLexer = !(err > 0);
        err = 0;
        return parse;
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

    private String getAlias(String token, int n) {
        switch (n) {
            case 0:
                switch (token) {
                    case "BEGIN":
                        alias = "PR01";
                        break;
                    case "END":
                        alias = "PR02";
                        break;
                    case "TEXT":
                        alias = "PR03";
                        break;
                    case "INT":
                        alias = "PR04";
                        break;
                    case "DOU":
                        alias = "PR05";
                        break;
                    case "BOL":
                        alias = "PR06";
                        break;
                    case "TRUE":
                        alias = "PR07";
                        break;
                    case "FALSE":
                        alias = "PR08";
                        break;
                    case "PRINT":
                        alias = "PR09";
                        break;
                    case "SCAN":
                        alias = "PR10";
                        break;
                    case "IF":
                        alias = "PR11";
                        break;
                    case "EIF":
                        alias = "PR12";
                        break;
                    case "FOR":
                        alias = "PR13";
                        break;
                    case "INC":
                        alias = "PR14";
                        break;
                    case "TO":
                        alias = "PR15";
                        break;
                    case "STOP":
                        alias = "PR16";
                        break;
                    case "WHILE":
                        alias = "PR17";
                        break;
                    case "EWHILE":
                        alias = "PR18";
                        break;
                    
                }
                break;
            case 1:
                switch (token) {
                    case "<":
                        alias = "OR01";
                        break;
                    case ">":
                        alias = "OR02";
                        break;
                }
                break;
            case 2:
                switch (token) {
                    case "+":
                        alias = "OA01";
                        break;
                    case "-":
                        alias = "OA02";
                        break;
                    case "*":
                        alias = "OA03";
                        break;
                    case "/":
                        alias = "OA04";
                        break;
                }
                break;
            case 3:
                switch (token) {
                    case "=":
                        alias = "SP01";
                        break;
                    case ",":
                        alias = "SP02";
                        break;
                    case ";":
                        alias = "SP03";
                        break;
                    case "'":
                        alias = "SP04";
                        break;
                }
                break;
            case 4:
                switch (token) {
                    case "{":
                        alias = "SA01";
                        break;
                    case "}":
                        alias = "SA02";
                        break;
                    case "(":
                        alias = "SA03";
                        break;
                    case ")":
                        alias = "SA04";
                        break;
                }
                break;
            case 5:
                alias = "VAR" + var;
                var++;
                break;
            case 6:
                alias = "PAL" + wd;
                wd++;
                break;
            case 7:
                alias = "NUM" + num;
                num++;
                break;
        }
        return alias;
    }

}
