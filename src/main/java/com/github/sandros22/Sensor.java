package com.github.sandros22;

import java.util.Random;

public class Sensor {

    public Sensor() {

    }

    public Runnable receberLeitura() {
        // Valor superior é exclusivo
        while (true) {
            new Random().nextInt(0, 1001);
        }
    }
}
