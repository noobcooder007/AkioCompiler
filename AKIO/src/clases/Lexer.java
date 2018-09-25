/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

/**
 *
 * @author charg
 */
public class Lexer {

    ArrayList<Token> object;
    int var = 1, com = 1, wd = 1, num = 1;
    int cont = 0, saltos = 1, chars = 0, lineChars = 0;
    String alias;
    String parse = "";
    String patron = ("(setup|main|Akio|text|int|dou|bol|true|false|var|print|scan|type|case|if|not|for|switch|break|nul)\\b"
            + "|([&|||!])|([<|>])|([+|-|*|/|^|%])|([_|=|,|:|?|;])|([{|}|(|)])|(@[a-zA-Z0-9]+)|(#[^#|\n]+)|('[^']+')|([0-9]+)|([\n])");
    Pattern p = Pattern.compile(patron);
    Token token;

    public Lexer(ArrayList object) {
        this.object = object;
    }

    public String compile(String code) {
        Matcher m = p.matcher(code);
        while (m.find()) {
            if (m.group(1) != null) {
                alias = getAlias(m.toMatchResult().group(0), 0);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "PR", alias, "[" + saltos + "," + (chars) + "]");
                object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(2) != null) {
                alias = getAlias(m.toMatchResult().group(0), 1);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "OL", alias, "[" + saltos + "," + (chars) + "]");
                object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(3) != null) {
                alias = getAlias(m.toMatchResult().group(0), 2);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "OR", alias, "[" + saltos + "," + (chars) + "]");
                object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(4) != null) {
                alias = getAlias(m.toMatchResult().group(0), 3);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "OA", alias, "[" + saltos + "," + (chars) + "]");
                object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(5) != null) {
                alias = getAlias(m.toMatchResult().group(0), 4);
                if ("SP05".equals(alias)) {
                    lineChars = m.end() + 2;
                }
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "SP", alias, "[" + saltos + "," + (chars) + "]");
                object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(6) != null) {
                alias = getAlias(m.toMatchResult().group(0), 5);
                if ("SA01".equals(alias) | "SA02".equals(alias)) {
                    lineChars = m.end() + 2;
                }
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "SA", alias, "[" + saltos + "," + (chars) + "]");
                object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(7) != null) {
                alias = getAlias(m.toMatchResult().group(0), 6);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "VAR", alias, "[" + saltos + "," + (chars) + "]");
                object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(9) != null) {
                alias = getAlias(m.toMatchResult().group(0), 8);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "PAL", alias, "[" + saltos + "," + (chars) + "]");
                object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(10) != null) {
                alias = getAlias(m.toMatchResult().group(0), 9);
                chars = m.start() - lineChars;
                token = new Token(m.toMatchResult().group(0), "NUM", alias, "[" + saltos + "," + (chars) + "]");
                object.add(token);
                cont++;
                parse += alias + " ";
            } else if (m.group(11) != null) {
                cont++;
                saltos++;
            }

        }
        lineChars = 0;
        if (cont > 0) {
            return parse.substring(0, parse.length() - 1);
        } else {
            return "";
        }
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

    private String getAlias(String token, int n) {
        switch (n) {
            case 0:
                switch (token) {
                    case "Akio":
                        alias = "PR01";
                        break;
                    case "setup":
                        alias = "PR02";
                        break;
                    case "main":
                        alias = "PR03";
                        break;
                    case "text":
                        alias = "PR04";
                        break;
                    case "int":
                        alias = "PR05";
                        break;
                    case "dou":
                        alias = "PR06";
                        break;
                    case "bol":
                        alias = "PR07";
                        break;
                    case "true":
                        alias = "PR08";
                        break;
                    case "false":
                        alias = "PR09";
                        break;
                    case "var":
                        alias = "PR10";
                        break;
                    case "print":
                        alias = "PR11";
                        break;
                    case "scan":
                        alias = "PR12";
                        break;
                    case "type":
                        alias = "PR13";
                        break;
                    case "case":
                        alias = "PR14";
                        break;
                    case "if":
                        alias = "PR15";
                        break;
                    case "not":
                        alias = "PR16";
                        break;
                    case "for":
                        alias = "PR17";
                        break;
                    case "switch":
                        alias = "PR18";
                        break;
                    case "break":
                        alias = "PR19";
                        break;
                    case "nul":
                        alias = "PR20";
                        break;
                }
                break;
            case 1:
                switch (token) {
                    case "&":
                        alias = "OL01";
                        break;
                    case "|":
                        alias = "OL02";
                        break;
                    case "!":
                        alias = "OL03";
                        break;
                }
                break;
            case 2:
                switch (token) {
                    case "<":
                        alias = "OR01";
                        break;
                    case ">":
                        alias = "OR02";
                        break;
                }
                break;
            case 3:
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
                    case "^":
                        alias = "OA05";
                        break;
                    case "%":
                        alias = "OA06";
                        break;
                }
                break;
            case 4:
                switch (token) {
                    case "_":
                        alias = "SP01";
                        break;
                    case "=":
                        alias = "SP02";
                        break;
                    case "?":
                        alias = "SP03";
                        break;
                    case ":":
                        alias = "SP04";
                        break;
                    case ";":
                        alias = "SP05";
                        break;
                }
                break;
            case 5:
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
            case 6:
                alias = (var > 9) ? "VAR" + var : "VAR0" + var;
                var++;
                break;
            case 8:
                alias = (wd > 9) ? "PAL" + wd : "PAL0" + wd;
                wd++;
                break;
            case 9:
                alias = (num > 9) ? "NUM" + num : "NUM0" + num;
                num++;
                break;
        }
        return alias;
    }

}
