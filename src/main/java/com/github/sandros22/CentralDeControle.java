package com.github.sandros22;

import java.util.*;
import java.util.logging.Logger;

public class CentralDeControle {

    private int N_SENSORES;
    private int N_ATUADORES;
    public Map<Integer, Integer> atuadores = new HashMap<>();
    Logger logger = Logger.getLogger(CentralDeControle.class.getName());


    public CentralDeControle(int num_sensores, int num_atuadores) {
        this.N_SENSORES = num_sensores;
        this.N_ATUADORES = num_atuadores;
    }

    public void iniciar() throws InterruptedException {
        List<Thread> sensores = instanciaSensores();
        instanciaAtuadores();
        for (Thread thread : sensores) {
            thread.start();
        }
        System.out.println("Main thread started");
        for (Thread thread : sensores) {
            thread.join();
        }
    }

    private List<Thread> instanciaSensores() {
        logger.info("Iniciando sensores");
        return getThreads(N_SENSORES);
    }

    private void instanciaAtuadores() {
        logger.info("Iniciando atuadores");
        for (int i = 0; i < N_ATUADORES; i++) {
            atuadores.put(i, 0);
        }
    }

    private List<Thread> getThreads(int nAtuadores) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < nAtuadores; i++) {
            int finalI = i;
            Runnable runnable = () -> System.out.printf("Thread: %d\n", finalI);
            threads.add(new Thread(runnable));
        }
        return threads;
    }

    public void setN_SENSORES(int N_SENSORES) {
        this.N_SENSORES = N_SENSORES;
    }

    public void setN_ATUADORES(int N_ATUADORES) {
        this.N_ATUADORES = N_ATUADORES;
    }

    public int getN_SENSORES() {
        return N_SENSORES;
    }

    public int getN_ATUADORES() {
        return N_ATUADORES;
    }
}
