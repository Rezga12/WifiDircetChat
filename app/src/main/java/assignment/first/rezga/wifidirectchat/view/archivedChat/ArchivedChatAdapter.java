package assignment.first.rezga.wifidirectchat.view.archivedChat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import assignment.first.rezga.wifidirectchat.DisplayChatContract;
import assignment.first.rezga.wifidirectchat.R;

public class ArchivedChatAdapter extends RecyclerView.Adapter<ArchivedChatViewHolder> {

    private DisplayChatContract.DisplayChatPresenter presenter;

    private static final int SELF_MESSAGE = 1;
    private static final int OTHER_MESSAGE = 2;

    public ArchivedChatAdapter(DisplayChatContract.DisplayChatPresenter presenter){
        super();
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ArchivedChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == SELF_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat_self,parent,false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat_other,parent,false);
        }

        return new ArchivedChatViewHolder(view,presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ArchivedChatViewHolder holder, int position) {
        presenter.onBindViewHOlderAtPosition(position,holder);
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
