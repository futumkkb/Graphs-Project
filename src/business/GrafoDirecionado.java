package business;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class GrafoDirecionado {

	private int qtdVertices, limiteArestas;
	private int[][] grafo;
	
	public double genTime;
	public double fechoTransitivoDiretoTime;
	public double fechoTransitivoInversoTime;
	public double fechoTransitivoWarshallTime;
	public double baseAntibaseNaive;
	public double baseAntibaseWarshall;

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

	// OK
	public void gerarGrafo(int[][] grafo) {
		
		long a = System.currentTimeMillis();

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
		
		this.genTime = System.currentTimeMillis() - a;
	}

	// OK
	public void print() {
		System.out.println("Grafo:");
		for (int i = 0; i < qtdVertices; i++) {
			System.out.println(i + ": " + Arrays.toString(getVizinhanca(i)));
		}
	}

	// OK
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

	// OK
	private void buscaProfundidade(int vertice, boolean[] visitados, Set<Integer> fechoDireto) {
		visitados[vertice] = true;
		for (int i = 0; i < qtdVertices; i++) {
			if (grafo[vertice][i] == 1 && !visitados[i]) {
				fechoDireto.add(i);
				buscaProfundidade(i, visitados, fechoDireto);
			}
		}
	}
	
	// OK
	private void buscaProfundidade(int vertice, boolean[] visitados) {
		visitados[vertice] = true;
		for (int i = 0; i < qtdVertices; i++) {
			if (grafo[vertice][i] == 1 && !visitados[i]) {
				buscaProfundidade(i, visitados);
			}
		}
	}
	
	// OK
	public void fechoTransitivoDiretoDFS() {
		long a = System.currentTimeMillis();
		
		System.out.println("Fecho transitivo direto usando DFS: ");
		
		for (int i = 0; i < this.qtdVertices; i++) {
			
			Set<Integer> fechoDireto = new HashSet<>();
			
			boolean[] visitados = new boolean[qtdVertices];
			
			buscaProfundidade(i, visitados, fechoDireto);
			
			System.out.println(i + "" + fechoDireto);
		}
		
		this.fechoTransitivoDiretoTime = System.currentTimeMillis() - a;
	}
	
	// OK
	private void encontraFechoInversoRecursivo(int vertice, boolean[] visitados, Set<Integer> fechoInverso) {
		visitados[vertice] = true;
		for (int i = 0; i < qtdVertices; i++) {
			if (grafo[i][vertice] == 1 && !visitados[i]) {
				fechoInverso.add(i);
				encontraFechoInversoRecursivo(i, visitados, fechoInverso);
			}
		}
	}

	// OK
	public void fechoTransitivoInversoDFS() {
		long a = System.currentTimeMillis();
		
		System.out.println("Fecho transitivo inverso usando DFS:");
		
		for (int i = 0; i < this.qtdVertices; i++) {
			
			Set<Integer> fechoInverso = new HashSet<>();
			
			boolean[] visitados = new boolean[qtdVertices];
	
			encontraFechoInversoRecursivo(i, visitados, fechoInverso);
			
			System.out.println(i + "" + fechoInverso);
		}
		
		this.fechoTransitivoInversoTime = System.currentTimeMillis() - a;
	}

	// OK
    public void fechoTransitivoWarshall() {
    	long a = System.currentTimeMillis();
    	
        boolean fechoTransitivo[][] = new boolean[qtdVertices][qtdVertices];
        
        for (int i = 0; i < qtdVertices; i++) {    
            for (int j = 0; j < qtdVertices; j++) {
                if (grafo[i][j] != 0) {
                	fechoTransitivo[i][j] = true;
        		}
        	}
            fechoTransitivo[i][i] = true;
        }
        
        for (int i = 0; i < qtdVertices; i++) {
            for (int j = 0; j < qtdVertices; j++) {
                if (fechoTransitivo[j][i]) {
                    for (int k = 0; k < qtdVertices; k++) {
                        if (fechoTransitivo[j][i] && fechoTransitivo[i][k]) {
                        	fechoTransitivo[j][k] = true;  
                        }
                    }
                }
            }
        }
        
        System.out.println("Fecho transitivo do grafo utilizando o metodo de Warshall:");
        
        for (int v = 0; v < qtdVertices; v++)  {
            for (int w = 0; w < qtdVertices; w++) {
                if (fechoTransitivo[v][w]) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        
		this.fechoTransitivoWarshallTime = System.currentTimeMillis() - a;
    }

	// Não Funciona
	public void baseAntibaseNaive() {
		long a = System.currentTimeMillis();
		
		System.out.println("Bases e Antibases usando algoritmo Naive");
		
		ArrayList<Integer> bases = new ArrayList<Integer>();
		ArrayList<Integer> antibases = new ArrayList<Integer>();
		boolean[] visitados = new boolean[qtdVertices];
		
		for (int i = 0; i < this.qtdVertices; i++) {
	
			buscaProfundidade(i, visitados);
	
			for (int j = 0; j < qtdVertices; j++) {
				if (visitados[j] && !bases.contains(j)) {
					bases.add(j);
				} else {
					if (!antibases.contains(j)) {
						antibases.add(j);
					}
				}
			}
		}
		
		System.out.println("Base: " + bases.toString());
		System.out.println("Antibase: " + antibases.toString());
		
		this.baseAntibaseNaive = System.currentTimeMillis() - a;
	}
	
	// Não Funciona
	public void baseAntibaseWarshall() {
		long a = System.currentTimeMillis();
		
		System.out.println("Bases e Antibases usando algoritmo de Warshall");
		
		boolean[][] alcancaveis = new boolean[qtdVertices][qtdVertices];
		
		ArrayList<Integer> base = new ArrayList<Integer>();
		ArrayList<Integer> antibase = new ArrayList<Integer>();

		for (int i = 0; i < qtdVertices; i++) {
			for (int j = 0; j < qtdVertices; j++) {
				alcancaveis[i][j] = getBoolean(grafo[i][j]);
			}
		}

		for (int k = 0; k < qtdVertices; k++) {
			for (int i = 0; i < qtdVertices; i++) {
				for (int j = 0; j < qtdVertices; j++) {
					alcancaveis[i][j] = alcancaveis[i][j] || (alcancaveis[i][k] && alcancaveis[k][j]);
				}
			}
		}

		for (int i = 0; i < qtdVertices; i++) {
			for (int j = 0; j < qtdVertices; j++) {
				if (!alcancaveis[i][j]) {
					antibase.add(i);
					break;
				} else {
					if (!base.contains(i)) {
						base.add(i);
					}
				}
			}
		}

		System.out.println("Base: " + base.toString());
		System.out.println("Antibase: " + antibase.toString());
		
		this.baseAntibaseWarshall = System.currentTimeMillis() - a;
	}
	
	public boolean getBoolean(int value) {
		return value == 1;
	}
}
