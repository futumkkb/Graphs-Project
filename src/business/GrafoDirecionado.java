package business;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
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
	public void baseAntibaseWarshall() {
		long a = System.currentTimeMillis();
		
		System.out.println("Bases e Antibases usando algoritmo de Warshall");
		
		int[][] distancias = new int[qtdVertices][qtdVertices];
		
		List<Integer> bases = new ArrayList<>();
		List<Integer> antibases = new ArrayList<>();

        // Inicializar a matriz de distâncias
        for (int i = 0; i < qtdVertices; i++) {
            for (int j = 0; j < qtdVertices; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                } else if (grafo[i][j] == 0) {
                    distancias[i][j] = Integer.MAX_VALUE;
                } else {
                    distancias[i][j] = grafo[i][j];
                }
            }
        }

        // Executar o algoritmo de Floyd-Warshall
        for (int k = 0; k < qtdVertices; k++) {
            for (int i = 0; i < qtdVertices; i++) {
                for (int j = 0; j < qtdVertices; j++) {
                    if (distancias[i][k] != Integer.MAX_VALUE &&
                            distancias[k][j] != Integer.MAX_VALUE &&
                            distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                    }
                }
            }
        }
        
        for (int i = 0; i < qtdVertices; i++) {
            boolean eBase = true;
            boolean eAntibase = true;

            for (int j = 0; j < qtdVertices; j++) {
                if (i != j && distancias[i][j] != Integer.MAX_VALUE) {
                    eBase = false;
                    break;
                }
            }
            
            for (int j = 0; j < qtdVertices; j++) {
                if (i != j && distancias[j][i] != Integer.MAX_VALUE) {
                    eAntibase = false;
                    break;
                }
            }

            if (eBase) {
            	bases.add(i);
            }
            
            if (eAntibase) {
                antibases.add(i);
            }
        }

		System.out.println("Antibases: " + antibases.toString());

		System.out.println("Bases: " + bases.toString());
		
		this.baseAntibaseWarshall = System.currentTimeMillis() - a;
	}
	
    public void baseAntibaseNaive() {
		long a = System.currentTimeMillis();
    	
		List<Integer> bases = new ArrayList<>();
		List<Integer> antibases = new ArrayList<>();

        for (int i = 0; i < qtdVertices; i++) {
            boolean eBase = true;
            boolean eAntibase = true;

            for (int j = 0; j < qtdVertices; j++) {
                if (grafo[i][j] == 1) {
                    eBase = false;
                    break;
                }
            }
            
            for (int j = 0; j < qtdVertices; j++) {
                if (i != j && grafo[j][i] == 1) {
                    eAntibase = false;
                    break;
                }
            }

            if (eBase) {
            	bases.add(i);
            }
            
            if (eAntibase) {
                antibases.add(i);
            }
        }

		System.out.println("Antibases: " + antibases.toString());

		System.out.println("Bases: " + bases.toString());
		
		this.baseAntibaseNaive = System.currentTimeMillis() - a;
    }
    
	public boolean getBoolean(int value) {
		return value == 1;
	}
}
