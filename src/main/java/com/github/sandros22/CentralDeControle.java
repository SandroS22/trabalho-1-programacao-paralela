package com.github.sandros22;

import java.util.*;
import java.util.logging.Logger;

public class CentralDeControle {

    private final int N_SENSORES;
    private final int N_ATUADORES;
    public static Map<Integer, Integer> atuadores = new HashMap<>();
    public static List<Map<Integer, Integer>> dados = new ArrayList<>();

    private List<Thread> sensores;
    private List<Thread> unidadesDeProcessamento;

    Logger logger = Logger.getLogger(CentralDeControle.class.getName());


    public CentralDeControle(int num_sensores, int num_atuadores) {
        this.N_SENSORES = num_sensores;
        this.N_ATUADORES = num_atuadores;
    }

    public void iniciar() {
        try {


            sensores = instanciaSensores();
            unidadesDeProcessamento = instanciaUps();
            instanciaAtuadores();
            iniciarThreads(sensores);
            Thread.sleep(1000);
            iniciarThreads(unidadesDeProcessamento);

            finalizarThreads(sensores);
            finalizarThreads(unidadesDeProcessamento);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(dados);
            System.out.println(atuadores);
        }

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
        logger.info("Instanciando unidades de processamento");
        return getUnidadesDeProcessamento();
    }

    private List<Thread> instanciaSensores() {
        logger.info("Instanciando sensores");
        return getSensores(N_SENSORES);
    }

    private List<Thread> getSensores(Integer tamanho) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) {
            Runnable sensor = new Sensor(tamanho);
            Thread thread = new Thread(sensor);
            threads.add(thread);
        }
        return threads;
    }


    private List<Thread> getUnidadesDeProcessamento() {
        List<Thread> threads = new ArrayList<>();
        int quantUps = 50;
        for (int i = 0; i < quantUps; i++) {
            Runnable unidadeDeProcessamento = new UnidadeDeProcessamento();
            Thread thread = new Thread(unidadeDeProcessamento);
            threads.add(thread);
        }
        return threads;
    }


    private void instanciaAtuadores() {
        logger.info("Instanciando atuadores");
        for (int i = 0; i < N_ATUADORES; i++) {
            atuadores.put(i, 0);
        }
    }

    public static synchronized void mostraMsgNoPainel(String msg, long timeOut) throws InterruptedException {
        System.out.printf(msg);
        Thread.sleep(timeOut);
    }
}
