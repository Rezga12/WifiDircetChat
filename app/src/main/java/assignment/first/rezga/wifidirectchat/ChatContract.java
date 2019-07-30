package assignment.first.rezga.wifidirectchat;

import java.util.Date;
import java.util.List;

import assignment.first.rezga.wifidirectchat.model.Message;

public interface ChatContract {

    interface ChatPresenter{
        void onBackPressed();
        void onRemovePressed();
        int getMessageCount();
        void onBindViewHOlderAtPosition(int position, CellHolder holder);
        void loadMessages();
        void updateData(List<Message> data);
        boolean isSelfMessage(int position);
        void sendButtonClicked(String message);
        void setDeviceAddress(String addr);
    }

    interface ChatView{
        void setPhoneName(String phoneName);
        void setLastActiveDate(Date date);
        void navigateBackToMain();
        void refreshMessageList();
        void clearMessageField();
    }

    interface CellHolder{
        void setMessage(String text);
    }


}
