package assignment.first.rezga.wifidirectchat;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import assignment.first.rezga.wifidirectchat.model.Device;
import assignment.first.rezga.wifidirectchat.view.ListPeersFragment;

public class MyBroadcastReceiver extends BroadcastReceiver {
    ListPeersFragment fragment;
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;

    private AvailablePeersContract.AvailablePeersPresenter presenter;

    public MyBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, ListPeersFragment fragment, AvailablePeersContract.AvailablePeersPresenter presenter){
        this.fragment = fragment;
        this.manager = manager;
        this.channel = channel;

        this.presenter = presenter;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (manager != null) {
            manager.requestPeers(channel, peerListListener);
        }

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            Log.i("AAAA","WIFI_P2P_STATE_CHANGED_ACTION");
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            Log.i("AAAA","peers changed");

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed! We should probably do something about
            // that.
            if (manager == null) {
                return;
            }




            Log.i("AAAA","connection changed");

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
//            DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
//                    .findFragmentById(R.id.frag_list);
//            fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
//                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
            Log.i("AAAA","device changed");
        }
    }




    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();


    public WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
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
                Toast.makeText(fragment.getActivity(),"Owner " + groupOwnerAddress,Toast.LENGTH_LONG).show();


            } else if (info.groupFormed) {
                // The other device acts as the peer (client). In this case,
                // you'll want to create a peer thread that connects
                // to the group owner.
                Toast.makeText(fragment.getActivity(),"Client " + groupOwnerAddress,Toast.LENGTH_LONG).show();


                new AsyncTask<Void,Void,Void>(){

                    @Override
                    protected Void doInBackground(Void... voids) {
                        Context context = fragment.getActivity().getApplicationContext();
                        String host;
                        int port;
                        int len;
                        Socket socket = new Socket();
                        byte buf[]  = new byte[1024];


                        try {
                            /**
                             * Create a client socket with the host,
                             * port, and timeout information.
                             */
                            socket.bind(null);

                            socket.connect((new InetSocketAddress(groupOwnerAddress, 8888)), 500);

                            /**
                             * Create a byte stream from a JPEG file and pipe it to the output stream
                             * of the socket. This data will be retrieved by the server device.
                             */
                            OutputStream outputStream = socket.getOutputStream();
                            ContentResolver cr = context.getContentResolver();

                            byte[] bytes = "sendingTXT".getBytes();
                            outputStream.write(bytes, 0,bytes.length);
                            Log.i("AAAA","sent text");
                            // Toast.makeText(context,"sent: " + "sendingTXT", Toast.LENGTH_LONG );
                            outputStream.close();
                        } catch (FileNotFoundException e) {
                            //catch logic
                        } catch (IOException e) {
                            //catch logic
                        }

/**
 * Clean up any open sockets when done
 * transferring or if an exception occurred.
 */              finally {
                            if (socket != null) {
                                if (socket.isConnected()) {
                                    try {
                                        socket.close();
                                    } catch (IOException e) {
                                        //catch logic
                                    }
                                }
                            }
                        }
                        return null;
                    }
                }.execute();
                //////////////////////////

            }
        }
    };


    public WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
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
