package assignment.first.rezga.wifidirectchat.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import assignment.first.rezga.wifidirectchat.Constants;
import assignment.first.rezga.wifidirectchat.MainActivity;
import assignment.first.rezga.wifidirectchat.MainContract;
import assignment.first.rezga.wifidirectchat.R;
import assignment.first.rezga.wifidirectchat.model.Device;
import assignment.first.rezga.wifidirectchat.model.DeviceInfo;
import assignment.first.rezga.wifidirectchat.model.Message;
import assignment.first.rezga.wifidirectchat.presenter.HistoryPresenterImpl;
import assignment.first.rezga.wifidirectchat.view.historyrecycler.HistoryAdapter;

public class HistoryFragment extends Fragment implements  MainContract.HistoryView{



    public static HistoryFragment newInstance() {

        Bundle args = new Bundle();

        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private MainContract.HistoryPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history,container,false);

        presenter = new HistoryPresenterImpl(this);

        recyclerView = view.findViewById(R.id.history_rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoryAdapter(presenter);
        recyclerView.setAdapter(adapter);


        Message mes = new Message();

        //MainActivity.showDebugDBAddressLogToast();
        presenter.loadData();



        return view;
    }




    @Override
    public void showDisplayedChat(String peerAddr, long date, String phoneName) {
        Intent intent = new Intent(getActivity(),DisplayChatActivity.class);
        intent.putExtra(Constants.PEER_ADDR_INTENT_KEY,peerAddr);
        intent.putExtra(Constants.CHAT_DATE_INTENT_KEY,date);
        intent.putExtra(Constants.PHONE_NAME_INTENT_KEY,phoneName);
        startActivity(intent);
    }

    @Override
    public void refreshDeviceList() {
        adapter.notifyDataSetChanged();
    }
}
