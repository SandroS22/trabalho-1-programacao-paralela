package com.github.sandros22;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Sensor implements  Runnable{

    private final Integer numAtuadores;

    private static final ReentrantLock lock = new ReentrantLock();
    private static final ReentrantLock lockTime = new ReentrantLock();

    public Sensor(Integer numAtuadores) {
        this.numAtuadores = numAtuadores;
    }

    @Override
    public void run() {
        while (true) {
            lockTime.lock();
            // Valor superior exclusivo
            int timeOut = new Random().nextInt(1, 6) * 1000;
            try {
                Thread.sleep(timeOut);
                lockTime.unlock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            lock.lock();
            Integer dadoSensorial = new Random().nextInt(1, 1000);
            Integer index = dadoSensorial % numAtuadores;

            Integer nivelAtividade = new Random().nextInt(1, 100);
            alocarDado(index, nivelAtividade);
            lock.unlock();
        }
    }

    public synchronized void alocarDado(Integer index, Integer dadoSensorial){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(index, dadoSensorial);
        CentralDeControle.dados.add(map);
    }
}
