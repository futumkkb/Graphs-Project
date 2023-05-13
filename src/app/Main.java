package app;

import java.rmi.server.SocketSecurityException;

import business.GrafoDirecionado;

import java.util.Scanner;
import java.util.Set;

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
		gr.gerarMatrizAdjacencia(qtdVertices, qtdArestas);
		System.out.println("Grafo:");
		gr.print();

		fechoTransitivoDiretoDFS(qtdVertices, gr);
		fechoTransitivoInversoDFS(qtdVertices, gr);
		fechoTransitivoWarshall(qtdVertices, gr);
		baseAntibase(qtdVertices, gr);
		baseAntibaseWarshall(qtdVertices, gr);

	}

	public static void fechoTransitivoDiretoDFS(int numVertices, GrafoDirecionado g) {
		System.out.println("Fecho transitivo direto usando DFS: ");
		for (int i = 0; i < numVertices; i++) {
			g.buscaProfundidade(i);
			System.out.println(" ");
		}
		System.out.println("----------------------");
	}

	public static void fechoTransitivoInversoDFS(int numVertices, GrafoDirecionado g) {
		System.out.println("Fecho transitivo inverso usando DFS: ");
		for (int i = 0; i < numVertices; i++) {
			Set<Integer> fechoInverso = g.encontraFechoInverso(i);
			System.out.println("Fecho inverso do vértice " + i + fechoInverso);
		}
		System.out.println("----------------------");
	}

	public static void fechoTransitivoWarshall(int numVertices, GrafoDirecionado g) {
		System.out.println("Fecho transitivo do grafo utilizando o metodo de warshall:");

		int[][] fechoTransitivo = g.warshall();
		// Imprime matriz do método de warshall
		for (int i = 0; i < g.getQtdVertices(); i++) {
			for (int j = 0; j < g.getQtdVertices(); j++) {
				System.out.print(fechoTransitivo[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("----------------------");
	}

	public static void baseAntibase(int numVertices, GrafoDirecionado g) {
		for (int i = 0; i < numVertices; i++) {
			g.baseAntibase(i);
		}
		System.out.println("----------------------");
	}

	public static void baseAntibaseWarshall(int numVertices, GrafoDirecionado g) {
		g.baseAntibaseWarshall();
		System.out.println("----------------------");
	}
}
