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
    private List<Producao> producoesSimbolosTerminais;
    private List<Producao> listaProducoes;

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
                return false;//por exemplo D not is terminal
            }
        }
        return true;
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
    private void atualizarSimbolosTerminais() {
        for (Producao producao : producoes) {
            if (producao.isTerminal(simbolosTerminais)) {
                if (!simbolosTerminais.contains(producao.getCabeca())) {
                    simbolosTerminais.add(producao.getCabeca());
                }
            }
        }
    }

    /**
     * Varre todas as produções a procura de producoes que contenha símbolos
     * terminais
     */
    private void procurarProducoesTerminais() {
        List<Simbolo> simbolos;
        for (Producao producao : producoes) {
            for (Simbolo simbolo : simbolosTerminais) {
                if (producao.contains(simbolo) && producao.getCabeca().getVariavel() != simbolo.getVariavel()) {
                    producoesSimbolosTerminais.add(producao);
                }
            }
//            simbolos = producao.getCorpo();
//            if (producao.getCabeca().isTerminal(simbolos)) {
//                if (!producoesSimbolosTerminais.contains(producao)) {
//                    producoesSimbolosTerminais.add(producao);
//                }
//            }
        }
    }

    public void buscaProducaoSimbolo(Simbolo s) {

        for (Producao producao : producoes) {
            if (producao.getCabeca().equals(s)) {
                listaProducoes.add(producao);
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
        simbolosTerminais = new ArrayList<>();
        atualizarSimbolosTerminais();
        producoesSimbolosTerminais = new ArrayList<>();
        atualizarSimbolosTerminais();
        listaProducoes = new ArrayList<>();

//        System.out.println("Simbolos terminais");
//        for (Simbolo simbolo : simbolosTerminais) {
//            System.out.println(simbolo.getVariavel());
//            procurarProducoesTerminais();
//            List<Simbolo> clone;
//            for (Producao producao : producoes) {
//                for (Producao producao1 : producoesSimbolosTerminais) {
//                    if (producao.getCorpo().contains(producao1.getCabeca())) {
//                        //trocar o simbolo terminal para o simbolo nao terminal
//                        clone = producao.getCorpo();
//                        for (Simbolo c : clone) {
////                        if (c.getVariavel())
//
//                        }
//                    }
//
//                }
//                System.out.println("Producoes com simbolos terminais");
//                for (Producao producao : producoesSimbolosTerminais) {
//                    System.out.print(producao.getCabeca().getVariavel() + " -> ");
//                    for (Simbolo simbolo : producao.getCorpo()) {
//                        System.out.print(simbolo.getVariavel());
//                    }
//                    System.out.println();
//                }
        for (Simbolo simbolo : simbolosTerminais) {
            for (Producao producao : producoesSimbolosTerminais) {
                if (producao.contains(simbolo)) {
                    //contém simbolo não terminal que produz um simbolo terminal
                    //buscar todas a produçoes do simbolo
                    buscaProducaoSimbolo(simbolo);
                    //adicionar a lsita de producoes
                }

            }

        }

//        System.out.println();
//        for (Producao producao : novasProducoes) {
//            System.out.print(producao.getCabeca().getVariavel() + " -> ");
//            for (Simbolo simbolo : producao.getCorpo()) {
//                System.out.print(simbolo.getVariavel());
//            }
//            System.out.println();
//        }
//            for (Producao producao1 : producoesSimbolosTerminais) {
//                if (producao.contains(producao1.getCabeca())) {
//                    clone = producao.getCorpo();
//                    if (qtdeProducoes(producao) == 1 || clone.size() == 1) {
//                        for (Simbolo c : clone) {
//                            if (producao1.getCabeca().getVariavel() == c.getVariavel()) {
//                                c.setVariavel(producao1.getCorpo().get(0).getVariavel());
//                            }
//                        }
//                        producao.setCorpo(clone);
//                    }
////                    else {
////                        Producao p = new Producao();
////                        for (Simbolo c : clone) {
////                            if (producao1.getCabeca().getVariavel() == c.getVariavel()) {
////                                c.setVariavel(producao1.getCorpo().get(0).getVariavel());
////                                p.setCabeca(producao.getCabeca());
////                                p.setCorpo(clone);
////                            }
////                            cloneP.add(p);
////                        }
////                    }
//                }
//            }
//
//        }
//       
        producoes.removeAll(producoesSimbolosTerminais);
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

        //não é preciso estabilizar, uma vez que não existem mais produções unitárias
//            if (producao.getCorpo().containsAny(this.simbolosTerminais)) {
//                variaveisUteis.add(producao.getCabeca());
//            }
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
