package com.github.sandros22;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class UnidadeDeProcessamento implements Runnable {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final ReentrantLock lockTime = new ReentrantLock();
    private static boolean erroMudarNivel = false;
    private static boolean erroMsgNoTerminal = false;

    public UnidadeDeProcessamento() {
    }

    public void processar(Map<Integer, Integer> dado) throws InterruptedException {
        CentralDeControle.atuadores.put(dado.entrySet().iterator().next().getKey(), dado.entrySet().iterator().next().getValue());
        CentralDeControle.dados.remove(dado);

        Thread t1 = new Thread(mudarNivel());
        Thread t2 = new Thread(mostraMsgNoPainel());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        if(!erroMudarNivel && !erroMsgNoTerminal) {
            CentralDeControle.mostraMsgNoPainel(String.format("Alterando: %d com valor %d\n", dado.entrySet().iterator().next().getKey(), dado.values().iterator().next()), 1000);
            lock.unlock();
            lockTime.lock();
            int time = new Random().nextInt(2, 4) * 1000;
            Thread.sleep(time);
            lockTime.unlock();
        } else {
            erroMudarNivel = false;
            erroMsgNoTerminal = false;
            CentralDeControle.mostraMsgNoPainel(String.format("Falha: %d\n", dado.entrySet().iterator().next().getKey()), 0);
        }
    }

    public Runnable mudarNivel() {
        return new Runnable() {
            @Override
            public void run() {
                int chanceErro = new Random().nextInt(0, 101);
                if (chanceErro <= 20) {
                    erroMudarNivel = true;
                }
            }
        };
    }

    public Runnable mostraMsgNoPainel() {
        return new Runnable() {
            @Override
            public void run() {
                int chanceErro = new Random().nextInt(0, 101);
                if (chanceErro <= 20) {
                    erroMsgNoTerminal = true;
                }
            }
        };
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {

                Map<Integer, Integer> dado = CentralDeControle.dados.get(0);
                if (dado != null) {
                    processar(dado);
                } else {
                    CentralDeControle.dados.remove(0);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IndexOutOfBoundsException ignore) {
            }
        }
    }
}
