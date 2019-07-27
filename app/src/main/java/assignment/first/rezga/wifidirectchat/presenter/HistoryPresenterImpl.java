package assignment.first.rezga.wifidirectchat.presenter;

import java.util.ArrayList;
import java.util.List;

import assignment.first.rezga.wifidirectchat.MainContract;
import assignment.first.rezga.wifidirectchat.model.DeviceInfo;
import assignment.first.rezga.wifidirectchat.model.MessageRepository;

public class HistoryPresenterImpl implements MainContract.HistoryPresenter {

    private MainContract.HistoryView view;

    private List<DeviceInfo>  devices = new ArrayList<>();

    public HistoryPresenterImpl(MainContract.HistoryView view){
        this.view = view;
    }

    @Override
    public void loadData() {
        MessageRepository repo = new MessageRepository();
        repo.loadAllDevices(new MessageRepository.DevicePostHandler() {
            @Override
            public void handleResponse(List<DeviceInfo> devices) {
                view.showDeviceList(devices);
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
        holder.setMessageCount(devices.get(i).messageNum);
        holder.setPhoneName(devices.get(i).name);
    }

    @Override
    public void onDeviceItemClicked(int i) {
        view.showDisplayedChat(devices.get(i).mac);
    }
}
