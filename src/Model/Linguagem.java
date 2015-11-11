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
    private List<Producao> simbolosTerminais;

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

    private void atualizarSimbolosAnulaveis() {
        for (Producao producao : producoes) {
            if (producao.isAnulavel(simbolosAnulaveis)) {
                //contains utiliza a sobrescrita do método equals?
                //http://www.matera.com/br/2015/01/15/desvendando-os-metodos-equals-e-hascode/
                //aparentemente sim
                if (!simbolosAnulaveis.contains(producao.getCabeca())) {
                    simbolosAnulaveis.add(producao.getCabeca());
                }
            }
        }
    }

    //procurar epsilon
    //encontrar variáveis anuláveis
    public void eliminarProducoesVazias() {
        simbolosAnulaveis = new ArrayList<>();
        int tamanhoAnterior;
        do {
            tamanhoAnterior = simbolosAnulaveis.size();
            atualizarSimbolosAnulaveis();
        } while (tamanhoAnterior < simbolosAnulaveis.size());

        //percorrer todas producoes
        //verificar se contém um simbolo anulável em seu corpo
        //caso contenha, verificar quantos
        //obter quantos simbolos são nulos
        //se apenas um, removê-lo localmente e criar uma produção sem ele
        Producao producao;
        for (int i = 0; i < producoes.size(); i++) {
            producao = producoes.get(i);
            if (producao.isAnulavel()) {
                //remove epsilon
                producoes.remove(producao);
            } else {
                for (Simbolo simbolo : producao.getCorpo()) {
                    simbolosAnulaveis.contains(simbolo);
                     //remover simbolo
                    //criar cópia da producao
                }
            }
        }
    }

    public void procuraSimbolosUnitarios() {
        List<Simbolo> simbolos;
        for (Producao producao : producoes) {
            simbolos = producao.getCorpo();
            for (Simbolo simbolo : simbolos) {
                if (simbolo.isTerminal() && !simbolosTerminais.contains(producao)) {
                    simbolosTerminais.add(producao);
                }
            }
        }
    }

    public void eliminarProducoesUnitarias() {
        simbolosTerminais = new ArrayList<>();
        procuraSimbolosUnitarios();

    }

}
