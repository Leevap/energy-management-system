package cz.zvonar.energyController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import cz.zvonar.energyController.dao.ProductionConsumptionDAO;
import cz.zvonar.energyController.data.CurrentConsumption;
import cz.zvonar.energyController.data.CurrentProduction;
import cz.zvonar.energyController.impl.BoylerInterfaceImpl;
import cz.zvonar.energyController.impl.ConsumptionMeasurementInterfaceImpl;
import cz.zvonar.energyController.impl.EnergySourceInterfaceImpl;
import cz.zvonar.energyController.impl.PlanningModuleImpl;
import cz.zvonar.energyController.interfaces.IConsumptionMeasurementInterface;
import cz.zvonar.energyController.interfaces.IDeviceManagementInterface;
import cz.zvonar.energyController.interfaces.IEnergySourceInterface;
import cz.zvonar.energyController.interfaces.IPlanningModule;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;

/**
 *
 * @author z003nc3m
 */
public class Starter {
    
    private static ProductionConsumptionDAO productionConsumptionDao;
    private static IEnergySourceInterface energySourceInterface;
    private static IConsumptionMeasurementInterface consumptionMeasurementInterface;
    private static List<IDeviceManagementInterface> managedDevices;
    private static IPlanningModule planningModule;
    private static boolean debug = false;
    
    
    public static void main(String[] args) throws InterruptedException {
        init();
        if (args.length > 0 && args[0].equals("debug")) {
            debug = true;
        }
        System.out.println(new Date() + " Power plant Controller at Your service");
        // keep program running until user aborts (CTRL-C)
        while (true) {
            try {
                CurrentConsumption consumption = consumptionMeasurementInterface.getCurrentConsumption();
                CurrentProduction production = energySourceInterface.getCurrentProduction();
                productionConsumptionDao.saveData(managedDevices, production, consumption);
                planningModule.replan(production, consumption, managedDevices);
                Thread.sleep(1000 * 60);
            } catch (Exception ex) {
                System.out.println(new Date() + " Exception raised by keep-alive loop: " + ex.getMessage());
            }
        }
    }
   
    private static void init(){
        productionConsumptionDao = new ProductionConsumptionDAO();
        energySourceInterface = new EnergySourceInterfaceImpl(debug);
        consumptionMeasurementInterface = new ConsumptionMeasurementInterfaceImpl(debug);
        managedDevices = new ArrayList<IDeviceManagementInterface>();
        managedDevices.add(new BoylerInterfaceImpl(debug));
        planningModule = new PlanningModuleImpl();
                
    }
}
