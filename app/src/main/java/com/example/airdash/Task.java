//====================================================================
//
// Application: Air Dash simulation application
// Class:       Task
// Description:
//   This app simulates meal deliveries by a fleet of drones
//
//====================================================================
package com.example.airdash;

// Import packages
import java.util.Random;
import java.util.TimerTask;

//--------------------------------------------------------------------
// class Task
//--------------------------------------------------------------------
public class Task extends TimerTask
{

    //----------------------------------------------------------------
    // run
    //----------------------------------------------------------------
    public void run()
    {

        //Update shared value
        Shared.Data.clockValue = Shared.Data.clockValue + 1;

        if(((Shared.Data.clockValue) % Shared.Data.deliveryTimeValue) == 0 )
        {
            Shared.Data.orderDeliveredValue = Shared.Data.orderDeliveredValue + Shared.Data.dronesFlyingValue;

            Shared.Data.orderQueuedValue = Shared.Data.orderQueuedValue + Shared.Data.orderRateValue - Shared.Data.dronesFlyingValue;
            if(Shared.Data.orderQueuedValue < 0)
                Shared.Data.orderQueuedValue = 0;
        }
        else
            Shared.Data.orderQueuedValue = Shared.Data.orderQueuedValue + Shared.Data.orderRateValue;

        if(Shared.Data.dronesFlyingValue > (int)(Math.random()*100))
        {
            Shared.Data.dronesAvailableValue = Shared.Data.dronesAvailableValue - 1;
            Shared.Data.dronesCrashesValue = Shared.Data.dronesCrashesValue + 1;
        }

        //Test if message sent
        if(ActMain.timerTaskHandler.sendEmptyMessage(0))
            System.out.println("[Task] Timer task " + "message sent to main thread.");
        else
            System.out.println("[Task] Error: " + "timer task message NOT sent to main thread.");

    }
}
