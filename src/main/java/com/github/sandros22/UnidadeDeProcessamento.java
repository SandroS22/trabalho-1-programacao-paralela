package com.github.sandros22;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class UnidadeDeProcessamento implements Runnable {

    private final Logger logger = Logger.getLogger("UnidadeDeProcessamento");

    private static final ReentrantLock lock = new ReentrantLock();

    public UnidadeDeProcessamento() {
    }

    public void processar(Map<Integer, Integer> dado) throws InterruptedException {
        CentralDeControle.atuadores.put(dado.entrySet().iterator().next().getKey(), dado.entrySet().iterator().next().getValue());
        CentralDeControle.dados.remove(dado);
        Integer time = new Random().nextInt(2, 4) * 1000;
        long timer1 = new Date().getTime();
        CentralDeControle.mostraMsgNoPainel(String.format("Alterando: %d com valor %d\n", dado.entrySet().iterator().next().getKey(), dado.values().iterator().next()), 1000);

        Thread.sleep(time);
        long timer2 = new Date().getTime();
        System.out.println((timer2 - timer1) / 1000);
        lock.unlock();
    }

    @Override
    public void run() {
        while (!CentralDeControle.dados.isEmpty()) {
            lock.lock();
            Map<Integer, Integer> dado = CentralDeControle.dados.get(0);
            try {
                if (dado != null) {
                    processar(dado);
                } else {
                    CentralDeControle.dados.remove(0);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
