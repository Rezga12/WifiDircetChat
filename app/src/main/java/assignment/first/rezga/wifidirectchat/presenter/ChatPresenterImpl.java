package assignment.first.rezga.wifidirectchat.presenter;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import assignment.first.rezga.wifidirectchat.ChatContract;
import assignment.first.rezga.wifidirectchat.DisplayChatContract;
import assignment.first.rezga.wifidirectchat.interactor.SocketCommunicator;
import assignment.first.rezga.wifidirectchat.model.Message;
import assignment.first.rezga.wifidirectchat.model.MessageRepository;
import assignment.first.rezga.wifidirectchat.view.ChatActivity;

public class ChatPresenterImpl implements ChatContract.ChatPresenter {
    private ChatContract.ChatView view;
    private ChatContract.ChatCommunicator communicator;
    private MessageRepository repo;

    private String peerAddr ="";
    private boolean isOwner;


    private List<Message> messages = new ArrayList<>();

    public ChatPresenterImpl(ChatContract.ChatView view,String peerAddr, boolean isOnwer){
        this.view = view;
        repo = new MessageRepository();
        this.communicator = new SocketCommunicator(this);

        this.peerAddr = peerAddr;
        this.isOwner = isOnwer;

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
        Log.i("AAAA", "load messages");
        Toast.makeText(ChatActivity.getContext(), "loaded message", Toast.LENGTH_LONG).show();
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

//        repo.insertMessage(message);

        view.clearMessageField();


//        loadMessages();

        communicator.sendMessage(message.message);

        Log.i("AAAA", "send to communicator: " + message.message);
    }

    @Override
    public void setDeviceAddress(String addr) {
        peerAddr = addr;
    }

    @Override
    public void messageReceived(String message) {

    }


    @Override
    public void onCommunicationEstsablished() {

    }

    @Override
    public void cleanConnections() {

    }

    @Override
    public void waitForConnectionAndStart() {
        communicator.startCommunication(peerAddr,isOwner);
        view.showLoadingBar();
    }

    @Override
    public void closeConnection() {
        communicator.closeConnection();
    }
}