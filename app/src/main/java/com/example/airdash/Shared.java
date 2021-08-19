//====================================================================
//
// Application: Air Dash simulation application
// Class:       Shared
// Description:
//   This app simulates meal deliveries by a fleet of drones
//
//====================================================================
package com.example.airdash;

//--------------------------------------------------------------------
// enum Shared
//--------------------------------------------------------------------
public enum Shared
{
    //Define enum value
    Data;

    //Declare enum fields
    public int clockValue;
    public int orderQueuedValue;
    public int orderDeliveredValue;
    public int dronesAvailableValue = 100;
    public int dronesCrashesValue;
    public int orderRateValue;
    public int dronesFlyingValue;
    public int deliveryTimeValue;
}
