package assignment.first.rezga.wifidirectchat.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import assignment.first.rezga.wifidirectchat.Constants;
import assignment.first.rezga.wifidirectchat.MainActivity;
import assignment.first.rezga.wifidirectchat.MainContract;
import assignment.first.rezga.wifidirectchat.R;
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

    private Button clearHistoryBUtton;
    private TextView historyEmpty;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history,container,false);

        presenter = new HistoryPresenterImpl(this);



        recyclerView = view.findViewById(R.id.history_rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoryAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(15));

        Message mes = new Message();

        //MainActivity.showDebugDBAddressLogToast();
        presenter.loadData();

        MainActivity.showDebugDBAddressLogToast(MainActivity.getContext());

        clearHistoryBUtton = view.findViewById(R.id.clear_container);
        clearHistoryBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });

        historyEmpty = view.findViewById(R.id.history_empty);

        return view;
    }




    @Override
    public void showDisplayedChat(String peerAddr, long date, String phoneName) {
        Intent intent = new Intent(getActivity(), ArchivedChatActivity.class);
        intent.putExtra(Constants.PEER_ADDR_INTENT_KEY,peerAddr);
        intent.putExtra(Constants.CHAT_DATE_INTENT_KEY,date);
        intent.putExtra(Constants.PHONE_NAME_INTENT_KEY,phoneName);
        startActivity(intent);
    }

    @Override
    public void refreshDeviceList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showPopup() {
        if(getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Pop editNameDialogFragment = Pop.newInstance("", presenter);
            editNameDialogFragment.show(fm, "fragment_dialog");

        }
    }

    @Override
    public void shopDevicePopUp(String name, String peerAddr) {
        if(getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            PopDevice editNameDialogFragment = PopDevice.newInstance(name,peerAddr, presenter);
            editNameDialogFragment.show(fm, "fragment_dialog");

        }
    }

    @Override
    public void setEmptyMessageVisible(boolean val){
        if(!val){
            historyEmpty.setVisibility(View.INVISIBLE);
            clearHistoryBUtton.setVisibility(View.VISIBLE);
        }else{
            historyEmpty.setVisibility(View.VISIBLE);
            clearHistoryBUtton.setVisibility(View.INVISIBLE);
        }

    }
}
