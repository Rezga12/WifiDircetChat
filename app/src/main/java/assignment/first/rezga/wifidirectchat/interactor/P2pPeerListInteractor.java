package assignment.first.rezga.wifidirectchat.interactor;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import assignment.first.rezga.wifidirectchat.AvailablePeersContract;
import assignment.first.rezga.wifidirectchat.model.Device;

public class P2pPeerListInteractor {

    private AvailablePeersContract.AvailablePeersPresenter presenter;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    public P2pPeerListInteractor(AvailablePeersContract.AvailablePeersPresenter presenter, FragmentActivity activity){
        this.presenter = presenter;

        manager = (WifiP2pManager)activity.getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(activity, activity.getMainLooper(), null);

//        Log.d("AAAA", "trying to cancell connection");
//        manager.cancelConnect(channel, new WifiP2pManager.ActionListener() {
//            @Override
//            public void onSuccess() {
//                Log.d("AAAA", "cancel success");
//            }
//
//            @Override
//            public void onFailure(int reason) {
//                Log.d("AAAA", "cancel failure");
//            }
//        });






    }

    public void cancelConnections(){
//        manager.removeGroup(channel, new WifiP2pManager.ActionListener() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onFailure(int reason) {
//
//            }
//        });
    }

    public void initiateDiscovery(){
        Log.i("AAAA","dicovering..");
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {}

            @Override
            public void onFailure(int reasonCode) {}
        });
    }



    public void requestPeers(){

        manager.requestPeers(channel, peerListListener);


    }
    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {

            Collection<WifiP2pDevice> refreshedPeers = peerList.getDeviceList();
            List<Device> devices = new ArrayList<>();
            for(WifiP2pDevice device : refreshedPeers){
                Device dev = new Device();
                dev.mac = device.deviceAddress;
                dev.name = device.deviceName;
                devices.add(dev);
            }

            presenter.updatePeerList(devices);

            Log.i("AAAA",devices.size() +  " ");

            if (devices.size() == 0) {
                Log.d("AAAA", "No devices found");
            }
        }
    };





}
