package assignment.first.rezga.wifidirectchat;

import java.util.List;

import assignment.first.rezga.wifidirectchat.model.Device;

public interface AvailablePeersContract {

    interface AvailablePeersPresenter{
        void loadPeerList();
        void updatePeerList(List<Device> device);

        int getPeerCount();
        void onBindViewHolderAtPosition(int position, PeersListHolder holder);

        void onCellClicked(int position);
        void onSuccessfulConnection(String peerName, String peerAddr, boolean isOwner);
        void startConnection();
        void savePeerInfo(String peerName, String peerAddr);
    }

    interface AvailablePeersView{
        void refreshPeerList();

        void navigateToChat(String name, String mac, boolean isOwner);
    }

    interface PeersListHolder{
        void setPhoneName(String phoneName);
        void setPeerAddress(String peerAddress);
    }

    interface PeerListConnector{
        void connectToPeer(final String peerAddr, final String peerName);
        void requestConnectionInfo();
    }

}
