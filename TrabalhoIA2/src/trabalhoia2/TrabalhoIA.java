/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia2;

/**
 *
 * @author Augusto
 */
public class TrabalhoIA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Interface inter = new Interface(730, 1370); //Instancia objeto do tipo interface que carrega a interface gráfica do jogo, com os controles.
        inter.geraInterface();
        
        RegrasFuncionamento regra = new RegrasFuncionamento();
        regra.zerarElementos();
        regra.sortearPokemon();
        regra.sortearCentro();
        regra.sortearLoja();
        regra.sortearTreinador();
        regra.varrerMatrizParaSensores();
        regra.imprimirMatriz();
        
    }
    
}
