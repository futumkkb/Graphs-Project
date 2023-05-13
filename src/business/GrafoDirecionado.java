package business;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Random;

public class GrafoDirecionado {

	private int qtdVertices, limiteArestas;
	private int[][] grafo;
	private boolean[][] matrizAdjacencia;

	// - 0 1 2 3 4 5 6 7 8 9
	// 0 - - - - - - - - - -
	// 1 - - - - - - - - - -
	// 2 - 1 - - 1 - - - - -
	// 3 - - 1 - 1 - - - - -
	// 4 - 1 - - - - - - - -
	// 5 - - - - - - - - - -
	// 6 - - - - - - - - - -
	// 7 - - - - - - - - - -
	// 8 - - - - - - - - - -
	// 9 - - - - - - - - - -

	public GrafoDirecionado(int qtdVertices, int limiteArestas) {
		if (limiteArestas > qtdVertices) {
			System.out.println("O numero maximo de arestas por vertice não pode ser maior que o numero de vertices");
			return;
		}

		this.qtdVertices = qtdVertices;
		this.limiteArestas = limiteArestas;

		this.grafo = new int[qtdVertices][qtdVertices];

		gerarGrafo(this.grafo);
	}

	public void gerarGrafo(int[][] grafo) {

		for (int i = 0; i < qtdVertices; i++) {
			for (int j = 0; j < qtdVertices; j++) {
				grafo[i][j] = 0;
			}
		}

		for (int i = 0; i < qtdVertices; i++) {
			for (int j = 0; j < limiteArestas; j++) {
				for (;;) {
					int randomVertice = (int) (Math.random() * (qtdVertices));

					if (grafo[i][randomVertice] != 1 && randomVertice != i) {
						grafo[i][randomVertice] = 1;
						break;
					}
				}
			}
		}
	}

	public void gerarMatrizAdjacencia(int numVertices, int numArestas) {
		boolean[][] matriz = new boolean[numVertices][numVertices];
		int countArestas = 0;
		Random rand = new Random();

		while (countArestas < numArestas) {
			int u = rand.nextInt(numVertices);
			int v = rand.nextInt(numVertices);

			if (u != v && !matriz[u][v]) {
				matriz[u][v] = true;
				matriz[v][u] = true;
				countArestas++;
			}
		}
		this.matrizAdjacencia = matriz;

	}

	public void print() {
		for (int i = 0; i < qtdVertices; i++) {
			System.out.println(i + ": " + Arrays.toString(getVizinhanca(i)));
		}
	}

	public int[] getVizinhanca(int vertice) {
		int[] vizinhanca = new int[limiteArestas];
		int j = 0;
		for (int i = 0; i < qtdVertices; i++) {
			if (this.grafo[vertice][i] == 1) {
				vizinhanca[j] = i;
				j++;
			}
		}
		return vizinhanca;
	}

	public void buscaProfundidade(int verticeInicial) {
		boolean[] visitados = new boolean[qtdVertices];
		buscaProfundidade(verticeInicial, visitados);
	}

	private void buscaProfundidade(int vertice, boolean[] visitados) {
		visitados[vertice] = true;
		System.out.print(vertice + " ");

		for (int i = 0; i < qtdVertices; i++) {
			if (grafo[vertice][i] == 1 && !visitados[i]) {
				buscaProfundidade(i, visitados);
			}
		}
	}

	public Set<Integer> encontraFechoInverso(int vertice) {
		Set<Integer> fechoInverso = new HashSet<>();
		boolean[] visitados = new boolean[qtdVertices];

		encontraFechoInversoRecursivo(vertice, visitados, fechoInverso);

		return fechoInverso;
	}

	private void encontraFechoInversoRecursivo(int vertice, boolean[] visitados, Set<Integer> fechoInverso) {
		visitados[vertice] = true;
		for (int i = 0; i < qtdVertices; i++) {
			if (grafo[i][vertice] == 1 && !visitados[i]) {
				fechoInverso.add(i);
				encontraFechoInversoRecursivo(i, visitados, fechoInverso);
			}
		}
	}

	public int[][] warshall() {
		int[][] fechoTransitivo = new int[qtdVertices][qtdVertices];

		// inicializa a matriz de fecho transitivo com os valores da matriz de
		// adjacência
		for (int i = 0; i < qtdVertices; i++) {
			for (int j = 0; j < qtdVertices; j++) {
				fechoTransitivo[i][j] = grafo[i][j];
			}
		}

		// aplica o algoritmo de Warshall
		for (int k = 0; k < qtdVertices; k++) {
			for (int i = 0; i < qtdVertices; i++) {
				for (int j = 0; j < qtdVertices; j++) {
					if (fechoTransitivo[i][k] == 1 && fechoTransitivo[k][j] == 1) {
						fechoTransitivo[i][j] = 1;
					}
				}
			}
		}

		return fechoTransitivo;
	}

	public int getQtdVertices() {
		return this.qtdVertices;
	}

	public void baseAntibase(int verticeInicial) {
		// inicializa as estruturas de dados
		boolean[] visitados = new boolean[qtdVertices];
		ArrayList<Integer> base = new ArrayList<Integer>();
		ArrayList<Integer> antibase = new ArrayList<Integer>();

		// realiza a busca em profundidade
		buscaProfundidade(verticeInicial, visitados);

		// separa os vértices na base e antibase
		for (int i = 0; i < qtdVertices; i++) {
			if (visitados[i]) {
				base.add(i);
			} else {
				antibase.add(i);
			}
		}
		// imprime o resultado
		System.out.println("Base: " + base.toString());
		System.out.println("Antibase: " + antibase.toString());
	}

	public void baseAntibaseWarshall() {
		// Inicializa as estruturas de dados
		boolean[][] alcancaveis = new boolean[qtdVertices][qtdVertices];
		ArrayList<Integer> base = new ArrayList<Integer>();
		ArrayList<Integer> antibase = new ArrayList<Integer>();

		// Preenche a matriz alcancaveis com a matriz de adjacencia
		for (int i = 0; i < qtdVertices; i++) {
			for (int j = 0; j < qtdVertices; j++) {
				alcancaveis[i][j] = matrizAdjacencia[i][j];
			}
		}

		// Aplica o algoritmo de Warshall
		for (int k = 0; k < qtdVertices; k++) {
			for (int i = 0; i < qtdVertices; i++) {
				for (int j = 0; j < qtdVertices; j++) {
					alcancaveis[i][j] = alcancaveis[i][j] || (alcancaveis[i][k] && alcancaveis[k][j]);
				}
			}
		}

		// Separa os vértices na base e antibase
		for (int i = 0; i < qtdVertices; i++) {
			boolean todosAlcancaveis = true;
			for (int j = 0; j < qtdVertices; j++) {
				if (!alcancaveis[i][j]) {
					todosAlcancaveis = false;
					break;
				}
			}
			if (todosAlcancaveis) {
				base.add(i);
			} else {
				antibase.add(i);
			}
		}

		// Imprime o resultado
		System.out.println("Base: " + base.toString());
		System.out.println("Antibase: " + antibase.toString());
	}

}
