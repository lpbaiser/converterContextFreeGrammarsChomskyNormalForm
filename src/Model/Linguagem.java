package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author leonardo
 */
public class Linguagem {

    private List<Simbolo> variaveis; //cada posição do array contém uma letra do alfabeto
    private Simbolo variavelIncial;
    private List<Producao> producoes;
    private List<Simbolo> simbolosAnulaveis;
    private List<Simbolo> simbolosTerminais;
    private List<Producao> producoesUnitarias;
    private List<Producao> newProducoes;

    public List<Producao> getProducoes() {
        return producoes;
    }

    public void setProducoes(List<Producao> producoes) {
        this.producoes = producoes;
    }

    public List<Simbolo> getVariaveis() {
        return variaveis;
    }

    public void setVariaveis(List<Simbolo> variaveis) {
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
     * Varre todas as produções a procura de símbolos terminais
     */
    private void atualizarSimbolosTerminais() {
        for (Producao producao : producoes) {
            if (producao.isTerminal(simbolosTerminais)) {
                if (!simbolosTerminais.contains(producao.getCabeca())) {
                    simbolosTerminais.add(producao.getCabeca());
                }
            }
        }
    }

    public void atualizarProducoesUnitarias() {
        for (Producao producao : producoes) {
            if (producao.isUnitaria()) {
                if (!producoesUnitarias.contains(producao)) {
                    producoesUnitarias.add(producao);
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
     * Procura produções que apontam para simbolos não terminais, verifica se
     * este símbolo não terminal está no List<Simbolo>, se estiver faz a troca
     * do símbolo não terminal pelo símbolo terminal e ao fim das iterações
     * remove as produções unitárias.
     */
    public void eliminarProducoesUnitarias() {
        List<Producao> producoes;
        Producao clone;
        int tamanhoAnterior;
        do {
            producoesUnitarias = new ArrayList<>();
            System.gc();
            atualizarProducoesUnitarias();
            tamanhoAnterior = producoesUnitarias.size();

            for (Producao producaoUnitaria : producoesUnitarias) {//runtime modification exception
                producoes = getProducoes(producaoUnitaria.getCorpo().get(0));
                for (Producao producao : producoes) {
                    clone = (Producao) producao.clone();
                    clone.setCabeca((Simbolo) producaoUnitaria.getCabeca().clone());
                    if (!clone.getCabeca().equals(clone.getCorpo().get(0)) && !this.producoes.contains(clone)) {
                        this.producoes.add(clone);
                    }
                }
                this.producoes.remove(producaoUnitaria);
            }
        } while (tamanhoAnterior > 0);
    }

    /**
     * Preciso obter todas as produções de uma mesma cabeça para induzir?
     */
    public void eliminarVariaveisInuteis() {
        //surgem outras produções unitárias ao decorrer da etapa 2
        //é preciso estabilizar, para inferir
        simbolosTerminais = new ArrayList<>();
        int tamanhoAnterior;
        do {
            tamanhoAnterior = simbolosTerminais.size();
            atualizarSimbolosTerminais();
        } while (tamanhoAnterior < simbolosTerminais.size());

        for (int i = 0; i < producoes.size(); i++) {
            Producao producao = producoes.get(i);
            if (!producao.isUtil(simbolosTerminais)) {
                producoes.remove(producao);
                i--;
            }
        }
    }

    public void eliminarVariaveisInalcancaveis() {
        List<Producao> producoesAlcancaveis;
        List<Producao> producoes;

        producoesAlcancaveis = new ArrayList<>();
        producoes = getProducoes(variavelIncial);
        if (producoes != null) {
            producoesAlcancaveis.addAll(producoes);
        }
        for (int i = 0; i < producoesAlcancaveis.size(); i++) {
            Producao producao = producoesAlcancaveis.get(i);
            for (Simbolo simbolo : producao.getCorpo()) {
                producoes = getProducoes(simbolo);
                if (producoes != null) {
                    if (!producoesAlcancaveis.containsAll(producoes)) {
                        producoesAlcancaveis.addAll(producoes);
                    }
                }
            }
        }
        setProducoes(producoesAlcancaveis);
        System.gc();
    }

    public void colocarFormaNormalChomsky() {
        Producao p;
        Producao newProducao;
        Producao clone;
        newProducoes = new ArrayList<>();
        List<Simbolo> newSimbolos;
        Simbolo simboloRandom;
        /*
        Procura produções com simbolos terminais e cria novas produções 
        com os simboos terminais
        */
        for (Producao producao : producoes) {
            if (producao.getCorpo().size() >= 2) {
                simboloRandom = geraSimboloRandom();
                for (Simbolo simbolo : producao.getCorpo()) {
                    if (simbolo.isTerminal()) {
                        newProducao = new Producao();
                        newProducao.setCabeca(simboloRandom);
                        newSimbolos = new ArrayList<>();
                        newSimbolos.add(simbolo);
                        newProducao.setCorpo(newSimbolos);
                        if (!containsSimboloTerminal(simbolo)) {
                            newProducoes.add(newProducao);
                            this.variaveis.add(simboloRandom);
                        }
                        int index = producao.getCorpo().indexOf(simbolo);
                        producao.getCorpo().set(index, newProducao.getCabeca());
                    }
                }
            }
        }
        
        //adiciona as novas produções na lista de produção
        for (Producao producao : newProducoes) {
            producoes.add(producao);
        }

        /*verifica se o corpo da produção é maior que dois
          quebra este corpo produzindo produções com corpo de tamanho 2  
        */
        for (int i = 0; i < producoes.size(); i++) {
            p = producoes.get(i);
            
            newSimbolos = new ArrayList<>();
            List<Simbolo> simbolos = new ArrayList<>();
            int pos = p.getCorpo().size() / 2;
            simboloRandom = geraSimboloRandom();
            if (p.getCorpo().size() > 2) {
                newProducao = new Producao();
                clone = (Producao) p.clone();
                for (int j = 0; j <= pos; j++) {
                    if (j <= pos) {
                        newSimbolos.add(p.getCorpo().get(j));
                    }
                    
                }
                newProducao.setCabeca(simboloRandom);
                newProducao.setCorpo(newSimbolos);
                simbolos.add(newProducao.getCabeca());
                pos++;
                for (; pos < p.getCorpo().size() ; pos++) {
                    simbolos.add(p.getCorpo().get(pos));
                }
                p.setCorpo(simbolos);
                producoes.add(newProducao);
            }
        }
        System.gc();
    }

    public boolean containsSimboloTerminal(Simbolo s) {
        for (Producao producao : newProducoes) {
            if (producao.getCorpo().get(0).getVariavel() == (s.getVariavel())) {
                return true;
            }
        }
        return false;
    }

    public Simbolo geraSimboloRandom() {
        Simbolo s;
        Random gerador = new Random();
        char charSimbolo;
        do {
            int numero = gerador.nextInt(25) + 65;//gera número 64 - 90 / A - Z
            charSimbolo = (char) numero;
            s = new Simbolo(false, charSimbolo);
        } while (this.variaveis.contains(s));
        return s;
    }

    public int qtdeProducoes(Producao p) {
        int qtde = 0;
        for (Producao producao : producoes) {
            if (producao.getCabeca().equals(p.getCabeca())) {
                qtde++;
            }
        }
        return qtde;
    }

    private List<Producao> getProducoes(Simbolo simbolo) {
        if (simbolo.isTerminal()) {
            return null;
        }
        List<Producao> producoes = new ArrayList<>();
        for (Producao producao : this.producoes) {
            if (producao.getCabeca().equals(simbolo)) {
                producoes.add(producao);
            }
        }
        return producoes;
    }

    public void imprimir() {
        for (Producao producao : producoes) {
            System.out.print(producao.getCabeca().getVariavel() + " -> ");
            for (Simbolo simbolo : producao.getCorpo()) {
                System.out.print(simbolo.getVariavel());
            }
            System.out.println();
        }
        System.out.println();
    }
}
