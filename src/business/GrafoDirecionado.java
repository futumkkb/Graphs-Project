package business;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GrafoDirecionado extends GrafoMutavel {

    /**
     * Construtor. Cria um grafo vazio com um nome escolhido pelo usuário. Em caso
     * de nome não informado
     * (string vazia), recebe o nome genérico "Grafo"
     *
     * @param nome Nome do grafo
     */
    public GrafoDirecionado(String nome) {
        super(nome);
    }


    /**
     * Adiciona uma aresta entre dois vértices do grafo, caso os dois vértices
     * existam no grafo.
     * Caso a aresta já exista, ou algum dos vértices não existir, o comando é
     * ignorado e retorna FALSE.
     *
     * @param origem  Vértice de origem
     * @param destino Vértice de destino
     * @param peso    Peso da aresta
     * @return TRUE se foi inserida, FALSE caso contrário
     */
    @Override
    public boolean addAresta(int origem, int destino, int peso){
        boolean adicionou = false;
        Vertice saida = this.existeVertice(origem);
        Vertice chegada = this.existeVertice(destino);

        if (saida != null && chegada != null) {
            adicionou = (saida.addAresta(destino, peso) && chegada.addAresta(origem, peso));
        }

        return adicionou;
    }

    /**
     * Remove aresta do grafo
     * @param origem Índice do vértice de onde se origina a aresta a ser removida
     * @param destino Índice do vértice aonde aponta a aresta a ser removida
     * @return aresta removida
     */
    @Override
    public Aresta removeAresta(int origem, int destino) {
        return vertices.find(origem).getArestas().remove(destino);
    }

}
