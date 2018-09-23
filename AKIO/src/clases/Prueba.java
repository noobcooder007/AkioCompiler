/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.Scanner;

/**
 *
 * @author charg
 */
public class Prueba {

    public static void main(String[] args) {
            Lexer lex = new Lexer(null, null);
            Scanner sc = new Scanner(System.in);
            String code = sc.nextLine();
            String parse = lex.compile(code);
            System.out.println(parse);
    }
}
