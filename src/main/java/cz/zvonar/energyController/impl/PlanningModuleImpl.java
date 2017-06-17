package cz.zvonar.energyController.impl;

import cz.zvonar.energyController.data.CurrentConsumption;
import cz.zvonar.energyController.data.CurrentProduction;
import cz.zvonar.energyController.interfaces.IDeviceManagementInterface;
import cz.zvonar.energyController.interfaces.IPlanningModule;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author z003nc3m
 */
public class PlanningModuleImpl implements IPlanningModule {

    @Override
    public void replan(CurrentProduction production, CurrentConsumption consumption, List<IDeviceManagementInterface> devices) {
        if (nightCurrent()) {
            turnOn(devices);
        } else {
            for (IDeviceManagementInterface device : devices) {
                if ((production.getTotalProduction() > (consumption.getTotalConsumption() + device.getMaximalConsumption() + 1000))) {
                    turnOn(device);
                } else if ((production.getTotalProduction() + 500) < consumption.getTotalConsumption()) {
                    turnOff(device);
                }
            }
        }
    }

    private boolean nightCurrent() {
        DateTime dt = new DateTime();
        int hour = dt.getHourOfDay();
        return ((hour >= 15) && (hour < 18));
    }

    private void turnOn(List<IDeviceManagementInterface> devices) {
        for (IDeviceManagementInterface device : devices) {
            turnOn(device);
        }
    }

    private void turnOn(IDeviceManagementInterface device) {
        device.start(100);
    }
    
    private void turnOff(IDeviceManagementInterface device) {
        device.killSoftly();
    }
}
