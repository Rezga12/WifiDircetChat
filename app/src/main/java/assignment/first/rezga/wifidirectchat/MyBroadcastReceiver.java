package assignment.first.rezga.wifidirectchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import assignment.first.rezga.wifidirectchat.interactor.P2pPeerListInteractor;
import assignment.first.rezga.wifidirectchat.view.ListPeersFragment;

public class MyBroadcastReceiver extends BroadcastReceiver {
    ListPeersFragment fragment;


    private AvailablePeersContract.AvailablePeersPresenter presenter;
    private P2pPeerListInteractor interactor;

    public MyBroadcastReceiver(P2pPeerListInteractor interactor, ListPeersFragment fragment, AvailablePeersContract.AvailablePeersPresenter presenter){
        this.fragment = fragment;


        this.presenter = presenter;

        this.interactor = interactor;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();



        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert

            Log.i("AAAA","WIFI_P2P_STATE_CHANGED_ACTION");
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            //interactor.requestPeers();
            interactor.requestPeers();
            Log.i("AAAA","peers changed");

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed! We should probably do something about
            // that.


            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {

                // We are connected with the other device, request connection
                // info to find group owner IP//manager.requestConnectionInfo(channel, connectionListener);

                WifiP2pGroup group = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_GROUP);
                WifiP2pDevice owner =   group.getOwner();
                //WifiP2pDevice device = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);


                Collection<WifiP2pDevice> list = group.getClientList();
                String peerAddr = "";
                String peetName = "";
                if (list.size() == 0) {
                    peerAddr = owner.deviceAddress;
                    peetName = owner.deviceName;
                }else{
                    for (WifiP2pDevice dev : list){
                        peerAddr = dev.deviceAddress;
                        peetName = dev.deviceName;
                    }
                }

                presenter.startConnection(peetName, peerAddr);

            }


        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

            Log.i("AAAA","device changed");
        }
    }





}
