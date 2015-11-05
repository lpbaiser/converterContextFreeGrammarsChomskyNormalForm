/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;

/**
 *
 * @author romulo
 */
public class Producao {

    Simbolo simbolo;
    List<Simbolo> corpo;

    boolean isAnulavel() {
        return corpo.get(0).equals("&");
    }

    boolean isAnulavel(List<Simbolo> simbolos) {
        return corpo.get(0).equals("&");
    }

    Simbolo getSimbolo() {
        return simbolo;
    }

    public List<Simbolo> getCorpo() {
        return corpo;
    }

    public void setCorpo(List<Simbolo> corpo) {
        this.corpo = corpo;
    }

    public void setSimbolo(Simbolo simbolo) {
        this.simbolo = simbolo;
    }

}
