package assignment.first.rezga.wifidirectchat.presenter;

import java.util.ArrayList;
import java.util.List;

import assignment.first.rezga.wifidirectchat.DisplayChatContract;
import assignment.first.rezga.wifidirectchat.model.Message;
import assignment.first.rezga.wifidirectchat.model.MessageRepository;

public class DisplayChatPresenterImpl implements DisplayChatContract.DisplayChatPresenter {

    private DisplayChatContract.DisplayChatView view;
    private MessageRepository repo;

    private List<Message> messages = new ArrayList<>();

    public DisplayChatPresenterImpl(DisplayChatContract.DisplayChatView view){
        this.view = view;
        repo = new MessageRepository();
    }

    @Override
    public void onBackPressed() {
        view.navigateBackToMain();
    }

    @Override
    public void onRemovePressed() {

    }

    @Override
    public int getMessageCount() {
        return messages.size();
    }

    @Override
    public void onBindViewHOlderAtPosition(int position, DisplayChatContract.ChatCellHolder holder) {
        //holder.setIsOwnerMessage(messages.get(position).isOwnMessage);
        holder.setMessage(messages.get(position).message);
        //view.refreshMessageList();
    }

    @Override
    public void loadMessages(String deviceAddr) {
        repo.loadAllMessages(deviceAddr, new MessageRepository.MessagePostHandler() {
            @Override
            public void handleResponse(List<Message> messages) {
                updateData(messages);
            }
        });
    }

    @Override
    public void updateData(List<Message> data) {
        messages = data;
        view.refreshMessageList();
    }

    @Override
    public boolean isSelfMessage(int position) {
        return messages.get(position).isOwnMessage;
    }
}
