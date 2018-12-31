package com.example.filip.schoolproject;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import com.example.filip.schoolproject.Activity.MainActivity;


public class WIFIdirectBroadcastReceiver extends BroadcastReceiver{

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;

    public WIFIdirectBroadcastReceiver(WifiP2pManager mManager, WifiP2pManager.Channel mChannel, MainActivity mActivity) {
        this.mActivity=mActivity;
        this.mChannel=mChannel;
        this.mManager = mManager;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
       String action=intent.getAction();

       if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
           //check to see if wifi is enabled and notify appropriate activity
           int state= intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1);

           if(state==WifiP2pManager.WIFI_P2P_STATE_ENABLED){
               Toast.makeText(context,"WIFI is ON",Toast.LENGTH_SHORT).show();
                }else {
                        Toast.makeText(context,"WIFI is OFF",Toast.LENGTH_SHORT).show();
                }
       }else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){
           //call wifiP2pManager.requestPeers() get a list of current peers
            }else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){
           //respond to new connection or disconnections
                }else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){
           //respond to this device s wifi state changing
       }
    }


}