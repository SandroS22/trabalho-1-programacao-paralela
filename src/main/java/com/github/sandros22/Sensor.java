package com.github.sandros22;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Sensor implements  Runnable{

    private final Integer numSensores;

    public Sensor(Integer numSensores) {
        this.numSensores = numSensores;
    }

    @Override
    public void run() {
        int contador = 0;
        while (contador < 5) {
            ReentrantLock lock = new ReentrantLock();
            // Valor superior exclusivo
            Integer timeOut = new Random().nextInt(1, 6) * 1000;
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.lock();
            Integer dadoSensorial = new Random().nextInt(1, 1000);
            Integer index = dadoSensorial % numSensores;

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
