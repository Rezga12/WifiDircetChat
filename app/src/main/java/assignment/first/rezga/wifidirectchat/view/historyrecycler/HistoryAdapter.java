package assignment.first.rezga.wifidirectchat.view.historyrecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import assignment.first.rezga.wifidirectchat.MainContract;

import assignment.first.rezga.wifidirectchat.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {



    private MainContract.HistoryPresenter presenter;

    public HistoryAdapter(MainContract.HistoryPresenter presenter){
        super();
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_history,parent,false);
        return new HistoryViewHolder(view,presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        //holder.setData(data.get(position));
        presenter.onBindViewHolderAtPosition(position,holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getSavedDevicesCount();
    }

}
