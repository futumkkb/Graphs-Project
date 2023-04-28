package business;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class GrafoMutavel extends Grafo {
    /**
     * Construtor. Cria um grafo vazio com um nome escolhido pelo usuário. Em caso
     * de nome não informado
     * (string vazia), recebe o nome genérico "Grafo"
     *
     * @param nome Nome do grafo
     */
    public GrafoMutavel(String nome) {
        super(nome);
    }

    /**
     * Lê conteudo de um arquivo .txt e instancia vértices conforme descrito,
     * adicionando-os à ABB. Método abstrato cuja implementação varia em
     * GrafoDirecionado e GrafoNaoDirecionado
     *
     * @param nomeArquivo Nome do arquivo a ser lido
     */
    public void carregar(String nomeArquivo) throws FileNotFoundException {
        File file = new File(nomeArquivo);
        Scanner fileReader = new Scanner(file);

        String line = null;
        String[] tokens;
        int key;

        while (fileReader.hasNextLine()){
            line = fileReader.nextLine();
            tokens = line.split("\\D+");

            Vertice origem = new Vertice(Integer.parseInt(tokens[0]));
            vertices.add(Integer.parseInt(tokens[0]), origem);

            Vertice destino = new Vertice(Integer.parseInt(tokens[1]));
            vertices.add(Integer.parseInt(tokens[1]), destino);

            origem.addAresta(Integer.parseInt(tokens[1]));
        }
        fileReader.close();
    }

    /**
     * Adiciona um vértice com o id especificado.
     * @param id O identificador do vértice a ser criado/adicionado
     * @return TRUE se houve a inclusão do vértice, FALSE se já existia vértice com este id
     */
    public boolean addVertice(int id) {
        Vertice novo = new Vertice(id);
        return this.vertices.add(id, novo);
    }

    /**
     * Remove todas as arestas que chegam no vértice e remove o vértice.
     * @param id O Identificador do vértice a ser removido
     * @return O vértice removido ou null caso não exista.
     */
    public Vertice removeVertice(int id) {
        Vertice[] array = new Vertice[vertices.size()];
        Vertice[] todosVertices = vertices.allElements(array);
        Aresta temp;

        for(int i = 0; i < todosVertices.length; i++) {
            temp = todosVertices[i].existeAresta(id);
            if(temp != null) {
                todosVertices[i].removeAresta(id);
            }
        }
        return vertices.remove(id);
    }

    /**
     * Salva grafo em um arquivo 
     * @param nomeArquivo Nome do arquivo em que o grafo será salvo
     */
    public void salvar(String nomeArquivo) {
        File file = new File("./" + nomeArquivo);
        
        try {
        	
			FileWriter fw = new FileWriter(file);

			Vertice[] abbVertices = vertices.allElements(new Vertice[] {});

	        for (Vertice vertice : abbVertices) {
	
	            Aresta[] allArestas = vertice.todasAsArestas();
	
	            for (Aresta aresta : allArestas) {
	
	                Vertice destino = new Vertice(aresta.destino());
	                
	                if (aresta.peso() == -1) {
	                	fw.write(vertice.getId() + " " + destino.getId());
	                } else {
	                	fw.write(vertice.getId() + " " + destino.getId() + " " + aresta.peso());
	                }
	            }
	        }
	        
	        fw.close();
	        
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public abstract boolean addAresta(int origem, int destino, int peso);

    public abstract Aresta removeAresta(int origem, int destino);
}
