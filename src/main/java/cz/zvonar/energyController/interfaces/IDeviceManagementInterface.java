/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zvonar.energyController.interfaces;

/**
 *
 * @author z003nc3m
 */
public interface IDeviceManagementInterface {
    float getActualAbstractValue();
    int getMaximalConsumption();
    boolean start(int percentage);
    boolean kill();
    boolean killSoftly();
    boolean isLow();
    float getMinimalAbstractValue();
    float getMaximalAbstractValue();
}
