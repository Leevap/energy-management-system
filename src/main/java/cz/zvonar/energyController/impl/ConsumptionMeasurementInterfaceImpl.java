/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zvonar.energyController.impl;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import cz.zvonar.energyController.data.CurrentConsumption;
import cz.zvonar.energyController.interfaces.IConsumptionMeasurementInterface;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author z003nc3m
 */
public class ConsumptionMeasurementInterfaceImpl implements IConsumptionMeasurementInterface {

    private final GpioPinDigitalInput elektromer;
    private int impulses = 0;
    private long lastImpulseTimestamp;
    private long currentImpulseTimestamp = System.currentTimeMillis();
    private final boolean debug;
    private double currentFlow;
    private CurrentConsumption currentConsumption;

    public ConsumptionMeasurementInterfaceImpl(boolean debug) {
        this.debug = debug;
        final GpioController gpio = GpioFactory.getInstance();
        elektromer = gpio.provisionDigitalInputPin(RaspiPin.GPIO_08);
        elektromer.setShutdownOptions(true);
        elektromer.addListener((GpioPinListenerDigital) (GpioPinDigitalStateChangeEvent event) -> {
            if (event.getState().equals(PinState.HIGH)) {
                ++impulses;
                lastImpulseTimestamp = currentImpulseTimestamp;
                currentImpulseTimestamp = System.currentTimeMillis();
                Long distance = currentImpulseTimestamp - lastImpulseTimestamp;
                this.currentFlow = 4500.0 / distance * 1000;
                if (debug) {
                    System.out.println(" --> Incrementing impulses to " + impulses + " current flow is: " + currentFlow);
                }
            }
        });

        final ScheduledExecutorService execService = Executors.newSingleThreadScheduledExecutor();
        Callable<Void> task = new Callable() {
            public Void call() {
                try {
                    currentConsumption.setConsumptionL1(impulses * 75); //kW 
                    impulses = 0;
                } finally {
                    // Schedule same task to run again (even if processing fails).
                    execService.schedule(this, 1, TimeUnit.MINUTES);
                }
                return null;
            }
        };
    }

    @Override
    public CurrentConsumption getCurrentConsumption() {
        return currentConsumption;
    }

}
