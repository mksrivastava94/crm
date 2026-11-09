package com.crm.erp.enumeration;

public enum UnitType {
    KG("Kg"),METER("Meter"),LITER("liter"),INCH("Inch"),FEET("Feet");

    private String unit;

    UnitType(String unitType){
        this.unit=unitType;
    }
}
