package app;

import java.rmi.server.SocketSecurityException;

import business.GrafoDirecionado;
import java.util.Scanner;

public class Main {

	// OK - (estrutura de dados) grafo direcionado - Yan

	// OK - (metodo) gerador de grafo - Andre

	// (metodo) implemetar um metodo autoral (naive) para encontrar os fechos
	// transitivos diretos e inversos
	// de qualquer vertice e dizer base/antibase

	// (metodo) implementar o metodo de warshall e dizer base/antibase

	// (metodo) teste dizendo tempo de execução usando 10, 100, 1000, 10000 e 100000
	// vertices

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Você deseja criar um grafo com quantos vértices? ");
		int qtdVertices = sc.nextInt();
		System.out.println("Você deseja que cada vertice do seu grafo tenha quantas arestas? ");
		int qtdArestas = sc.nextInt();

		GrafoDirecionado gr = new GrafoDirecionado(qtdVertices, qtdArestas);

		System.out.println("Grafo:");
		gr.print();

		System.out.println("--------------------------");

		System.out.println("Você deseja realizar uma busca em profundidade a partir de qual vértice?");
		int verticeInicial = sc.nextInt();

		gr.buscaProfundidade(verticeInicial);

	}

}
