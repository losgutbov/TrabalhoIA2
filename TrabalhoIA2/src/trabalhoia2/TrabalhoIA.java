/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia2;

import java.io.IOException;

/**
 *
 * @author Augusto
 */
public class TrabalhoIA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        RegrasFuncionamento regra = new RegrasFuncionamento();
        regra.lerMatrizTerreno();
        regra.sortearTudo();
        regra.lerInformacoesPokemon();
        Interface inter = new Interface(730, 1370, regra); //Instancia objeto do tipo interface que carrega a interface gráfica do jogo, com os controles.
        AgentProlog  agente = new AgentProlog(inter);
        inter.geraInterface();
        agente.executarAgente();
        //System.out.println(regra.getListaPokemons().get(0).stringToProlog());
        
    }
}
