package assignment.first.rezga.wifidirectchat;

import java.util.Date;
import java.util.List;

import assignment.first.rezga.wifidirectchat.model.DeviceInfo;

public interface MainContract  {

    interface HistoryPresenter{
        void loadData();
        int getSavedDevicesCount();
        void updateData(List<DeviceInfo> devices);
        void onBindViewHolderAtPosition(int position, DeviceItemHolder holder);

        void onDeviceItemClicked(int position);
        void onClearButtonPressed();

        void clearHistory();

        void onDeviceItemLongClick(String name, String peerAddr);

        void removeConversation(String mac);
    }

    interface HistoryView{
        //void showDeviceList(List<DeviceInfo> data);
        void showDisplayedChat(String peerAddr, long date, String phoneName);
        void refreshDeviceList();
        void showPopup();

        void shopDevicePopUp(String name, String peerAddr);
    }

    interface DeviceItemHolder{
        void setPeerAddress(String address);

        void setPhoneName(String name);
        void setMessageCount(int messageCount);
        void setChatTime(Date date);
    }

}
