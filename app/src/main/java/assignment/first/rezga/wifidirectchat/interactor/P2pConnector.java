package assignment.first.rezga.wifidirectchat.interactor;

import android.content.Context;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import assignment.first.rezga.wifidirectchat.AvailablePeersContract;
import assignment.first.rezga.wifidirectchat.MainActivity;

public class P2pConnector implements AvailablePeersContract.PeerListConnector {


    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    private FragmentActivity activity;

    private AvailablePeersContract.AvailablePeersPresenter presenter;
    private String PeerName;
    private WifiP2pManager.ConnectionInfoListener connectionInfoListener;


    public P2pConnector(AvailablePeersContract.AvailablePeersPresenter presenter, FragmentActivity activity){

        this.presenter = presenter;
        this.activity = activity;

        manager = (WifiP2pManager)activity.getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(activity, activity.getMainLooper(), null);
    }

    @Override
    public void connectToPeer(final String peerAddr, final String peerName) {


        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = peerAddr;
        config.wps.setup = WpsInfo.PBC;
        P2pConnector.this.PeerName = peerName;



        manager.connect(channel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                //presenter.onSuccessfulConnection(peerAddr,peerName);
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.getContext(), "Connect failed. Retry.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void requestConnectionInfo(String peerName, String peerAddr) {
        connectionInfoListener = new MyConnectionInfoListener(peerName, peerAddr);
        manager.requestConnectionInfo(channel, connectionInfoListener);
    }


    private class MyConnectionInfoListener implements WifiP2pManager.ConnectionInfoListener {
        private String peerName;
        private String peerAddr;

        public MyConnectionInfoListener(String peerName, String peerAddr){
            this.peerAddr = peerAddr;
            this.peerName = peerName;
        }

        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            // InetAddress from WifiP2pInfo struct.


//            //Log.i("AAAA",groupOwnerAddress);
//            // After the group negotiation, we can determine the group owner
//            // (server).
//            if (info.groupFormed && info.isGroupOwner) {
//                final String groupOwnerAddress =  info.groupOwnerAddress.getHostAddress();
//                // Do whatever tasks are specific to the group owner.
//                // One common case is creating a group owner thread and accepting
//                // incoming connections.
//                //new MainActivity.FileServerAsyncTask(activity).execute();
//                //
//                //
//                Toast.makeText(MainActivity.getContext(),"Owner " + groupOwnerAddress,Toast.LENGTH_LONG).show();
//                //Log.i("AAAA","owner");
//
//                presenter.onSuccessfulConnection(peerName,peerAddr,info.isGroupOwner);
//
//
//
//            } else
//
            if (info.groupFormed) {
                presenter.onSuccessfulConnection(peerName, info.groupOwnerAddress.getHostAddress(),info.isGroupOwner, peerAddr);
                Log.i("AAAA","owner: " + info.groupOwnerAddress);
                Log.i("AAAA", "peer " + peerAddr);
            }


        }
    }
}
