package assignment.first.rezga.wifidirectchat.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import assignment.first.rezga.wifidirectchat.AvailablePeersContract;
import assignment.first.rezga.wifidirectchat.Constants;
import assignment.first.rezga.wifidirectchat.MyBroadcastReceiver;
import assignment.first.rezga.wifidirectchat.R;
import assignment.first.rezga.wifidirectchat.presenter.AvailablePeersPresenterImpl;
import assignment.first.rezga.wifidirectchat.view.listpeersrecycler.PeersListAdapter;

public class ListPeersFragment extends Fragment implements AvailablePeersContract.AvailablePeersView {



    private WifiP2pManager.Channel channel;
    private WifiP2pManager manager;
    private MyBroadcastReceiver receiver;

    private final IntentFilter intentFilter = new IntentFilter();
    private AvailablePeersContract.AvailablePeersPresenter presenter;

    private PeersListAdapter adapter;
    private RecyclerView recyclerView;



    public static ListPeersFragment newInstance() {

        Bundle args = new Bundle();

        ListPeersFragment fragment = new ListPeersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);

        this.presenter = new AvailablePeersPresenterImpl(this);

        requestPermissions();

        setUpIntentFilter();

        recyclerView = view.findViewById(R.id.peer_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PeersListAdapter(presenter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void setUpIntentFilter(){
        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        //???? NULL ptr exception
        manager = (WifiP2pManager)getActivity().getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(getContext(), getActivity().getMainLooper(), null);

        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {}

            @Override
            public void onFailure(int reasonCode) {}
        });
    }

    private void requestPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity() != null) {
            int selfPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            } else {
                // presenter.loadDir(null);
            }
        }else{
            Log.d("mari"," Build.VERSION.SDK_INT < Build.VERSION_CODES.M");
        }
    }



    public void onResume() {
        super.onResume();
        Log.i("AAAA","onResume");
        FragmentActivity activity = getActivity();
        if (activity!= null){
            Log.i("AAAA","activity not null");
            receiver = new MyBroadcastReceiver(manager, channel, this,presenter);
            activity.registerReceiver(receiver, intentFilter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("AAAA","onPause");
        FragmentActivity activity = getActivity();
        if (activity!= null){
            //receiver = new MyBroadcastReceiver(manager, channel, this,presenter);
            activity.unregisterReceiver(receiver);
        }
    }

    @Override
    public void refreshPeerList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToChat(String name, String mac) {
        Intent intent = new Intent(getContext(),ChatActivity.class);
        intent.putExtra(Constants.PHONE_NAME_INTENT_KEY,name);
        intent.putExtra(Constants.PEER_ADDR_INTENT_KEY,mac);
        if(getActivity() != null){
            getActivity().startActivity(intent);
        }

    }
}
