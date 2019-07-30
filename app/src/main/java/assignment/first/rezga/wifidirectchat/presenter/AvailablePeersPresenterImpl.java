package assignment.first.rezga.wifidirectchat.presenter;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import assignment.first.rezga.wifidirectchat.AvailablePeersContract;
import assignment.first.rezga.wifidirectchat.interactor.P2pConnector;
import assignment.first.rezga.wifidirectchat.model.Device;

public class AvailablePeersPresenterImpl implements AvailablePeersContract.AvailablePeersPresenter {

    private AvailablePeersContract.AvailablePeersView view;

    private List<Device> devices = new ArrayList<>();
    private AvailablePeersContract.PeerListConnector connector;


    public AvailablePeersPresenterImpl(AvailablePeersContract.AvailablePeersView view, FragmentActivity activity){

        this.view = view;
        this.connector = new P2pConnector(this,activity);
    }

    @Override
    public void loadPeerList() {

    }

    @Override
    public void updatePeerList(List<Device> devices) {
        this.devices = devices;
        view.refreshPeerList();
    }

    @Override
    public int getPeerCount() {
        return devices.size();
    }

    @Override
    public void onBindViewHolderAtPosition(int position, AvailablePeersContract.PeersListHolder holder) {
        holder.setPeerAddress(devices.get(position).mac);
        holder.setPhoneName(devices.get(position).name);


    }

    @Override
    public void onCellClicked(int position) {

        connector.connectToPeer(devices.get(position).mac,devices.get(position).name);
    }

    @Override
    public void onSuccessfulConnection(String peerName, String peerAddr, boolean isOwner) {
        view.navigateToChat(peerName,peerAddr,isOwner);
    }

    @Override
    public void startConnection() {
        connector.requestConnectionInfo();
    }



    @Override
    public void savePeerInfo(String peerName, String peerAddr) {

    }
}
