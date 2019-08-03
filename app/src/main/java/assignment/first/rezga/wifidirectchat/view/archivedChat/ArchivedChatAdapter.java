package assignment.first.rezga.wifidirectchat.view.archivedChat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import assignment.first.rezga.wifidirectchat.DisplayChatContract;
import assignment.first.rezga.wifidirectchat.R;
import assignment.first.rezga.wifidirectchat.view.ArchivedChatActivity;

public class ArchivedChatAdapter extends RecyclerView.Adapter<ArchivedChatViewHolder> {

    private DisplayChatContract.DisplayChatPresenter presenter;

    private static final int SELF_MESSAGE = 1;
    private static final int OTHER_MESSAGE = 2;

    private ArchivedChatActivity activity;

    public ArchivedChatAdapter(DisplayChatContract.DisplayChatPresenter presenter, ArchivedChatActivity activity){
        super();
        this.presenter = presenter;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ArchivedChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        boolean isSelf = false;
        if(viewType == SELF_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat_self,parent,false);
            isSelf = true;
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat_other,parent,false);
        }

        return new ArchivedChatViewHolder(view,isSelf,this);
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
