package business;

public class GrafoCompleto extends Grafo {

    private int ordem;

    /**
     * Cria um grafo completo
     * @param ordem
     * @param nome
     */
    public GrafoCompleto(int ordem, String nome) {
        super(nome);
        this.ordem = ordem;

       // Criação dos vertices
       for (int i = 1; i <= ordem; i++) {
           vertices.add(i, new Vertice(i));
       }

        // Criação das arestas
        for (int i = 1; i < ordem; i++){
           Vertice v = vertices.find(i);
           for (int j = i + 1; j <= ordem; j++) {
                // this.addAresta(i, j, 0);
                v.addAresta(j);        
            }
        }
    }
}
