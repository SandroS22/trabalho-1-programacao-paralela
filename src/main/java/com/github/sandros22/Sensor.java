package com.github.sandros22;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Sensor implements  Runnable{

    private final Integer numAtuadores;

    public Sensor(Integer numAtuadores) {
        this.numAtuadores = numAtuadores;
    }

    private final Logger logger = Logger.getLogger(Sensor.class.getName());

    @Override
    public void run() {
        int contador = 0;
        logger.info("Sensor rodando");
        while (contador < 5) {
            // Valor superior exclusivo
            Integer timeOut = new Random().nextInt(1, 6) * 1000;
            try {
                Thread.sleep(timeOut);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ReentrantLock lock = new ReentrantLock();
            lock.lock();
            Integer dadoSensorial = new Random().nextInt(1, 1000);
            Integer index = dadoSensorial % numAtuadores;

            Integer nivelAtividade = new Random().nextInt(1, 100);
            alocarDado(index, nivelAtividade);
            contador++;
            lock.unlock();
        }
    }

    public synchronized void alocarDado(Integer index, Integer dadoSensorial){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(index, dadoSensorial);
        CentralDeControle.dados.add(map);
    }
}
