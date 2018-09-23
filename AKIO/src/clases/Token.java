/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author charg
 */
public class Token {
    
    String token, lexema, alias, aparicion;

    public Token(String token, String lexema, String alias, String aparicion) {
        this.token = token;
        this.lexema = lexema;
        this.alias = alias;
        this.aparicion = aparicion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAparicion() {
        return aparicion;
    }

    public void setAparicion(String aparicion) {
        this.aparicion = aparicion;
    }
    
}
