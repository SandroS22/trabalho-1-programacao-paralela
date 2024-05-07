package com.github.sandros22;

import java.util.*;
import java.util.logging.Logger;

public class CentralDeControle {

    private final int N_SENSORES;
    private final int N_ATUADORES;
    public static Map<Integer, Integer> atuadores = new HashMap<>();

    private List<Thread> sensores;
    private List<Thread> unidadesDeProcessamento;

    Logger logger = Logger.getLogger(CentralDeControle.class.getName());


    public CentralDeControle(int num_sensores, int num_atuadores) {
        this.N_SENSORES = num_sensores;
        this.N_ATUADORES = num_atuadores;
    }

    public void iniciar() throws InterruptedException {
        sensores = instanciaSensores();
        unidadesDeProcessamento = instanciaUps();
        instanciaAtuadores();
        iniciarThreads(sensores);
        System.out.println("Main thread started");
        iniciarThreads(unidadesDeProcessamento);


        System.out.println("Main thread waiting for termination");
        finalizarThreads(sensores);
        finalizarThreads(unidadesDeProcessamento);


    }

    public void iniciarThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void finalizarThreads(List<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private List<Thread> instanciaUps() {
        logger.info("Iniciando unidades de processamento");
        return getThreads(1);
    }

    private List<Thread> instanciaSensores() {
        logger.info("Instanciando sensores");
        return getThreads(N_SENSORES);
    }

    private List<Thread> getThreads(Integer tamanho) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) {
            Runnable sensor = new Sensor(N_SENSORES);
            Thread thread = new Thread(sensor);
            threads.add(thread);
        }
        return threads;
    }

    private void instanciaAtuadores() {
        logger.info("Iniciando atuadores");
        for (int i = 0; i < N_ATUADORES; i++) {
            atuadores.put(i, 0);
        }
    }
}
