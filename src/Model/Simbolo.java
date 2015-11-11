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
public class Simbolo {

    private final boolean terminal;
    private final char variavel;

    public Simbolo(boolean terminal, char variavel) {
        this.terminal = terminal;
        this.variavel = variavel;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public char getVariavel() {
        return variavel;
    }
    
    public boolean isTerminal(List<Simbolo> simbolos){
        boolean b = true;
        for (Simbolo simbolo : simbolos) {
            if (!simbolo.terminal){
                b = false;
            }
        }
        return b;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Simbolo) obj).terminal == this.terminal && ((Simbolo) obj).variavel == this.variavel;
    }
}
