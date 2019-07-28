package assignment.first.rezga.wifidirectchat;

import java.util.List;

import assignment.first.rezga.wifidirectchat.model.Device;
import assignment.first.rezga.wifidirectchat.model.DeviceInfo;

public interface AvailablePeersContract {

    interface AvailablePeersPresenter{
        void loadPeerList();
        void updatePeerList(List<Device> device);

        int getPeerCount();
        void onBindViewHolderAtPosition(int position, PeersListHolder holder);

        void onCellClicked(int position);
    }

    interface AvailablePeersView{
        void refreshPeerList();

        void navigateToChat(String name, String mac);
    }

    interface PeersListHolder{
        void setPhoneName(String phoneName);
        void setPeerAddress(String peerAddress);
    }

}
