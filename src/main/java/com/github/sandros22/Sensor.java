package com.github.sandros22;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Sensor implements  Runnable{

    private final Integer numAtuadores;

    public Sensor(Integer numAtuadores) {
        this.numAtuadores = numAtuadores;
    }

    @Override
    public void run() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        int contador = 0;
        while (contador < 5) {
            // Valor superior exclusivo
            Integer timeOut = new Random().nextInt(1, 6) * 1000;
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
