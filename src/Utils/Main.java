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
        
        linguagem = arquivo.FileRead("src/Entrada/ex7.6unit-a.txt");
        System.out.println("Variável Inicial: " + linguagem.getVariavelIncial().getVariavel());

        linguagem.imprimir();
        linguagem.eliminarProducoesUnitarias();
        linguagem.imprimir();
    }

}
