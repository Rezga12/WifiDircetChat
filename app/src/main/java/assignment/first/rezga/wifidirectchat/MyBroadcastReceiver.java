package assignment.first.rezga.wifidirectchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;


import assignment.first.rezga.wifidirectchat.interactor.P2pPeerListInteractor;
import assignment.first.rezga.wifidirectchat.view.ListPeersFragment;

public class MyBroadcastReceiver extends BroadcastReceiver {
    ListPeersFragment fragment;
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;

    private AvailablePeersContract.AvailablePeersPresenter presenter;
    private P2pPeerListInteractor interactor;

    public MyBroadcastReceiver(P2pPeerListInteractor interactor, WifiP2pManager manager, WifiP2pManager.Channel channel, ListPeersFragment fragment, AvailablePeersContract.AvailablePeersPresenter presenter){
        this.fragment = fragment;
        this.manager = manager;
        this.channel = channel;

        this.presenter = presenter;

        this.interactor = interactor;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        interactor.requestPeers();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            Log.i("AAAA","WIFI_P2P_STATE_CHANGED_ACTION");
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            Log.i("AAAA","peers changed");

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed! We should probably do something about
            // that.


            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {

                // We are connected with the other device, request connection
                // info to find group owner IP//manager.requestConnectionInfo(channel, connectionListener);
                presenter.startConnection();

            }


        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

            Log.i("AAAA","device changed");
        }
    }





}
