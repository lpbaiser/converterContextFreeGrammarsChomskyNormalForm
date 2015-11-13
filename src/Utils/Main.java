package Utils;

import Model.Linguagem;

/**
 *
 * @author Leonardo Baiser <lpbaiser@gmail.com>
 * @since
 */
public class Main {

    public static void main(String[] args) {
        Arquivo arquivo = new Arquivo();
        Linguagem linguagem;

        linguagem = arquivo.FileRead("src/Entrada/ex7.6useless-a.txt");

        System.out.println("Gramática Livre de Contexto");
        System.out.println("Variável Inicial: " + linguagem.getVariavelIncial().getVariavel());

        System.out.println("GLC -> CFN");
        System.out.println("Linguagem inicial");
        linguagem.imprimir();

        System.out.println("Remover Epsilon");
        linguagem.eliminarProducoesVazias();
        linguagem.imprimir();

        System.out.println("Remover Producoes Unitárias");
        linguagem.eliminarProducoesUnitarias();
        linguagem.imprimir();

        System.out.println("Remover Variáveis Inúteis");
        linguagem.eliminarVariaveisInuteis();
        linguagem.imprimir();
        System.exit(0);

        System.out.println("Remover Variáveis Inalcancáveis");
        linguagem.eliminarVariaveisInalcancaveis();
        linguagem.imprimir();
    }

}
