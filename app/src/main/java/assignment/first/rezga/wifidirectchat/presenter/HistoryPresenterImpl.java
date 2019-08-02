package assignment.first.rezga.wifidirectchat.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import assignment.first.rezga.wifidirectchat.MainContract;
import assignment.first.rezga.wifidirectchat.model.DeviceInfo;
import assignment.first.rezga.wifidirectchat.model.MessageRepository;

public class HistoryPresenterImpl implements MainContract.HistoryPresenter {

    private MainContract.HistoryView view;

    private List<DeviceInfo>  devices = new ArrayList<>();
    private MessageRepository repo;

    public HistoryPresenterImpl(MainContract.HistoryView view){
        this.view = view;
        repo = new MessageRepository();
    }

    @Override
    public void loadData() {

        repo.loadAllDevices(new MessageRepository.DevicePostHandler() {
            @Override
            public void handleResponse(List<DeviceInfo> devices) {
                Log.i("AAAA","size: " + devices.size());
                for(DeviceInfo device : devices){
                    Log.i("AAAA",device.name + " " + device.messageNum + device.lastDate);
                }
                updateData(devices);
                view.setEmptyMessageVisible(devices.size() == 0);
            }
        });
    }

    @Override
    public int getSavedDevicesCount() {
        return devices.size();
    }

    @Override
    public void updateData(List<DeviceInfo> devices) {
        this.devices = devices;
        view.refreshDeviceList();
    }

    @Override
    public void onBindViewHolderAtPosition(int i, MainContract.DeviceItemHolder holder) {
        holder.setChatTime(devices.get(i).lastDate);
        if(devices.get(i).lastDate != null){
            holder.setMessageCount(devices.get(i).messageNum);
        }else{
            holder.setMessageCount(0);
        }

        holder.setPhoneName(devices.get(i).name);
        holder.setPeerAddress(devices.get(i).mac);
    }

    @Override
    public void onDeviceItemClicked(int i) {
        DeviceInfo device = devices.get(i);
        view.showDisplayedChat(device.mac,0,device.name);
    }

    @Override
    public void onClearButtonPressed() {
        view.showPopup();
    }

    @Override
    public void clearHistory() {
        repo.deleteAllMessages();
    }

    @Override
    public void onDeviceItemLongClick(String name, String peerAddr) {
        view.shopDevicePopUp(name, peerAddr);
    }

    @Override
    public void removeConversation(String mac) {
        repo.deleteMessage(mac);
        repo.deleteDevice(mac);
    }


}
