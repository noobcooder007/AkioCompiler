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
    String alias;
    String parse = "";
    String patron = ("(setup|main|Akio|text|int|dou|bol|true|false|var|print|scan|type|case|if|not|for|switch|break|nul)\\b"
            + "|([&|||!])|([<|>])|([+|-|*|/|^|%])|([_|=|,|:|?])|([{|}|(|)])|(@[a-zA-Z0-9]+)|(#[^#|\n]+)|('[^']+')|([0-9]+)|([\n])");
    Pattern p = Pattern.compile(patron);
    Token token;

    public Lexer(ArrayList object) {
        this.object = object;
    }

    public String compile(String code) {
        int var = 1, com = 1, wd = 1, num = 1;
        int cont = 0, saltos = 0, chars = 0;
        Matcher m = p.matcher(code);
        while (m.find()) {
            
            if (m.group(1) != null) {
                System.out.println(m.toMatchResult().start());
                chars = m.end();
                alias = getAlias(m.toMatchResult().group(0));
                token = new Token(m.toMatchResult().group(0), "PR", alias, "[" + saltos + "," + m.start() + "]");
                cont++;
            } else if (m.group(2) != null) {
                cont++;
            } else if (m.group(3) != null) {
                cont++;
            } else if (m.group(4) != null) {
                cont++;
            } else if (m.group(5) != null) {
                cont++;
            } else if (m.group(6) != null) {
                cont++;
            } else if (m.group(7) != null) {
                cont++;
            } else if (m.group(8) != null) {
                cont++;
            } else if (m.group(9) != null) {
                cont++;
            } else if (m.group(10) != null) {
                cont++;
            } else if (m.group(11) != null) {
                cont++;
                saltos++;
            }

        }
        if (cont > 0) {
            //return parse.substring(0, parse.length() - 1);
            return "";
        } else {
            return "";
        }
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

    private String getAlias(String token) {
        return alias;
    }

}
