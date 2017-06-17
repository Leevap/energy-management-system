/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zvonar.energyController.impl;

import cz.zvonar.energyController.data.CurrentProduction;
import cz.zvonar.energyController.data.HealthStatus;
import cz.zvonar.energyController.interfaces.IEnergySourceInterface;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;

/**
 *
 * @author z003nc3m
 */
public class EnergySourceInterfaceImpl implements IEnergySourceInterface {

    boolean debug;
    CurrentProduction production;
    private int productionFailcounter;
    URL solarURL;

    public EnergySourceInterfaceImpl(boolean debug) {
        init();
        this.debug = debug;
    }

    public EnergySourceInterfaceImpl() {
        init();
    }

    private void init() {
        try {
            this.solarURL = new URL("http://192.168.1.150:80/min_cur.js");
        } catch (MalformedURLException ex) {
            Logger.getLogger(EnergySourceInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public CurrentProduction getCurrentProduction() {
        DateTime dt = new DateTime();
        int hour = dt.getHourOfDay();
        if (debug) {
            System.out.println(" Current hour is " + hour);
        }
        if ((hour >= 21) || (hour <= 6)) {
            production.setProductionL1(0);
            return production;
        }
        if (debug) {
            System.out.println(new Date() + " Requesting solar system for production.");
        }
        try {
            HttpURLConnection con = (HttpURLConnection) solarURL.openConnection();
            Scanner scanner = new Scanner(con.getInputStream());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("var Pac=")) {
                    String[] split = line.split("=");
                    production.setProductionL1(Integer.valueOf(split[1]));
                    productionFailcounter = 0;
                }
            }
        } catch (Exception ex) {
            productionFailcounter++;
            System.out.println("Unable to get production, attempt " + productionFailcounter + ". Is power plant down?");
            if (productionFailcounter == 60) {
                System.out.println("Unable to contact Power plant for 1 hour. Setting production to 0!");
                production.setProductionL1(0);
            }
        }
        return production;
    }

    @Override
    public HealthStatus getHealthStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
