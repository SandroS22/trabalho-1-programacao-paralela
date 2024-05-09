package com.github.sandros22;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class UnidadeDeProcessamento implements Runnable{
    public UnidadeDeProcessamento() {
    }

    public synchronized void processar(Map<Integer, Integer> dado) throws InterruptedException {
        CentralDeControle.atuadores.put(dado.entrySet().iterator().next().getKey(), dado.entrySet().iterator().next().getValue());
        List<Map<Integer, Integer>> aux = new ArrayList<>();
        aux.add(CentralDeControle.dados.get(0));
        CentralDeControle.dados.removeAll(aux);
        Thread.sleep(0);
    }

    @Override
    public void run() {
        ReentrantLock lock = new ReentrantLock();
        int contador = 0;
        while (contador < 5){
            if(CentralDeControle.dados.size() > 1) {
                lock.lock();
                Map<Integer, Integer> dado = CentralDeControle.dados.get(0);
                try {
                    if(dado != null) {
                        processar(dado);
                        lock.unlock();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            contador++;
        }
    }
}
