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
    public void requestConnectionInfo() {
        manager.requestConnectionInfo(channel, connectionInfoListener);
    }

    private WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            // InetAddress from WifiP2pInfo struct.
            final String groupOwnerAddress =  info.groupOwnerAddress.getHostAddress();

            Log.i("AAAA",groupOwnerAddress);
            // After the group negotiation, we can determine the group owner
            // (server).
            if (info.groupFormed && info.isGroupOwner) {
                // Do whatever tasks are specific to the group owner.
                // One common case is creating a group owner thread and accepting
                // incoming connections.
                //new MainActivity.FileServerAsyncTask(activity).execute();
                //
                //
                Toast.makeText(MainActivity.getContext(),"Owner " + groupOwnerAddress,Toast.LENGTH_LONG).show();
                //Log.i("AAAA","owner");




            } else if (info.groupFormed) {
                // The other device acts as the peer (client). In this case,
                // you'll want to create a peer thread that connects
                // to the group owner.
                Toast.makeText(MainActivity.getContext(),"Client " + groupOwnerAddress,Toast.LENGTH_LONG).show();
                //Log.i("AAAA","client");

            }

            presenter.onSuccessfulConnection("",groupOwnerAddress,info.isGroupOwner);
        }
    };
}
