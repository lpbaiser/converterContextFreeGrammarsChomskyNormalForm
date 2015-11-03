
package Model;

import java.util.ArrayList;

/**
 *
 * @author leonardo
 */
public class Linguagem {
    
    private String[] alfabeto; //cada posição do array contém uma letra do alfabeto
    private String[] producoes; //cada posição do array contém uma produçao
    private int qtdeLtrAlfabeto = 0;
    private int qtdeProducoes = 0;

    public String[] getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(String[] alfabeto) {
        this.alfabeto = alfabeto;
    }

    public String[] getProducoes() {
        return producoes;
    }

    public void setProducoes(String producoes) {
        this.producoes[this.qtdeProducoes] = producoes;
    }

    public int getQtdeLtrAlfabeto() {
        return qtdeLtrAlfabeto;
    }

    public void setQtdeLtrAlfabeto(int qtdeLtrAlfabeto) {
        this.qtdeLtrAlfabeto = qtdeLtrAlfabeto;
    }

    public int getQtdeProducoes() {
        return qtdeProducoes;
    }

    public void setQtdeProducoes(int qtdeProducoes) {
        this.qtdeProducoes = qtdeProducoes;
    }
    
    public void incQtdeProducoes(){
        this.qtdeProducoes ++;
    }
    
    
}
