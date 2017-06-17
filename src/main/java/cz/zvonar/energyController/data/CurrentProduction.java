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
public class CurrentProduction {
    private int productionL1;
    private int productionL2;
    private int productionL3;

    public int getProductionL1() {
        return productionL1;
    }

    public void setProductionL1(int productionL1) {
        this.productionL1 = productionL1;
    }

    public int getProductionL2() {
        return productionL2;
    }

    public void setProductionL2(int productionL2) {
        this.productionL2 = productionL2;
    }

    public int getProductionL3() {
        return productionL3;
    }

    public void setProductionL3(int productionL3) {
        this.productionL3 = productionL3;
    }
    
    public int getTotalProduction() {
        return productionL1 + productionL2 + productionL3;
    }    
    
}
