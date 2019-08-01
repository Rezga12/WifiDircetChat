package assignment.first.rezga.wifidirectchat;

import java.net.InetAddress;
import java.util.List;

import assignment.first.rezga.wifidirectchat.model.Device;

public interface AvailablePeersContract {

    interface AvailablePeersPresenter{
        void loadPeerList();
        void updatePeerList(List<Device> device);

        int getPeerCount();
        void onBindViewHolderAtPosition(int position, PeersListHolder holder);

        void onCellClicked(int position);
        void onSuccessfulConnection(String peerName, String groupOwnerAddress, boolean isOwner, String peerMacAddr);
        void startConnection(String peerName, String peerAddr);
        void savePeerInfo(String peerName, String peerAddr);

       // void onSuccessfulConnection(String peerName, String groupOwnerAddress, boolean isGroupOwner,String peerAddr);
    }

    interface AvailablePeersView{
        void refreshPeerList();
        void showLoadingAnimation();
        void hideLoadingAnimation();
        void navigateToChat(String name, String mac, boolean isOwner, String peerMacAddr);
    }

    interface PeersListHolder{
        void setPhoneName(String phoneName);
        void setPeerAddress(String peerAddress);
    }

    interface PeerListConnector{
        void connectToPeer(final String peerAddr, final String peerName);
        void requestConnectionInfo(String peerName, String peerAddr);
    }

}
