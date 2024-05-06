package com.github.sandros22;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.print("Insira o número de sensores: ");
        Scanner entradaNumSensores = new Scanner(System.in);
        int N_SENSORES = entradaNumSensores.nextInt();

        System.out.print("Insira o número de atuadores: ");
        Scanner entradaNumAtuadores = new Scanner(System.in);
        int N_ATUADORES = entradaNumAtuadores.nextInt();

        CentralDeControle central = new CentralDeControle(N_SENSORES, N_ATUADORES);
        central.iniciar();
    }
}