package src.CampoMinado.Model;

import src.CampoMinado.Controller.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    //construtor
    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        //inicializnado o objeto para começar o jogo

        gerarCampo(); // ira gerar um campo para ter nossas minas e nosso numeros aleatorios;
        associarVizinhos();
        sortearMinas(); // Esse método tem como objetivo fazer com que inicialize o jogo cada vez que ele for recarregado ...entao ira
        // sortear novas posições de minas;

    }


    public void abrir(int linha,int coluna){
        try{
            campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst().
                    ifPresent(c -> c.abrir());
            //findFirst -> abre a possibilidade de retorno de option de List<campo>.
        }catch(ExplosaoException e){
            campos.forEach(c -> c.setCampoAberto(true));
            throw  e;
        }
    }


    public void alternarMarcacao(int linha,int coluna){
        campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst().
                ifPresent(c -> c.alternarMarcacao());
        //findFirst -> abre a possibilidade de retorno de option de List<campo>.
    }




    //Gerar o campo do Mini-game
    private void gerarCampo() {
        for(int linha =0; linha < linhas; linha++){
            for(int coluna = 0; coluna < colunas; coluna++){
                campos.add(new Campo(linha,coluna));
            }
        }
    }

    private void associarVizinhos() {
        for (Campo c1: campos){
            for(Campo c2: campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    //Sempre que precisarmos reniciar o jogo esse metodo sera reiniciado novamente
    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo>  minado = c -> c.isMinado();
        //Do while ira ficar executando enquanto a quantidade de minas for menor que quantidade de minas
        // que tem no campo
        do {
            //Se a quantidade de minas armadas for igual a quantidade de minas ira sair do laço logo em sequencia
            int aleatorio = (int) ((Math.random() *  campos.size()));
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();


        }while(minasArmadas < minas);
    }

    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }


    //Método para reinicializar o jogo
    public void reiniciar(){
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder(); // Sempre quando tivermos uma quantidade grande de String para serem rederizadas
        //Utilizamos o StringBuilder que possibilita rederizarmos uma quantidade grande de strings

        sb.append("  ");
        for(int c = 0; c < colunas; c++ ){
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }

        sb.append("\n");



       int i =0;
        for(int l=0; l< linhas;l++){
            sb.append(l);
            sb.append(" ");
           for( int c =0 ;c <colunas;c++){
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
           }
            sb.append("\n");
       }
        return sb.toString();
    }
}
