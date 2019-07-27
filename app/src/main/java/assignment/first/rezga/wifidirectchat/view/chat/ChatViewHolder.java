package assignment.first.rezga.wifidirectchat.view.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import assignment.first.rezga.wifidirectchat.DisplayChatContract;
import assignment.first.rezga.wifidirectchat.R;

public class ChatViewHolder extends RecyclerView.ViewHolder implements DisplayChatContract.ChatCellHolder {

    private TextView messageText;
    private ConstraintLayout container;
    private ConstraintLayout parent;

    private DisplayChatContract.DisplayChatPresenter presenter;

    public ChatViewHolder(@NonNull View itemView, DisplayChatContract.DisplayChatPresenter presenter) {
        super(itemView);

        messageText = itemView.findViewById(R.id.message_text);
        container = itemView.findViewById(R.id.container);

        parent = itemView.findViewById(R.id.parent);
    }


    @Override
    public void setMessage(String text) {
        messageText.setText(text);
    }

    @Override
    public void setIsOwnerMessage(boolean isOwnerMessage) {

        ConstraintSet constraintSet = new ConstraintSet();
        ConstraintSet constraintSetText = new ConstraintSet();
        if(isOwnerMessage){

            constraintSet.connect(R.id.container,ConstraintSet.BOTTOM,R.id.parent,ConstraintSet.BOTTOM);
            constraintSet.connect(R.id.container,ConstraintSet.END,R.id.parent,ConstraintSet.END);
            constraintSet.connect(R.id.container,ConstraintSet.TOP,R.id.parent,ConstraintSet.TOP);

            constraintSetText.connect(R.id.message_text,ConstraintSet.BOTTOM,R.id.container,ConstraintSet.BOTTOM);
            constraintSetText.connect(R.id.message_text,ConstraintSet.END,R.id.container,ConstraintSet.END);
            constraintSetText.connect(R.id.message_text,ConstraintSet.TOP,R.id.container,ConstraintSet.TOP);

        }else{

            constraintSet.connect(R.id.container,ConstraintSet.BOTTOM,R.id.parent,ConstraintSet.BOTTOM);
            constraintSet.connect(R.id.container,ConstraintSet.START,R.id.parent,ConstraintSet.START);
            constraintSet.connect(R.id.container,ConstraintSet.TOP,R.id.parent,ConstraintSet.TOP);

            constraintSetText.connect(R.id.message_text,ConstraintSet.BOTTOM,R.id.container,ConstraintSet.BOTTOM);
            constraintSetText.connect(R.id.message_text,ConstraintSet.START,R.id.container,ConstraintSet.START);
            constraintSetText.connect(R.id.message_text,ConstraintSet.TOP,R.id.container,ConstraintSet.TOP);
        }

        constraintSet.applyTo(container);
    }
}
