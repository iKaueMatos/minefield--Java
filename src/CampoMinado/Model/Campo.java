package src.CampoMinado.Model;

import src.CampoMinado.Controller.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
//Math.abs
public class Campo {


    // Tipo primitivo -> boolean True e false
     private boolean minado; // Por padrao os atributos boolean ja estão sendo definidos como false;
    private boolean CampoAberto;
    private boolean CampoMarcado;
    private final int linha;
    private final int coluna;



    private List<Campo> vizinhos = new ArrayList<>();


    //Contrutor -> Basicamente aqui estamos especificando que queremos passar esses valores no nosso construtor
    //Linha e coluna

    public Campo(int linha1, int coluna1){
        this.linha = linha1; //this ->diferenciar o nome da nosso atributo
        this.coluna = coluna1;
    }

    public boolean adicionarVizinho(Campo vizinho){
        boolean linhaDiferente = this.linha != vizinho.linha;
        boolean colunaDiferente = this.linha != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        //Math.abs -> pegando o valor absolutp da linha e coluna
        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if(deltaGeral == 1 &&  !diagonal){
            vizinhos.add(vizinho);
            return true;
        }else if(deltaGeral == 2 && diagonal){
            vizinhos.add(vizinho);
            return  true;
        }else{
            return false;
        }
    }


    void alternarMarcacao(){ // void -> Função sem retorno !!
        if(!CampoAberto){
            CampoMarcado = !CampoMarcado; // se marcado for verdadeiro retorna falso e se marcado for falso retorna verdadeiro
        }
    }

    void setCampoAberto(boolean campoAberto) {
        CampoAberto = campoAberto;
    }

    boolean abrir(){ // por padão esse metodo ira retornar falso caso precisamos tentar abrir um campo
       if(!CampoAberto && !CampoMarcado){ // se caso o campo estiver aberto abre a possibilidade para executar o comando
           CampoAberto = true;

           if(minado) {
                throw  new ExplosaoException(); // ira quebrar o fluxo que esta perto de ser executado na nossa aplicação,desta forma
               // ira exibir uma excessão;
               }
           if (vizinhacaSegura()){
               vizinhos.forEach(v -> v.abrir());
            }
       }
       return false;
    }

    boolean vizinhacaSegura(){
        return  vizinhos.stream().noneMatch(v -> v.minado); // nenhum vizinho pode dar selecionar o campo,caso isso aconteça o campo não e seguro
    }

    void minar(){
        minado = true;
    }
public boolean isMinado(){
        return minado;
}

    public  boolean isCampoMarcado(){
            return CampoMarcado;
    }

    public boolean isCampoAberto(){
        return CampoAberto;
    }

    public boolean isFechado(){
        return !isCampoAberto();
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado(){

        boolean desvendado = !minado && CampoAberto;
        boolean protegido = minado && CampoMarcado;
        return desvendado || protegido;

    }

    long minasNaVizinhaca(){
        return vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar(){
        CampoAberto = false;
        minado = false;
        CampoMarcado = false;
    }


    // metodo Exibindo
    public String toString(){
        if(CampoMarcado){
            return "x";
        }else if(CampoAberto && minado){
            return "*";
        }else if(CampoAberto && minasNaVizinhaca()> 0){
            return Long.toString(minasNaVizinhaca());
        }else if(CampoAberto){
            return " ";
        }else{
            return "?";
        }
    }

    public void alterarMarcacao() {
    }
}
