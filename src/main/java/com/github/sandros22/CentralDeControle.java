package com.github.sandros22;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

public class CentralDeControle {

    private final int N_SENSORES;
    private final int N_ATUADORES;
    private Map<Integer, Integer> atuadores = new HashMap<>();
    Logger logger = Logger.getLogger(CentralDeControle.class.getName());


    public CentralDeControle(int num_sensores, int num_atuadores) {
        this.N_SENSORES = num_sensores;
        this.N_ATUADORES = num_atuadores;
    }

    public void iniciar() throws InterruptedException {
        instanciaSensores();
        instanciaUps();
        instanciaAtuadores();
        for (Thread thread : sensores) {
            thread.start();
        }
        System.out.println("Main thread started");
        for (Thread thread : unidadesDeProcessamento) {
            thread.join();
        }
    }

    private void instanciaUps() {
        for (int i = 0; i < N_ATUADORES; i++) {
            int finalI = i;
            UnidadeDeProcessamento unidadeDeProcessamento = new UnidadeDeProcessamento();
            Runnable runnable = unidadeDeProcessamento.processar();
            Thread thread = new Thread(runnable);
        }
    }

    private void instanciaSensores() {
        logger.info("Iniciando sensores");
        for (int i = 0; i < N_SENSORES; i++) {
            int finalI = i;
            Sensor sensor = new Sensor();
            Runnable runnable = sensor.receberLeitura();
            Thread thread = new Thread(runnable);
        }
    }

    private void instanciaAtuadores() {
        logger.info("Iniciando atuadores");
        for (int i = 0; i < N_ATUADORES; i++) {
            atuadores.put(i, 0);
        }
    }

    public int getN_SENSORES() {
        return N_SENSORES;
    }

    public int getN_ATUADORES() {
        return N_ATUADORES;
    }
}
