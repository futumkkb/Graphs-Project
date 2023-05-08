package app;

import business.GrafoDirecionado;

public class Main {
	
	// OK - (estrutura de dados) grafo direcionado - Yan
	
	// OK - (metodo) gerador de grafo - Andre
	
	// (metodo) implemetar um metodo autoral (naive) para encontrar os fechos transitivos diretos e inversos
	// de qualquer vertice e dizer base/antibase
	
	// (metodo) implementar o metodo de warshall e dizer base/antibase
	
	// (metodo) teste dizendo tempo de execução usando 10, 100, 1000, 10000 e 100000 vertices
	
	public static void main(String[] args) {
		GrafoDirecionado gr = new GrafoDirecionado(100000, 2);
		
		gr.gerarGrafo();
		
		gr.print();
	}
	
}
