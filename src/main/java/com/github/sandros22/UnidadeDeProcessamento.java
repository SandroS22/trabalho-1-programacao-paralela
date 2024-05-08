package com.github.sandros22;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class UnidadeDeProcessamento implements Runnable{
    public UnidadeDeProcessamento() {
    }

    public synchronized void processar(Map<Integer, Integer> dado) throws InterruptedException {
        CentralDeControle.atuadores.put(dado.entrySet().iterator().next().getKey(), dado.entrySet().iterator().next().getValue());
        CentralDeControle.dados.remove(0);
        Thread.sleep(0);
        System.out.println(CentralDeControle.dados);
    }

    @Override
    public void run() {
        int contador = 0;
        while (contador < 5){
            ReentrantLock lock = new ReentrantLock();
            if(CentralDeControle.dados.size() > 1) {
                Map<Integer, Integer> dado = CentralDeControle.dados.get(0);
                try {
                    processar(dado);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            contador++;
        }
    }
}
