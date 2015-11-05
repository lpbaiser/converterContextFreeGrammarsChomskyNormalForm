package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leonardo
 */
public class Linguagem {

    private Simbolo[] variaveis; //cada posição do array contém uma letra do alfabeto
    private Simbolo variavelIncial;
    private List<Producao> producoes;

    public void setProducoes(List<Producao> producoes) {
        this.producoes = producoes;
    }

    public List<Producao> getProducoes() {
        return producoes;
    }

    public Simbolo[] getVariaveis() {
        return variaveis;
    }

    public void eliminarProducoesVazias() {

        //encontrar variáveis anuláveis
        //procurar epsilon
        List<Simbolo> simbolosAnulaveis = new ArrayList<>();
//        while (simbolosAnulaveis.size()) {
//            List<Simbolo> simbolosAnulaveis = new ArrayList<>();
        for (Producao producao : producoes) {
            if (producao.isAnulavel()) {
                simbolosAnulaveis.add(producao.getSimbolo());
//            producao.isAnulavel(simbolosAnulaveis);
            }

        }
    }

    public boolean variaveisContains(char simbolo) {
        for (Simbolo variavel : variaveis) {
            if (variavel.getVariavel() == simbolo) {
                return true;
            }
        }
        return false;
    }

    public Simbolo getVariavelIncial() {
        return variavelIncial;
    }

    public void setVariaveis(Simbolo[] variaveis) {
        this.variaveis = variaveis;
    }

    public void setVariavelIncial(Simbolo variavelInicial) {
        this.variavelIncial = variavelInicial;
    }
}
