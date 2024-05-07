package com.github.sandros22;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sensor implements  Runnable{

    private Integer numSensores;

    public Sensor(Integer numSensores) {
        this.numSensores = numSensores;
    }

//    public Runnable receberLeitura(Integer numSensores) throws InterruptedException {
//        while (true) {
//            // Valor superior exclusivo
//            Integer timeOut = new Random().nextInt(1, 6) * 1000;
//            Thread.sleep(timeOut);
//            Integer dadoSensorial = new Random().nextInt(1, 1000);
//
//            Integer index = dadoSensorial % numSensores;
//            ReentrantLock lock = new ReentrantLock();
//            lock.lock();
//            CentralDeControle.atuadores.put(index, dadoSensorial);
//            lock.unlock();
//
//            System.out.println(dadoSensorial);
//        }
//    }

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
            ReentrantLock lock = new ReentrantLock();
            lock.lock();
            CentralDeControle.atuadores.put(index, dadoSensorial);
            lock.unlock();

            System.out.println(dadoSensorial);
        }
    }
}
