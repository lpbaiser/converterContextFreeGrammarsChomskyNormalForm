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
    private List<Simbolo> simbolosAnulaveis;
    private List<Simbolo> simbolosTerminais;

    public List<Producao> getProducoes() {
        return producoes;
    }

    public void setProducoes(List<Producao> producoes) {
        this.producoes = producoes;
    }

    public Simbolo[] getVariaveis() {
        return variaveis;
    }

    public void setVariaveis(Simbolo[] variaveis) {
        this.variaveis = variaveis;
    }

    public Simbolo getVariavelIncial() {
        return variavelIncial;
    }

    public void setVariavelIncial(Simbolo variavelInicial) {
        this.variavelIncial = variavelInicial;
    }

    public boolean variaveisContains(char simbolo) {
        for (Simbolo variavel : variaveis) {
            if (variavel.getVariavel() == simbolo) {
                return true;
            }
        }
        return false;
    }

    /**
     * Varre todas as produções a procura de símbolos anuláveis
     */
    private void atualizarSimbolosAnulaveis() {
        for (Producao producao : producoes) {
            if (producao.isAnulavel(simbolosAnulaveis)) {
                //contains utiliza a sobrescrita do método equals
                if (!simbolosAnulaveis.contains(producao.getCabeca())) {
                    simbolosAnulaveis.add(producao.getCabeca());
                }
            }
        }
    }

    /**
     * Procura os simbolos anuláveis até estabilizar Remove as produções vazias
     * Adiciona as produções que representam o epsilon
     */
    public void eliminarProducoesVazias() {
        //Encontrar todos os símbolos anuláveis
        simbolosAnulaveis = new ArrayList<>();
        int tamanhoAnterior;
        do {
            tamanhoAnterior = simbolosAnulaveis.size();
            atualizarSimbolosAnulaveis();
        } while (tamanhoAnterior < simbolosAnulaveis.size());

        //percorrer todas producoes
        //verificar se contém um simbolo anulável em seu corpo
        //caso contenha, clonar, remover o símbolo do clone e adicioná-lo à lista de produções
        //Para os unitários, ao removê-lo, eles não são adicionados, por ter corpo vazio
        //Para as produções cujo corpo tem mais de um símbolo anulável, este será naturalmente processado ao fim da lista
        Producao producao;
        Producao clone;
        for (int i = 0; i < producoes.size(); i++) {
            producao = producoes.get(i);
            if (producao.isAnulavel()) {
                //remove epsilon
                producoes.remove(producao);
                i--;
            } else {
                for (Simbolo simbolo : producao.getCorpo()) {
                    if (simbolosAnulaveis.contains(simbolo)) {
                        clone = (Producao) producao.clone();
                        clone.getCorpo().remove(simbolo);
                        if (!clone.getCorpo().isEmpty()) {
                            producoes.add(clone);
                        }
                    }
                }
            }
        }
    }

    /**
     * Varre todas as produções a procura de símbolos terminais
     */
    private void procuraSimbolosTerminais() {
        List<Simbolo> simbolos;
        for (Producao producao : producoes) {
            simbolos = producao.getCorpo();
            if (producao.getCabeca().isTerminal(simbolos)) {
                if (!simbolosTerminais.contains(producao.getCabeca())) {
                    simbolosTerminais.add(producao.getCabeca());
                }
            }
        }
    }

    public void eliminarProducoesUnitarias() {
        simbolosTerminais = new ArrayList<>();
        procuraSimbolosTerminais();

        for (Producao producao : producoes) {
            for (Simbolo variavel : variaveis) {
                if (producao.getCorpo().contains(variavel)) {
                    //trocar o simbolo terminal para o simbolo nao terminal
                }

            }
        }

    }

    /**
     * Preciso obter todas as produções de uma mesma cabeça para induzir?
     */
    public void eliminarVariaveisInuteis() {
        //surgem outras produções unitárias ao decorrer da etapa 2
        simbolosTerminais = new ArrayList<>();
        procuraSimbolosTerminais();

        //não é preciso estabilizar, uma vez que não existem mais produções unitárias
        for (Producao producao : producoes) {
            if (!producao.isUtil(simbolosTerminais)) {
                producoes.remove(producao);
            }
        }
    }

    public void eliminarVariaveisInalcancaveis() {
        List<Producao> producoesAlcancaveis;
        producoesAlcancaveis = new ArrayList<>();
        producoesAlcancaveis.addAll(getProducoes(variavelIncial));
        for (Producao producao : producoesAlcancaveis) {//runtime modification exception?
            for (Simbolo simbolo : producao.getCorpo()) {//loop infinito A -> aA
                producoesAlcancaveis.addAll(getProducoes(simbolo));
            }
        }
        setProducoes(producoesAlcancaveis);
        System.gc();
    }

    private List<Producao> getProducoes(Simbolo simbolo) {
        List<Producao> producoes = new ArrayList<>();
        for (Producao producao : this.producoes) {
            if (producao.getCabeca().equals(simbolo)) {
                producoes.add(producao);
            }
        }
        return producoes;
    }

    public void imprimir() {
        System.out.println();
        for (Producao producao : producoes) {
            System.out.print(producao.getCabeca().getVariavel() + " -> ");
            for (Simbolo simbolo : producao.getCorpo()) {
                System.out.print(simbolo.getVariavel());
            }
            System.out.println();
        }
    }

}
