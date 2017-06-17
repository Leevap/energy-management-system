/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zvonar.energyController.impl;

import cz.zvonar.energyController.interfaces.IDeviceManagementInterface;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z003nc3m
 */
public class HeatingPumpInterface implements IDeviceManagementInterface {
    boolean isLow;
    float minimalAbstractValue = 20;
    float maximalAbstractValue = 24;
    
    
    @Override
    public float getActualAbstractValue() {
        URL url;
        try {
            url = new URL("http://192.168.1.31:80/temp");
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
    public int getMaximalConsumption() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean start(int percentage) {
        URL url;
        try {
            url = new URL("http://192.168.1.31:80/start/" + percentage);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Scanner scanner = new Scanner(con.getInputStream());
            isLow = Boolean.valueOf(scanner.nextLine()); 
        } catch (MalformedURLException ex) {
            Logger.getLogger(BoylerInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BoylerInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return isLow;
    }

    @Override
    public boolean kill() {
        URL url;
        try {
            url = new URL("http://192.168.1.31:80/stop/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Scanner scanner = new Scanner(con.getInputStream());
            isLow = Boolean.valueOf(scanner.nextLine());
        } catch (MalformedURLException ex) {
            Logger.getLogger(BoylerInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BoylerInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isLow;
    }

    @Override
    public boolean killSoftly() {
        return kill();
    }

    @Override
    public boolean isLow() {
        return isLow;
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
