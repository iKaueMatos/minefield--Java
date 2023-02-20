package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.CampoMinado.Model.Campo;

import static org.junit.jupiter.api.Assertions.*;

class CampoTesteTest {

    private Campo campo;


    @BeforeEach // BeforeEach -> significa que para cada um dos metodos vai executar essa função | Desta formainicializando o teste
    void iniciarCampo(){
        new Campo(3,3);
    }

    @Test
    void testeVizinhoDistancia1Embaixo() {
        Campo vizinho = new Campo(3, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

       @Test
       void testeVizinhoDistancia1() {
           Campo vizinho = new Campo(5, 2);
           boolean resultado = campo.adicionarVizinho(vizinho);
           assertTrue(resultado);

       }
        @Test
       void testeVizinhoNaoVizinho(){
        Campo vizinho = new Campo(1,1);
        boolean resultado =  campo.adicionarVizinho(vizinho);
       assertTrue(resultado);

    }
    @Test
    void testeVizinhoMarcacaoDuasChamadas(){
        campo.alterarMarcacao();
        campo.alterarMarcacao();
        assertFalse(campo.isCampoMarcado());
    }


}