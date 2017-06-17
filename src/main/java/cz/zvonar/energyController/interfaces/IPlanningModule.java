/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zvonar.energyController.interfaces;

import cz.zvonar.energyController.data.CurrentConsumption;
import cz.zvonar.energyController.data.CurrentProduction;
import java.util.List;

/**
 *
 * @author z003nc3m
 */
public interface IPlanningModule {
    public void replan(CurrentProduction production, CurrentConsumption consumption, List<IDeviceManagementInterface> devices);
}
