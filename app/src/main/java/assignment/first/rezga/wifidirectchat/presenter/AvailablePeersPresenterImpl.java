package assignment.first.rezga.wifidirectchat.presenter;

import java.util.ArrayList;
import java.util.List;

import assignment.first.rezga.wifidirectchat.AvailablePeersContract;
import assignment.first.rezga.wifidirectchat.model.Device;

public class AvailablePeersPresenterImpl implements AvailablePeersContract.AvailablePeersPresenter {

    private AvailablePeersContract.AvailablePeersView view;

    private List<Device> devices = new ArrayList<>();


    public AvailablePeersPresenterImpl(AvailablePeersContract.AvailablePeersView view){

        this.view = view;

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
        view.navigateToChat(devices.get(position).name,devices.get(position).mac);
    }
}
