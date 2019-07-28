package assignment.first.rezga.wifidirectchat.view.chat;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import assignment.first.rezga.wifidirectchat.ChatContract;
import assignment.first.rezga.wifidirectchat.DisplayChatContract;
import assignment.first.rezga.wifidirectchat.R;

public class ChatViewHolder extends RecyclerView.ViewHolder implements ChatContract.CellHolder {



    private TextView messageText;
    private ConstraintLayout container;
    private ConstraintLayout parent;
    private ConstraintLayout timeContainer;

    private ChatContract.ChatPresenter presenter;



    public ChatViewHolder(@NonNull final View itemView, ChatContract.ChatPresenter presenter) {
        super(itemView);

        messageText = itemView.findViewById(R.id.message_text);
        container = itemView.findViewById(R.id.container);

        parent = itemView.findViewById(R.id.parent);
        timeContainer = itemView.findViewById(R.id.time_container);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintSet set = new ConstraintSet();
                //set.clone(parent);
                set.connect(R.id.time_container,ConstraintSet.END,R.id.parent,ConstraintSet.END,80);
                //TransitionManager.beginDelayedTransition(parent);
                set.applyTo(timeContainer);
                Log.i("AAAA","123");
            }
        });

        this.presenter = presenter;
    }


    @Override
    public void setMessage(String text) {
        messageText.setText(text);
    }

}
