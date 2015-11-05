
package Model;

import java.util.HashMap;

/**
 *
 * @author leonardo
 */
public class Linguagem {
    
    private String[] variaveis; //cada posição do array contém uma letra do alfabeto
    private String variavelIncial;
    private HashMap<String, String> producoes = new HashMap<>();
    private int qtdeLtrAlfabeto = 0;
    private int qtdeProducoes = 0;

    public String[] getVariaveis() {
        return variaveis;
    }

    public void setVariaveis(String[] alfabeto) {
        this.variaveis = alfabeto;
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

    public String getVariavelIncial() {
        return variavelIncial;
    }

    public void setVariavelIncial(String variavelIncial) {
        this.variavelIncial = variavelIncial;
    }

    public HashMap<String, String> getProducoes() {
        return producoes;
    }

    public void setProducoes(HashMap<String, String> producoes) {
        this.producoes = producoes;
    }
    
    
    
    
    
    
}
