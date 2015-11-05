package Utils;

import Model.Linguagem;

/**
 *
 * @author Leonardo Baiser <lpbaiser@gmail.com>
 * @since
 */
public class Main {

    public static void main(String[] args) {
        Arquivo a = new Arquivo();
        Linguagem l = new Linguagem();
        l = a.FileRead("src/Entrada/ex7.6lambda-a.txt");
        System.out.println("Ling; " + l.getVariavelIncial().getVariavel());
        l.eliminarProducoesVazias();

    }

}
