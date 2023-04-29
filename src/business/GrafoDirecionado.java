package business;

import java.util.LinkedList;

public class GrafoDirecionado {
	
	private LinkedList<LinkedList<Integer>> grafo;
	private int qtdVertices;
	
	public GrafoDirecionado(int qtdVertices) {
		this.qtdVertices = qtdVertices;
		this.grafo = new LinkedList<>();

		for (int i = 0; i < this.qtdVertices; i++) {
			grafo.add(new LinkedList<Integer>());
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
	
	public void gerarGrafo() {
		// Andre: conectar vertices gerando arestas
	}
	
	public void print() {
		for (int i = 0; i < qtdVertices; i++) {
			System.out.println(i + ": " + grafo.get(i).toString());
		}
	}
}
