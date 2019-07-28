package assignment.first.rezga.wifidirectchat.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import assignment.first.rezga.wifidirectchat.ChatContract;
import assignment.first.rezga.wifidirectchat.DisplayChatContract;
import assignment.first.rezga.wifidirectchat.model.Message;
import assignment.first.rezga.wifidirectchat.model.MessageRepository;

public class ChatPresenterImpl implements ChatContract.ChatPresenter {
    private ChatContract.ChatView view;
    private MessageRepository repo;

    private String peerAddr ="";

    private List<Message> messages = new ArrayList<>();

    public ChatPresenterImpl(ChatContract.ChatView view){
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
    public void onBindViewHOlderAtPosition(int position, ChatContract.CellHolder holder) {
        holder.setMessage(messages.get(position).message);
    }


    @Override
    public void loadMessages() {
        repo.loadAllMessages(peerAddr, new MessageRepository.MessagePostHandler() {
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

    @Override
    public void sendButtonClicked(String messageText) {
        Message message = new Message();
        message.isOwnMessage = true;
        message.message = messageText;
        message.peerAddress = peerAddr;
        message.sendTime = new Date(System.currentTimeMillis());

        repo.insertMessage(message);

        view.clearMessageField();


        loadMessages();


    }

    @Override
    public void setDeviceAddress(String addr) {
        peerAddr = addr;
    }
}