package assignment.first.rezga.wifidirectchat.view.listpeersrecycler;

import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import assignment.first.rezga.wifidirectchat.AvailablePeersContract;
import assignment.first.rezga.wifidirectchat.R;

public class PeersViewHolder extends RecyclerView.ViewHolder implements AvailablePeersContract.PeersListHolder {


    private TextView phoneNameField;
    private TextView phoneAddressField;

    private AvailablePeersContract.AvailablePeersPresenter presenter;

    public PeersViewHolder(@NonNull View itemView, final AvailablePeersContract.AvailablePeersPresenter presenter) {
        super(itemView);

        phoneNameField = itemView.findViewById(R.id.history_name2);
        phoneAddressField = itemView.findViewById(R.id.peer_address);

        this.presenter = presenter;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeersViewHolder.this.presenter.onCellClicked(getAdapterPosition());
            }
        });

        ImageView broom_right = itemView.findViewById(R.id.wifi_icon);
        broom_right.setColorFilter(R.color.default_font_color,PorterDuff.Mode.SRC_ATOP);
    }


    @Override
    public void setPhoneName(String phoneName) {
        phoneNameField.setText(phoneName);
    }

    @Override
    public void setPeerAddress(String peerAddress) {
        phoneAddressField.setText(peerAddress);
    }
}
