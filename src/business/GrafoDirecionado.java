package business;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GrafoDirecionado {
	
	private List<LinkedList<Integer>> grafo;
	private int qtdVertices;
	private int conexoesAresta;
	
	public GrafoDirecionado(int qtdVertices, int conexoesAresta) {
		if (conexoesAresta < qtdVertices) {
			this.conexoesAresta = conexoesAresta;
			this.qtdVertices = qtdVertices;
			this.grafo = new LinkedList<>();
			
			for (int i = 0; i < this.qtdVertices; i++) {
				grafo.add(new LinkedList<Integer>());
			}
			
			gerarGrafo();
		} else {
			System.out.println("O numero de vertices deve ser maior que o numero de arestas");
		}
	}
	
	public void gerarGrafo() {
		for (int i = 0; i < this.qtdVertices; i++) {
			
			while (getVizinhanca(i).size() < this.conexoesAresta) {
				
				int random = new Random().nextInt(this.qtdVertices);
				
				if (getVizinhanca(i).contains(random) || random == i) {
					continue;
				}
				
				getVizinhanca(i).add(random);
				
			}
		}
	}
	
	public boolean existeVertice(int v) {
		return grafo.get(v) != null;
	}

	public boolean existeAresta(int v, int w) {
		return grafo.get(v).contains(w);
	}
	
	public void addVertice(int v) {
		if (!existeVertice(v)) {
			grafo.set(v, new LinkedList<Integer>());
		}
	}
	
	public void addAresta(int v, int w) {
		if (!existeAresta(v, w)) {
			grafo.get(v).add(w);
		}
	}
	
	public LinkedList<Integer> getVizinhanca(int v) {
		return existeVertice(v) ? this.grafo.get(v) : null;
	}
	
	public void print() {
		for (int i = 0; i < qtdVertices; i++) {
			System.out.println(i + ": " + grafo.get(i).toString());
		}
	}
}
