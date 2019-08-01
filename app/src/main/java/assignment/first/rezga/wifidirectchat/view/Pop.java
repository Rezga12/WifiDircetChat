package assignment.first.rezga.wifidirectchat.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import assignment.first.rezga.wifidirectchat.MainContract;
import assignment.first.rezga.wifidirectchat.R;

public class Pop extends DialogFragment {

    private MainContract.HistoryPresenter presenter;

    private Button back;
    private Button clear;

    public static Pop newInstance(String title, MainContract.HistoryPresenter presenter) {
        Pop frag = new Pop();
        Bundle args = new Bundle();
        args.putString("title", title);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clearHistory();
                presenter.loadData();
                dismiss();
            }
        });

    }
}
