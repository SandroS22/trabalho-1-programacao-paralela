package com.github.sandros22;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Sensor implements  Runnable{

    private Integer numSensores;

    public Sensor(Integer numSensores) {
        this.numSensores = numSensores;
    }

    @Override
    public void run() {
        while (true) {
            // Valor superior exclusivo
            Integer timeOut = new Random().nextInt(1, 6) * 1000;
            try {
                Thread.sleep(timeOut);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Integer dadoSensorial = new Random().nextInt(1, 1000);

            Integer index = dadoSensorial % numSensores;
            System.out.println(Thread.currentThread().getName() + " fora do sync");
            alocarDado(index, dadoSensorial);
        }
    }

    public synchronized void alocarDado(Integer index, Integer dadoSensorial){
        System.out.println(Thread.currentThread().getName() + " dentro do sync");
        CentralDeControle.atuadores.put(index, dadoSensorial);
    }
}
