/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zvonar.energyController.interfaces;

import cz.zvonar.energyController.data.CurrentConsumption;

/**
 *
 * @author z003nc3m
 */
public interface IConsumptionMeasurementInterface {
    CurrentConsumption getCurrentConsumption();
    
}
