package assignment.first.rezga.wifidirectchat.view.archivedChat;


import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import assignment.first.rezga.wifidirectchat.DisplayChatContract;
import assignment.first.rezga.wifidirectchat.R;
import assignment.first.rezga.wifidirectchat.view.ArchivedChatActivity;

public class ArchivedChatViewHolder extends RecyclerView.ViewHolder implements DisplayChatContract.ChatCellHolder {

    private TextView messageText;
    private ConstraintLayout container;
    private ConstraintLayout parent;
    private ConstraintLayout timeContainer;

    private ConstraintLayout root;

    private DisplayChatContract.DisplayChatPresenter presenter;

    private boolean showtime = false;

    public ArchivedChatViewHolder(@NonNull final View itemView, DisplayChatContract.DisplayChatPresenter presenter, final ArchivedChatActivity activity, boolean isSelf) {
        super(itemView);

        messageText = itemView.findViewById(R.id.message_text);
        container = itemView.findViewById(R.id.container);

        //parent = itemView.findViewById(R.id.parent);


        root = itemView.findViewById(R.id.parent);

        final ConstraintSet set1 = new ConstraintSet();
        final ConstraintSet set2 = new ConstraintSet();
        if(isSelf){
            set1.clone(itemView.getContext(),R.layout.cell_chat_self);
            set2.clone(itemView.getContext(),R.layout.cell_chat_self_showtime);
        }else{
            set1.clone(itemView.getContext(),R.layout.cell_chat_other);
            set2.clone(itemView.getContext(),R.layout.cell_chat_other_showtime);
        }



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(root);
                if(showtime){
                    set1.applyTo(root);

                }else{
                    Log.i("AAAA","showed message");
                    set2.applyTo(root);
                }
                showtime = !showtime;
            }
        });
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
