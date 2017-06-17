/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zvonar.energyController.data;

/**
 *
 * @author z003nc3m
 */
public class CurrentConsumption {
   private int consumptionL1;
   private int consumptionL2;
   private int consumptionL3;

    public int getConsumptionL1() {
        return consumptionL1;
    }

    public void setConsumptionL1(int consumptionL1) {
        this.consumptionL1 = consumptionL1;
    }

    public int getConsumptionL2() {
        return consumptionL2;
    }

    public void setConsumptionL2(int consumptionL2) {
        this.consumptionL2 = consumptionL2;
    }

    public int getConsumptionL3() {
        return consumptionL3;
    }

    public void setConsumptionL3(int consumptionL3) {
        this.consumptionL3 = consumptionL3;
    }

    public int getTotalConsumption() {
        return consumptionL1 + consumptionL2 + consumptionL3;
    }

 
   
   
}
