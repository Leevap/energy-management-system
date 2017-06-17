/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zvonar.energyController.dao;

import cz.zvonar.energyController.data.CurrentConsumption;
import cz.zvonar.energyController.data.CurrentProduction;
import cz.zvonar.energyController.interfaces.IDeviceManagementInterface;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author z003nc3m
 */
public class ProductionConsumptionDAO {
        private static final DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("dd.MM HH:mm:ss");
        public void saveData(List<IDeviceManagementInterface> devices, CurrentProduction production, CurrentConsumption consumption) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        int devicesConsumption = 0;
        for (IDeviceManagementInterface device : devices) {
            devicesConsumption += (device.isLow() ? 2000 : 0);
        }        
        String outfile = "\"" + now.format(dataFormat) + "\"," + production + "," + consumption + "," + devicesConsumption + System.lineSeparator();
        Files.write(Paths.get("/var/www/html/ram/Data.csv"), outfile.getBytes(), StandardOpenOption.APPEND);
    }
    
}
