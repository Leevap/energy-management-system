package cz.zvonar.energyController.impl;

import cz.zvonar.energyController.data.CurrentConsumption;
import cz.zvonar.energyController.data.CurrentProduction;
import cz.zvonar.energyController.interfaces.IDeviceManagementInterface;
import cz.zvonar.energyController.interfaces.IPlanningModule;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author z003nc3m
 */
public class ExtendedPlanningModuleImpl implements IPlanningModule {

    @Override
    public void replan(CurrentProduction production, CurrentConsumption consumption, List<IDeviceManagementInterface> devices) {
        addAllExhaustedDevices(devices);
        for (IDeviceManagementInterface device : devices) {
            if (production.getTotalProduction() > consumption.getTotalConsumption() + device.getMaximalConsumption()) {
                device.start(100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExtendedPlanningModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void addAllExhaustedDevices(List<IDeviceManagementInterface> devices) {
        for (IDeviceManagementInterface device : devices) {
            if (device.getMinimalAbstractValue() > device.getActualAbstractValue()) {
                device.start(100);
            }
        }
    }

}
