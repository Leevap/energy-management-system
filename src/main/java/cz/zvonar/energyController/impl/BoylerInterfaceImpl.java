package cz.zvonar.energyController.impl;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import cz.zvonar.energyController.interfaces.IDeviceManagementInterface;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z003nc3m
 */
public class BoylerInterfaceImpl implements IDeviceManagementInterface {

    private boolean debug;
    private static GpioPinDigitalOutput boyler;
    private int maximalConsumption = 2000;
    private float minimalAbstractValue = 40;
    private float maximalAbstractValue = 80;

    public BoylerInterfaceImpl(boolean debug) {
        this.debug = debug;
        final GpioController gpio = GpioFactory.getInstance();
        boyler = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, PinState.HIGH);
    }

    @Override
    public float getActualAbstractValue() {
        URL url;
        try {
            url = new URL("http://192.168.1.30:80/temp");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Scanner scanner = new Scanner(con.getInputStream());
            return Float.valueOf(scanner.nextLine());
        } catch (MalformedURLException ex) {
            Logger.getLogger(BoylerInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BoylerInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0L;
    }

    @Override
    public boolean start(int percentage) {
        if (debug) {
            System.out.println("Boyler is " + boyler.getState());
        }
        if (boyler.isHigh()) {
            System.out.println(new Date() + " --> Turning boyler ON");
            boyler.setState(PinState.LOW);
            return true;
        }
        return false;
    }

    @Override
    public boolean kill() {
        if (debug) {
            System.out.println("Boyler is " + boyler.getState());
        }

        if (boyler.isLow()) {
            System.out.println(new Date() + " --> Turning boyler OFF");
            boyler.setState(PinState.HIGH);
            return true;
        }
        return false;
    }

    @Override
    public boolean killSoftly() {
        return kill();
    }

    @Override
    public boolean isLow() {
        return boyler.isLow();
    }

    @Override
    public int getMaximalConsumption() {
        return maximalConsumption;
    }

    @Override
    public float getMinimalAbstractValue() {
        return minimalAbstractValue;
    }

    @Override
    public float getMaximalAbstractValue() {
        return maximalAbstractValue;
    }

}
