package app;

import business.GrafoDirecionado;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Você deseja criar um grafo com quantos vértices? "); int qtdVertices = sc.nextInt();
		
		System.out.println("Você deseja que cada vertice do seu grafo tenha quantas arestas? "); int qtdArestas = sc.nextInt();
		
		sc.close();

		GrafoDirecionado gd = new GrafoDirecionado(qtdVertices, qtdArestas);
		
		System.out.println("");
		
		gd.print();
		
		System.out.println("");
		
		gd.fechoTransitivoDiretoDFS();
		
		System.out.println("");
		
		gd.fechoTransitivoInversoDFS();
		
		System.out.println("");
		
		gd.fechoTransitivoWarshall();
		
		System.out.println("");
		
		gd.baseAntibaseNaive();
		
		System.out.println("");
		
		gd.baseAntibaseWarshall();
		
		System.out.println("");
		
		System.out.println("-----------------------------");
		
		System.out.println("");
		
		System.out.println("Tempo de Execucao: ");
		System.out.println("- Geracao de Grafo: " + gd.genTime + " milisegundos");
		System.out.println("- Fecho Transitivo Direto: " + gd.fechoTransitivoDiretoTime + " milisegundos");
		System.out.println("- Fecho Transitivo Inverso: " + gd.fechoTransitivoInversoTime + " milisegundos");
		System.out.println("- Fecho Transitivo Warshall: " + gd.fechoTransitivoWarshallTime + " milisegundos");
		System.out.println("- Base e Antibase Naive: " + gd.baseAntibaseNaive + " milisegundos");
		System.out.println("- Base e Antibase Warshall: " + gd.baseAntibaseWarshall + " milisegundos");
		
		System.out.println("");
		
		System.out.println("-----------------------------");
	}
}
