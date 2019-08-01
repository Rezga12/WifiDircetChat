package assignment.first.rezga.wifidirectchat;

import java.util.Date;
import java.util.List;

import assignment.first.rezga.wifidirectchat.model.Message;

public interface DisplayChatContract {

    interface DisplayChatPresenter{
        void onBackPressed();
        void onRemovePressed();
        int getMessageCount();
        void onBindViewHOlderAtPosition(int position, ChatCellHolder holder);
        void loadMessages(String deviceAddr);
        void updateData(List<Message> data);
        boolean isSelfMessage(int position);
    }

    interface DisplayChatView{

        void setPhoneName(String phoneName);
        void setLastActiveDate(Date date);
        void navigateBackToMain();
        void refreshMessageList();
    }

    interface ChatCellHolder{
        void setMessageTime(Date date);

        void setMessage(String text);
        void setIsOwnerMessage(boolean isOwnerMessage);
    }

}
