package trabalhoia2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Augusto
 */
public class RegrasFuncionamento {
    private int[][] matrizTerreno = new int[42][42];//matriz que receberá os valores referentes à localização dos terrenos
    private int[][] matrizElementos = new int[42][42];//posições de pokemons, centro e lojas.
    private int[] posicaoAtual = new int[2];
    //private int[] codPokemon = new int[150];
    private int pokebolas = 25;
    private int sentido;
    private int pontuacao;
    private int identificadorPokemon;
    private String nomePokemon,descricaoPokemon, tipoPokemon; 
    private ArrayList<Pokemon> listaPokemons = new ArrayList<Pokemon>();
    private Pokemon pokemon = new Pokemon();

// FINAL GARANTE QUE OS VALORES NÃO SERÃO MODIFICADOS. 
    final int ZERO = 0;
    final int CENTRO = 152;
    final int LOJA = 153;
    final int TREINADOR = 154;

    final int PERFUME = 155;    
    final int PROPAGANDA_BOLAS = 156;
    final int DESAFIO = 157;
    
    final int AGENTE = 200;
    
    final int CIMA = 0;
    final int DIREITA = 1;
    final int BAIXO = 2;
    final int ESQUERDA = 3;
    
    final int MVFRENTE = -1;
    final int MVDIREITA = -1;
    final int MVESQUERDA = -1;
    final int USAPOKEBOLA = -5;
    final int PEGAPOKEBOLA = -10;
    final int RECPOKEMON = -100;
    final int GANHARBATALHA = 150;
    final int PERDERBATALHA = -1000;    
     
    //-------------------------MÉTODOS GET E SET---------------------------------//
    public RegrasFuncionamento() {
    }

    public int[][] getMatrizTerreno() {
        return matrizTerreno;
    }

    public void setMatrizTerreno(int[][] matrizTerreno) {
        this.matrizTerreno = matrizTerreno;
    }

    public int[][] getMatrizElementos() {
        return matrizElementos;
    }

    private void setMatrizElementos(int[][] matrizElementos) {
        this.matrizElementos = matrizElementos;
    }

    public int[] getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(int[] posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }
    
    public void setPosicaoAtual(int posicaoI, int posicaoJ) {
        this.posicaoAtual[0] = posicaoI;
        this.posicaoAtual[1] = posicaoJ;
    }

    public int getSentido() {
        return sentido;
    }

    public void setSentido(int sentido) {
        this.sentido = sentido;
        if(this.sentido<0){this.sentido =ESQUERDA;}
        if(this.sentido>3){this.sentido =CIMA;}
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
       
    public ArrayList<Pokemon> getListaPokemons() {
        return listaPokemons;
    }

    public void setLista(ArrayList<Pokemon> listaPokemons) {
        this.listaPokemons = listaPokemons;
    }
    
    public void adicionarCustoPontuacao(int acao){
        this.setPontuacao(this.getPontuacao()+acao);
    }
    //-------------------------FIM MÉTODOS GET E SET---------------------------------//
    
    public int[] determinarMovimento(){
        int[] coordenadasNovas = new int[2];
        coordenadasNovas[0]=this.getPosicaoAtual()[0];
        coordenadasNovas[1]=this.getPosicaoAtual()[1];
        switch(this.getSentido()){
            case CIMA:
                if((--(coordenadasNovas[0]))<0){
                    coordenadasNovas[0]=-1;
                    coordenadasNovas[1]=-1;
                }                
                break;
            case DIREITA:
                if((++(coordenadasNovas[1]))>41){
                    coordenadasNovas[0]=-1;
                    coordenadasNovas[1]=-1;
                }                
                break;
            case BAIXO:
                if((++coordenadasNovas[0])>41){
                    coordenadasNovas[0]=-1;
                    coordenadasNovas[1]=-1;
                }
                break;
            case ESQUERDA:
                if((--(coordenadasNovas[1]))<0){
                    coordenadasNovas[0]=-1;
                    coordenadasNovas[1]=-1;
                }                
                break;
        }
        return coordenadasNovas;
    }
    
    //MÉTODO QUE ZERA TODAS AS POSIÇÕES DA MATRIZ. DETERMINA SE É POSSIVEL COLOCAR ALGUM ELEMENTO NA POSIÇÃO CASO SEJA ZERO. 
    public void zerarElementos(){
        for(int i = 0; i < matrizElementos.length; i++){
            for(int j=0; j < matrizElementos.length; j++){
                this.matrizElementos[i][j] = ZERO; 
            }     
        }          
    }
    
    //MÉTODO DE IMPRESSÃO DA MATRIZ
    public void imprimirMatriz(){ 
        for(int i = 0; i < matrizElementos.length; i++){
            for(int j=0; j < matrizElementos.length; j++){
                System.out.print(matrizElementos[i][j]+" "); 
            }  
            System.out.println(" ");
         }          
    }
    
    //MÉTODO DE INICIALIZAÇÃO DO AGENTE COM O SEU SENTIDO. 
    private void iniciarAgente(){
        //19 linhas 24 colunas
        this.matrizElementos[19][24]=AGENTE;
        this.setPosicaoAtual(19, 24);
        this.setSentido(BAIXO);
    }
    
    //MÉTODO QUE SORTEIA OS POKEMONS NO MAPA
    private void sortearPokemon(){
       Random gerador = new Random();
       int rand = 0;
       ArrayList<Integer> pokemonsJaIntroduzidos = new ArrayList<>();
       
       for(int x = 0; x<150; x++){
           int i = gerador.nextInt(matrizElementos.length);
           int j = gerador.nextInt(matrizElementos.length);
           if(matrizElementos[i][j]==0){
               rand = 0;
               while(rand==0){
                   rand = 1 + gerador.nextInt(150);
                   if(pokemonsJaIntroduzidos.contains(rand)){
                       rand=0;
                   }else{pokemonsJaIntroduzidos.add(rand);}
               }
               matrizElementos[i][j] = rand;
           }else{x--;}
       }
   }

    //MÉTODO QUE SORTEIA OS CENTROS POKEMONS DENTRO DO MAPA
    private void sortearCentro(){   
        Random gerador = new Random();
        
         for(int x = 0; x<20; x++){
           int i = gerador.nextInt(matrizElementos.length);
           int j = gerador.nextInt(matrizElementos.length);
           if(matrizElementos[i][j]==0){
               matrizElementos[i][j] = CENTRO;
           }else{x--;}
       }          
    }
    
    //MÉTODO QUE SORTEIA AS LOJAS DENTRO DO MAPA
    private void sortearLoja(){   
        Random gerador = new Random();

         for(int x = 0; x<15; x++){
           int i = gerador.nextInt(matrizElementos.length);
           int j = gerador.nextInt(matrizElementos.length);
           if(matrizElementos[i][j]==0){
               matrizElementos[i][j] = LOJA;
           }else{x--;}
       }          
    }
    
    //MÉTODO QUE SORTEIA OS TREINADORES DENTRO DO MAPA
    private void sortearTreinador(){   
        Random gerador = new Random();

         for(int x = 0; x<50; x++){
           int i = gerador.nextInt(matrizElementos.length);
           int j = gerador.nextInt(matrizElementos.length);
           if(matrizElementos[i][j]==0){
               matrizElementos[i][j] = TREINADOR;
           }else{x--;}
       }          
    }

    //MÉTODO QUE ADICIONA OS ESTIMULOS 
    private void adicionarEstimulo(int estimulo, int posiI, int posiJ){
        if((posiI!=0)&&(matrizElementos[posiI-1][posiJ]==0)){
            matrizElementos[posiI-1][posiJ] = estimulo;
        }
        if((posiI!=41)&&(matrizElementos[posiI+1][posiJ]==0)){
            matrizElementos[posiI+1][posiJ] = estimulo;
        }
        if((posiJ!=0)&&(matrizElementos[posiI][posiJ-1]==0)){
            matrizElementos[posiI][posiJ-1] = estimulo;
        }
        if((posiJ!=41)&&(matrizElementos[posiI][posiJ+1]==0)){
            matrizElementos[posiI][posiJ+1] = estimulo;
        }
    }
    
    //MÉTODO QUE VARRE A MATRIZ DE SENSORES E CHAMA O MEDODO PARA ADICONAR O ESTIMULO DE ACORDO COM O SENSOR.
    private void varrerMatrizParaSensores(){
        for(int i = 0; i<matrizElementos.length; i++){
            for(int j=0; j<matrizElementos.length; j++){
                switch(matrizElementos[i][j]){
                    case CENTRO:
                        adicionarEstimulo(PERFUME, i, j);
                        break;
                    case LOJA:
                        adicionarEstimulo(PROPAGANDA_BOLAS, i, j);
                        break;
                    case TREINADOR:
                        adicionarEstimulo(DESAFIO, i, j);
                        break;
                    default:
                        break;
                }
            }
        }        
    }
    
    //MÉTODO UTILIZADO PARA CHAMAR TODOS OS ELMENTOS DO MAPA
    public void sortearTudo(){
        this.zerarElementos();
        this.iniciarAgente();
        this.sortearPokemon();
        this.sortearCentro();
        this.sortearLoja();
        this.sortearTreinador();
        this.varrerMatrizParaSensores();
    }

    //MÉTODO PARA LER ARQUIVO TXT PARA PREENCHER A MATRIZ COM OS TERRENOS ESPECÍFICOS
    public void lerMatrizTerreno() throws FileNotFoundException, IOException{
       FileReader txtMatriz = new FileReader("C:\\Users\\Thamires\\Documents\\NetBeansProjects\\TrabalhoIA2\\TrabalhoIA2\\src\\trabalhoia2\\arquivos\\matrizTerrenos.txt");
        //FileReader txtMatriz = new FileReader(getClass().getResource("arquivos/matrizTerrenos.txt").toString());
        Scanner lerTxt =  new Scanner(txtMatriz).useDelimiter("\n");
        int cont=0, i=0;
        try{                        
            while(lerTxt.hasNext()){
                String[] a1 = lerTxt.next().split(" ");
                for(int j=0; j<42; j++){
                    this.matrizTerreno[i][j] = Integer.parseInt(a1[j]);
                }
                i++;
            }                           
        }catch(Exception IOException){
            System.err.printf("Erro na abertura do arquivo: %s.\n",IOException.getMessage());
        }
    }
    
    //MÉTODO PARA LER ARQUIVO TXT QUE ADICIONA POKEMONS A LISTA DE POKEMONS COM SUAS RESPECITIVAS INFORMAÇÕES
    public void lerInformacoesPokemon()throws FileNotFoundException, IOException{
        Scanner scanner = new Scanner(new FileReader("C:\\Users\\Thamires\\Documents\\NetBeansProjects\\TrabalhoIA2\\TrabalhoIA2\\src\\trabalhoia2\\arquivos\\pokemon.txt")).useDelimiter("\\||\\n");       
        while (scanner.hasNext()) {     
            this.identificadorPokemon = scanner.nextInt();
            this.nomePokemon = scanner.next();
            this.descricaoPokemon = scanner.next();
            this.tipoPokemon = scanner.next();
            
            pokemon.setIdentificador(this.identificadorPokemon);
            pokemon.setNome(this.nomePokemon);
            pokemon.setDescricao(this.descricaoPokemon);
            pokemon.setTipo(this.tipoPokemon);
            String a1[] = this.tipoPokemon.split(",");
            pokemon.setTipos(a1);
            listaPokemons.add(pokemon);
            pokemon = new Pokemon();
        }
        scanner.close();
    }
    
    //MÉTODO PARA IMPRIMIR LISTA DE POKEMONS COM SUAS INFORMAÇÕES
    public void imprimirListaPokemons(){
        for (int i = 0; i < listaPokemons.size(); i++) {
            System.out.println(listaPokemons.get(i).toString());
        }
    }
    
    //MÉTODO QUE PESQUISA UM POKEMON DA LISTA DE POKEMONS. 
    public void pesquisarElementoListaPokemons(int identPokemonCapturado){
        for(int i=0; i < listaPokemons.size(); i++){
            if(listaPokemons.get(i).getIdentificador()== identPokemonCapturado){
                System.out.println(listaPokemons.get(i).toString());
            }
        }
    }
    
    //MÉTODO QUE RETORNA A INFORMAÇÃO DO POKEMON PARA O PROLOG
    public String pokemonsParaProlog(int identPokemonCapturado){
        return listaPokemons.get(identPokemonCapturado).stringToProlog(); 
    }
    
    //MÉTODO PARA EXIBIR AS INFORMAÇÕES DO POKEMON ATRAVÉS DA POKEDEX
    public void pokedexInformacao (int identPokemon){
        for(int i=0; i < listaPokemons.size(); i++){
            if(listaPokemons.get(i).getIdentificador()== identPokemon){
                System.out.println(listaPokemons.get(i).toString());
            }
        }
    }
    
}