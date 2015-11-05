package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Leonardo Baiser <lpbaiser@gmail.com>
 * @since 
 */
public class Arquivo {
    
    private Linguagem linguagem;
    private FileReader fileRead;
    private BufferedReader buffArquivo;
    private HashMap<String, String> producoes = new HashMap<>();
    
    public Linguagem FileRead(String path){
        linguagem = new Linguagem();
        
        try {
            fileRead = new FileReader(path);
            buffArquivo = new BufferedReader(fileRead);
            String linha = buffArquivo.readLine();
            linha = buffArquivo.readLine();
            
            String[] variaveis = linha.split(" ");
            linguagem.setVariaveis(variaveis);
            linha = buffArquivo.readLine();
            linguagem.setVariavelIncial(linha);
            linha = buffArquivo.readLine();
//            linha.split(linha);
            

            while (linha != null) {
//                System.out.println("Linha: "+ linha);
                String[] p = linha.split(" ");
                producoes.put(p[0], p[1]);
                linguagem.incQtdeProducoes();
                
                linha = buffArquivo.readLine();
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        linguagem.setProducoes(producoes);
        
        return linguagem;
    }

}
