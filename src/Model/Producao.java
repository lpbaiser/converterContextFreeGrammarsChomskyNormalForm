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

    private Simbolo cabeca;
    private List<Simbolo> corpo;

    public boolean isAnulavel() {
        return corpo.get(0).getVariavel() == '&';
    }

    public boolean isAnulavel(List<Simbolo> simbolos) {
        if (simbolos == null || simbolos.isEmpty()) {
            return isAnulavel();
        }
        if (isAnulavel()) {
            return true;
        }
        for (Simbolo simbolo : corpo) {
            if (!simbolos.contains(simbolo)) {
                return false;
            }
        }
        return true;
    }

//    public boolean isUnitary(Linguagem linguagem) {
//        for (int i = 0; i < linguagem.getVariaveis().length; i++) {
//            if ()
//        }
//        return corpo.contains(l.getVariaveis());
//    }
//
//    public boolean isUnitary(List<Simbolo> simbolos) {
//
//    }

    public Simbolo getCabeca() {
        return cabeca;
    }

    public List<Simbolo> getCorpo() {
        return corpo;
    }

    public void setCorpo(List<Simbolo> corpo) {
        this.corpo = corpo;
    }

    public void setCabeca(Simbolo cabeca) {
        this.cabeca = cabeca;
    }

}
