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
    private char variavel;

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
    
    public void setVariavel(char variavel){
        this.variavel = variavel;
    }

    /**
     * Dado uma produção, verifica se a variável deriva apenas símbolos
     * terminais. Exemplo: cabeca.isTerminal(corpo)
     *
     * @param simbolos
     * @return
     */
    public boolean isTerminal(List<Simbolo> simbolos) {
        if (simbolos.size()>1){
            return false;
        }
        for (Simbolo simbolo : simbolos) {
            if (simbolo.isTerminal()) {
                return true;
            }
        }
        return false;
    }
    

    @Override
    public boolean equals(Object obj) {
        return ((Simbolo) obj).terminal == this.terminal && ((Simbolo) obj).variavel == this.variavel;
    }

    @Override
    protected Object clone() {
        return new Simbolo(terminal, variavel);
    }
}
