package assignment.first.rezga.wifidirectchat.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import assignment.first.rezga.wifidirectchat.MainContract;
import assignment.first.rezga.wifidirectchat.R;

public class PopDevice extends DialogFragment {
    private MainContract.HistoryPresenter presenter;

    private Button back;
    private Button clear;

    private TextView title;
    private String mac;

    private TextView explanation;

    public static PopDevice newInstance(String name, String mac,MainContract.HistoryPresenter presenter) {
        PopDevice frag = new PopDevice();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("mac", mac);
        frag.setArguments(args);
        frag.presenter = presenter;

        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getDialog().getWindow() != null){
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }


        return inflater.inflate(R.layout.fragment_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        back = view.findViewById(R.id.back);
        clear = view.findViewById(R.id.clear);
        title = view.findViewById(R.id.dialog_title);
        explanation = view.findViewById(R.id.explanation);
        explanation.setText("All messages with given device will be permanently removed.");

        if(getArguments() != null){
            String name = getArguments().getString("name", "");
            title.setText("Remove Conversation With " + name + "?");
            mac = getArguments().getString("mac", "");
        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.removeConversation(mac);
                presenter.loadData();
                dismiss();
            }
        });

        clear.setText("Remove");

    }
}
