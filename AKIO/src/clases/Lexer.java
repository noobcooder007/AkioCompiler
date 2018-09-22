/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author charg
 */
public class Lexer {

    String parse = "";
    String patron = ("(setup|main|Akio|text|int|dou|bol|true|false|var|print|scan|type|case|if|not|for|switch|break|nul)\\b"
            + "|([&|||!])|([<|>])|([+|-|*|/|^|%])|([_|=|,|:|?])|([{|}|(|)])|(@[a-zA-Z0-9]+)|(#[^#|\n]+)|('[^']+')|([0-9]+)");
    Pattern p = Pattern.compile(patron);

    public Lexer() {
    }

    public String compile(String code) {
        int pr = 1, ol = 1, or = 1, oa = 1, sp = 1, sa = 1, var = 1, com = 1, wd = 1, num = 1, cont = 0;
        //code = code.replaceAll("\\s", "");
        Matcher m = p.matcher(code);
        while (m.find()) {
            if (m.group(1) != null) {
                //parse = parse + "PR" + String.valueOf(pr) + ",";
                parse += m.toMatchResult().group(0) + " ";
                pr++;
                cont++;
                //System.out.print(m.toMatchResult().group(0));
                System.out.println("PR ");
            }
            if (m.group(2) != null) {
                //parse = parse + "OL" + String.valueOf(ol) + ",";
                parse += m.toMatchResult().group(0) + " ";
                ol++;
                cont++;
                //System.out.print(m.toMatchResult().group(0));
            }
            if (m.group(3) != null) {
                //parse = parse + "OR" + String.valueOf(or) + ",";
                parse += m.toMatchResult().group(0) + " ";
                or++;
                cont++;
                //System.out.print(m.toMatchResult().group(0));
            }
            if (m.group(4) != null) {
                //parse = parse + "OA" + String.valueOf(oa) + ",";
                parse += m.toMatchResult().group(0) + " ";
                oa++;
                cont++;
                //System.out.print(m.toMatchResult().group(0));
            }
            if (m.group(5) != null) {
                //parse = parse + "SP" + String.valueOf(sp) + ",";
                parse += m.toMatchResult().group(0) + " ";
                sp++;
                cont++;
                //System.out.print(m.toMatchResult().group(0));
            }
            if (m.group(6) != null) {
                //parse = parse + "SA" + String.valueOf(sa) + ",";
                parse += m.toMatchResult().group(0) + " ";
                sa++;
                cont++;
                //System.out.print(m.toMatchResult().group(0));
            }
            if (m.group(7) != null) {
                //parse = parse + "VAR" + String.valueOf(var) + ",";
                parse += m.toMatchResult().group(0) + " ";
                var++;
                cont++;
                //System.out.print(m.toMatchResult().group(0));
            }
            if (m.group(8) != null) {
                //parse = parse + "COM" + String.valueOf(com) + ",";
                com++;
                cont++;
                //System.out.print(m.toMatchResult().group(0));
                System.out.println("COM");
            }
            if (m.group(9) != null) {
                parse += m.toMatchResult().group(0) + " ";
                wd++;
                cont++;
                System.out.println("WD ");
            }
            if (m.group(10) != null) {
                parse += m.toMatchResult().group(0) + " ";
                num++;
                cont++;
            }

        }
        if (cont > 0) return parse.substring(0, parse.length()-1);
        else return "";
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

}
