package assignment.first.rezga.wifidirectchat.view.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import assignment.first.rezga.wifidirectchat.ChatContract;
import assignment.first.rezga.wifidirectchat.R;
import assignment.first.rezga.wifidirectchat.view.archivedChat.ArchivedChatViewHolder;

public class ChatAdapter extends RecyclerView.Adapter<ArchivedChatViewHolder> {
    private ChatContract.ChatPresenter presenter;

    private static final int SELF_MESSAGE = 1;
    private static final int OTHER_MESSAGE = 2;

    public ChatAdapter(ChatContract.ChatPresenter presenter){
        super();
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ArchivedChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == SELF_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat_self,parent,false);
            return new ArchivedChatViewHolder(view,true,this);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat_other,parent,false);
            return new ArchivedChatViewHolder(view,false,this);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull ArchivedChatViewHolder holder, int position) {
        presenter.onBindViewHOlderAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getMessageCount();
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.isSelfMessage(position) ? SELF_MESSAGE : OTHER_MESSAGE;
    }
}
