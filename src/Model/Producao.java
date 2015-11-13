/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
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

    /**
     * Realiza a indução através do parâmetro
     *
     * @param simbolosAnulaveis todos os símbolos anuláveis. Constata se o corpo é
     * anulável, e realiza a indução
     * @return
     */
    public boolean isAnulavel(List<Simbolo> simbolosAnulaveis) {
        if (simbolosAnulaveis == null || simbolosAnulaveis.isEmpty()) {
            return isAnulavel();
        }
        if (isAnulavel()) {
            return true;
        }
        for (Simbolo simbolo : corpo) {
            if (!simbolosAnulaveis.contains(simbolo)) {
                return false;
            }
        }
        return true;
    }

    public boolean isTerminal() {
        for (Simbolo simbolo : corpo) {
            if (!simbolo.isTerminal()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTerminal(List<Simbolo> simbolosTerminais) {
        if (simbolosTerminais == null || simbolosTerminais.isEmpty()) {
            return isTerminal();
        }
        if (isTerminal()) {
            return true;
        }
        for (Simbolo simbolo : corpo) {
            if (!(simbolo.isTerminal() || simbolosTerminais.contains(simbolo))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se é possível derivar palavras de uma produção
     *
     * @param variaveisUteis
     * @return true se, de todas as variáveis do corpo da produção forem
     * terminais ou úteis
     */
    public boolean isUtil(List<Simbolo> variaveisUteis) {
        for (Simbolo simbolo : corpo) {
            if (!(simbolo.isTerminal() || variaveisUteis.contains(simbolo))) {
                return false;
            }
        }
        return true;
    }

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

    
    public boolean contains(Simbolo s) {
        for (Simbolo simbolo : corpo) {
            if (simbolo.getVariavel() == s.getVariavel()) {

                return true;

            }
        }

        return false;
    }
   

    @Override
    public Object clone() {
        Producao producao = new Producao();
        producao.setCabeca((Simbolo) cabeca.clone());
        List<Simbolo> corpo = new ArrayList<>();
        for (Simbolo simbolo : this.corpo) {
            corpo.add((Simbolo) simbolo.clone());
        }
        producao.setCorpo(corpo);
        return producao;
    }

}
