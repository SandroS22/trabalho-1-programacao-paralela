package com.github.sandros22;

import java.util.Random;

public class Sensor {

    public Sensor() {

    }

    public Integer enviarLeitura(){
        // Valor superior é exclusivo
        return new Random().nextInt(0,1001);
    }
}
