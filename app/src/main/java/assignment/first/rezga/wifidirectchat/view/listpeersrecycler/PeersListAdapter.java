package assignment.first.rezga.wifidirectchat.view.listpeersrecycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import assignment.first.rezga.wifidirectchat.AvailablePeersContract;
import assignment.first.rezga.wifidirectchat.R;

public class PeersListAdapter extends RecyclerView.Adapter<PeersViewHolder> {


    private AvailablePeersContract.AvailablePeersPresenter presenter;

    public PeersListAdapter(AvailablePeersContract.AvailablePeersPresenter presenter){
        this.presenter = presenter;
    }


    @NonNull
    @Override
    public PeersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_peer,parent,false);
        return new PeersViewHolder(view,presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull PeersViewHolder holder, int position) {
        presenter.onBindViewHolderAtPosition(position,holder);
    }

    @Override
    public int getItemCount() {
        //Log.i("AAAA","size returned "  + presenter.getPeerCount());
        return presenter.getPeerCount();
    }
}
