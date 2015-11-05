package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Leonardo Baiser <lpbaiser@gmail.com>
 * @since 
 */
public class Arquivo {
    
    private Linguagem linguagem;
    private FileReader fileRead;
    private BufferedReader buffArquivo;
    
    public Linguagem FileRead(String path){
        linguagem = new Linguagem();
        
        try {
            fileRead = new FileReader(path);
            buffArquivo = new BufferedReader(fileRead);
            String linha = buffArquivo.readLine();
            linha = buffArquivo.readLine();
            
            String[] alfabeto = linha.split(" ");
            linguagem.setAlfabeto(alfabeto);
            linha.split(linha);
            

            while (linha != null) {

                linguagem.setProducoes(linha);
                linguagem.incQtdeProducoes();
                
                linha = buffArquivo.readLine();
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return linguagem;
    }

}
