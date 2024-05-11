package com.github.sandros22;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class UnidadeDeProcessamento implements Runnable {

    private final Logger logger = Logger.getLogger("UnidadeDeProcessamento");

    private final Object mtx = new Object();

    public UnidadeDeProcessamento() {
    }

    public synchronized void processar(Map<Integer, Integer> dado) throws InterruptedException {
        CentralDeControle.atuadores.put(dado.entrySet().iterator().next().getKey(), dado.entrySet().iterator().next().getValue());
        CentralDeControle.dados.remove(dado);
        Integer time = new Random().nextInt(2, 4) * 1000;
        CentralDeControle.mostraMsgNoPainel(String.format("Alterando: %d com valor %d\n", dado.entrySet().iterator().next().getKey(), dado.values().iterator().next()), 1000);
        System.out.println(dado);
        Thread.sleep(time);
    }

    @Override
    public void run() {
        logger.info("Unidade de Processamento rodando");
        ReentrantLock lock = new ReentrantLock();
        while (!CentralDeControle.dados.isEmpty()) {
            synchronized (mtx) {
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
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
