package assignment.first.rezga.wifidirectchat.view.historyrecycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import assignment.first.rezga.wifidirectchat.MainContract;
import assignment.first.rezga.wifidirectchat.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder implements MainContract.DeviceItemHolder {

    private TextView nameField;
    private TextView time;
    private TextView messageNum;

    private MainContract.HistoryPresenter presenter;

    public HistoryViewHolder(@NonNull View itemView, final MainContract.HistoryPresenter presenter) {
        super(itemView);

        this.presenter = presenter;

        nameField = itemView.findViewById(R.id.history_name);
        time = itemView.findViewById(R.id.history_time);
        messageNum = itemView.findViewById(R.id.history_message_num);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryViewHolder.this.presenter.onDeviceItemClicked(getAdapterPosition());
            }
        });

    }

    @Override
    public void setPhoneName(String name) {
        nameField.setText(name);
    }

    @Override
    public void setMessageCount(int messageCount) {
        messageNum.setText(String.valueOf(messageCount));
    }

    @Override
    public void setChatTime(Date date) {
        time.setText(date.toString());
    }
}