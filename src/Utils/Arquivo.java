package Utils;

import Model.Linguagem;
import Model.Producao;
import Model.Simbolo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Leonardo Baiser <lpbaiser@gmail.com>
 * @since
 */
public class Arquivo {

    private Linguagem linguagem;
    private FileReader fileRead;
    private BufferedReader buffArquivo;

    public Linguagem FileRead(String path) {
        String linha;

        linguagem = new Linguagem();
        try {
            fileRead = new FileReader(path);
            buffArquivo = new BufferedReader(fileRead);

            linha = buffArquivo.readLine();
            linha = buffArquivo.readLine();

            String[] strVariaveis = linha.split(" ");
            Simbolo[] variaveis = new Simbolo[strVariaveis.length];
            for (int i = 0; i < strVariaveis.length; i++) {
                variaveis[i] = new Simbolo(false, strVariaveis[i].charAt(0));
            }
            linguagem.setVariaveis(variaveis);

            linha = buffArquivo.readLine();
            linguagem.setVariavelIncial(new Simbolo(false, linha.charAt(0)));

            linha = buffArquivo.readLine();

            Producao producao;
            List<Simbolo> corpo;
            List<Producao> producoes = new ArrayList<>();
            while (linha != null) {
                String[] p = linha.split(" ");
                producao = new Producao();
                producao.setSimbolo(new Simbolo(true, p[0].charAt(0)));

                corpo = new ArrayList<>();
                String string;
                for (int i = 0; i < p[1].length(); i++) {
                    string = p[1];
                    corpo.add(new Simbolo(linguagem.variaveisContains(string.charAt(i)), string.charAt(i)));
                }
                producao.setCorpo(corpo);
                producoes.add(producao);
                linha = buffArquivo.readLine();
            }
            linguagem.setProducoes(producoes);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return linguagem;
    }

}
