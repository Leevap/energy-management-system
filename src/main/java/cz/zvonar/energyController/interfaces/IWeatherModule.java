/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zvonar.energyController.interfaces;

import java.sql.Timestamp;

/**
 *
 * @author z003nc3m
 */
public interface IWeatherModule {
    int sunIntensityForecast(Timestamp date);
}
