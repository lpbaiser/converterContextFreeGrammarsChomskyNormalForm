package Utils;

import Model.Linguagem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Leonardo Baiser <lpbaiser@gmail.com>
 * @since 16/11/2015
 */
public class Main {

    public static void main(String[] args) {
        Arquivo arquivo = new Arquivo();
        Linguagem linguagem;

        List<String> arqExemplos =  new ArrayList<String>();
        arqExemplos.add("ex7.6lambda-a.txt");
        arqExemplos.add("ex7.6lambda-e.txt");
        arqExemplos.add("ex7.6unit-a.txt");
        arqExemplos.add("ex7.6unit-e.txt");
        arqExemplos.add("ex7.6useless-a.txt");
        arqExemplos.add("ex7.6useless-e.txt");
        arqExemplos.add("ex7.6cnf-a.txt");
        arqExemplos.add("ex7.6cnf-e1.txt");
        
        int i = 0;
        System.out.println("Conversor de gramáticas livre de contexto \n "
                + "para gramática na forma normal de Chomsky");
        do {

            System.out.println("Digite o número da opção desejada: ");
            System.out.println("1 - Rodar o conversor com exemplos \n"
                    + "2 - Digitar o caminho do arquivo que deseja converter \n"
                    + "3 - Sair");
            Scanner scanner = new Scanner(System.in);
            i = scanner.nextInt();
            if (i == 1) {
                for (String arqExemplo : arqExemplos) {
                    linguagem = arquivo.FileRead("src/Entrada/"+arqExemplo);
                    System.out.println("GLC -> CFN");
                    System.out.println("Linguagem inicial");
                    linguagem.imprimir();
                    System.out.println("Linguagem convertida");
                    linguagem.eliminarProducoesVazias();
                    linguagem.eliminarProducoesUnitarias();
                    linguagem.eliminarVariaveisInuteis();
                    linguagem.eliminarVariaveisInalcancaveis();
                    linguagem.colocarFormaNormalChomsky();
                    linguagem.imprimir();
                }
            } else if (i == 2) {
                System.out.println("Digite o caminho do arquivo: ");
                String caminho = scanner.next();
                try {
                    linguagem = arquivo.FileRead(Main.class.getResource(caminho).getFile());
                    System.out.println("GLC -> CFN");
                    System.out.println("Linguagem inicial");
                    linguagem.imprimir();
                    System.out.println("Linguagem convertida");
                    linguagem.eliminarProducoesVazias();
                    linguagem.eliminarProducoesUnitarias();
                    linguagem.eliminarVariaveisInuteis();
                    linguagem.eliminarVariaveisInalcancaveis();
                    linguagem.colocarFormaNormalChomsky();
                    linguagem.imprimir();

                } catch (Exception ex) {
                    System.out.println("Erro - Arquivo não encontrado!");
                }
            }

        } while (i != 3);

    }

}
