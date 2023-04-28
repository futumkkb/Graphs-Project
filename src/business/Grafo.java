package business;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** 
 * MIT License
 *
 * Copyright(c) 2021-23 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * Classe básica para um Grafo simples não direcionado.
 */
public abstract class Grafo {
    public final String nome;
    protected ABB<Vertice> vertices;

    /**
     * Construtor. Cria um grafo vazio com um nome escolhido pelo usuário. Em caso
     * de nome não informado
     * (string vazia), recebe o nome genérico "Grafo"
     */
    public Grafo(String nome) {
        if (nome.length() == 0)
            this.nome = "Grafo";
        else
            this.nome = nome;
        this.vertices = new ABB<>();
    }

    public static Grafo grafoCompleto(int ordem) {

        GrafoCompleto g = new GrafoCompleto(ordem, "GrafoCompleto");

        return g;
    }

    /**
     * Retorna o nome do grafo (string), caso seja necessário em outras
     * classes/sistemas
     * 
     * @return O nome do grafo (uma string)
     */
    public String nome() {
        return this.nome;
    }

    public void salvar(String nomeArquivo) {
        File file = new File(nomeArquivo);

        try {

            FileWriter fw = new FileWriter(file);

            Vertice[] abbVertices = vertices.allElements(new Vertice[] {});

            for (Vertice vertice : abbVertices) {

                Aresta[] allArestas = vertice.todasAsArestas();

                for (Aresta aresta : allArestas) {

                    Vertice destino = new Vertice(aresta.destino());

                    fw.write(vertice.getId() + " " + destino.getId() + " " + aresta.peso());
                    
                }
            }

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param idVertice O indentificado do vértice procurado
     * @return Vértice procurado ou null caso não exista
     */
    public Vertice existeVertice(int idVertice) {
        return vertices.find(idVertice);
    }

    /**
     * Verifica se existe uma aresta entre A e B
     * 
     * @param verticeA Vertice de partida
     * @param verticeB Vertice de chegada
     * @return A aresta ente A e B ou null caso não exista
     */
    public Aresta existeAresta(int verticeA, int verticeB) {
        Vertice verticePartida = vertices.find(verticeA);
        return verticePartida.existeAresta(verticeB);
    }

    /**
     * Verifica se um grafo é completo, supondo que este seja conexo
     * @return Resultado da comparação entre o somatório de arestas com a expressão (n*(n-1))/2
     */
    public boolean completo() {
        Vertice[] todosVertices = new Vertice[vertices.size()]; 
        todosVertices = vertices.allElements(todosVertices); 
        int n = ordem();
        int total = 0;
        
        for (Vertice vertice : todosVertices) {
            total += vertice.grau();
        }

        return (total == (n*(n-1))/2);
    }

    /**
     * Gera um subgrafo a partir das indicações dos vértices do grafo original
     * 
     * @param verticesSubgrafo Lista de vértices do grafo original
     * @return Um subrgrafo com os vértices da lista
     */
    public Grafo subGrafo(Lista<Integer> verticesSubgrafo) {
        Vertice[] arrayVertice = new Vertice[vertices.size()];
        Vertice[] verticesDoGrafo = vertices.allElements(arrayVertice);

        Integer[] arrayVerticesSubgrafo = new Integer[verticesSubgrafo.size()];
        Integer[] verticesDoSubgrafo = verticesSubgrafo.allElements(arrayVerticesSubgrafo);

        boolean estaNaLista = false;
        int id = 0;

        GrafoNaoDirecionado subgrafo = new GrafoNaoDirecionado("Subgrafo de " + this.nome);
        
        for (int i = 0; i < verticesDoGrafo.length; i++) {
            subgrafo.vertices.add(i + 1, new Vertice(verticesDoGrafo[i].getId()));
        }

        for (int i = 1; i <= subgrafo.vertices.size(); i++) {
            for (int j = 0; j < verticesDoSubgrafo.length; j++) {
                if (i == verticesDoSubgrafo[j]) {
                    estaNaLista = true;
                    break;
                }
                id = i;
            }
            if (!estaNaLista) {
                subgrafo.removeVertice(id);
            }
            estaNaLista = false;
        }
        return subgrafo;
    }

    /**
     * Calcula e retorna a quantidade total de arestas presentes no grafo
     */
    public int tamanho() {
        int totalArestas = 0;

        for (int i = 0; i < vertices.size(); i ++){
            Vertice[] todosVertices = new Vertice[vertices.size()];
            vertices.allElements(todosVertices);

            for (int j = 0; j < todosVertices[i].todasAsArestas().length; j++){
                totalArestas++;
            }
        }

        return totalArestas;
    }

    /**
     * Retorna a quantidade total de vértices presentes no grafo
     */
    public int ordem() {
        return vertices.size();
    }

    /**
     * Realiza busca em largura/amplitude no grafo
     *
     * @param idVerticeInicio id do vértice a partir do qual se iniciará a busca
     * @return Subgrafo contendo os vértices do caminho percorrido, ou null caso o vértice
     * não exista no grafo
     */
    public Grafo bfs(int idVerticeInicio) {

        Lista<Integer> visitados = new Lista<>();

        visitados.add(idVerticeInicio);

        LinkedList<Integer> fila = new LinkedList<>();

        fila.add(idVerticeInicio);

        while (fila.size() != 0) {
            idVerticeInicio = fila.poll();

            // Cria um vetor com o tamanho igual ao numero de vizinhos do vertice
            Integer[] vizinhos = new Integer[vertices.find(idVerticeInicio).vizinhos().size()];

            // Preenche o vetor com cada vizinho do vertice
            vertices.find(idVerticeInicio).vizinhos().allElements(vizinhos);

            for (int i = 0; i < vizinhos.length; i++) {

                int vertice = vizinhos[i];

                if (!vertices.find(vertice).visitado()) {
                    vertices.find(vertice).visitar();
                    fila.add(vertice);
                    visitados.add(vertice);
                }
            }
        }

        return subGrafo(visitados);
    }

    /**
     * Realiza busca em profundidade no grafo
     *
     * @param idVerticeInicio id do vértice a partir do qual se iniciará a busca
     * @return Subgrafo contendo os vértices do caminho percorrido, ou null caso o vértice
     * não exista no grafo
     */
    public Grafo dfs(int idVerticeInicio, Lista<Integer> verticesVisitados) {
        int atual = idVerticeInicio;
        vertices.find(atual).visitar();

        Integer[] vizinhos = new Integer[vertices.find(atual).vizinhos().size()];
        vertices.find(atual).vizinhos().allElements(vizinhos);

        for (int i = 0; i < vizinhos.length; i++) {
            if (! vertices.find(vizinhos[i]).visitado() ){
                dfs(vizinhos[i], verticesVisitados);
                vertices.find(vizinhos[i]).visitar();
            }
        }

        return subGrafo(verticesVisitados);
    }

    public ABB<Vertice> getVertices() {
		return vertices;
	}
}
