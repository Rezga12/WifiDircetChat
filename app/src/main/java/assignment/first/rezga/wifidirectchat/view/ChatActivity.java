package assignment.first.rezga.wifidirectchat.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import assignment.first.rezga.wifidirectchat.ChatContract;
import assignment.first.rezga.wifidirectchat.Constants;
import assignment.first.rezga.wifidirectchat.DisplayChatContract;
import assignment.first.rezga.wifidirectchat.R;
import assignment.first.rezga.wifidirectchat.presenter.ChatPresenterImpl;
import assignment.first.rezga.wifidirectchat.presenter.DisplayChatPresenterImpl;
import assignment.first.rezga.wifidirectchat.view.archivedChat.ArchivedChatAdapter;
import assignment.first.rezga.wifidirectchat.view.chat.ChatAdapter;

public class ChatActivity extends AppCompatActivity implements ChatContract.ChatView {

    private RecyclerView recyclerView;
    private Button sendButton;
    private EditText editText;


    private ImageView backButton;
    private ImageView removeButton;
    private TextView phoneNameField;
    private TextView chatDateField;

    private ChatContract.ChatPresenter presenter;

    private ChatAdapter adapter;

    private static Context context;

    public static  Context getContext(){
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        editText = findViewById(R.id.newMessage);


        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.sendButtonClicked(editText.getText().toString());
            }
        });


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        context = this;

        Intent intent = getIntent();



        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        presenter = new ChatPresenterImpl(this);


        backButton = findViewById(R.id.chat_back_button);
        backButton.setColorFilter(getResources().getColor(R.color.default_font_color), PorterDuff.Mode.SRC_ATOP);

        removeButton = findViewById(R.id.chat_icon_trash);
        removeButton.setColorFilter(getResources().getColor(R.color.default_font_color), PorterDuff.Mode.SRC_ATOP);

        phoneNameField = findViewById(R.id.chat_phone_name);
        chatDateField = findViewById(R.id.chat_date);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackPressed();
            }
        });

        setPhoneName(intent.getStringExtra(Constants.PHONE_NAME_INTENT_KEY));
        Date date = new Date(intent.getLongExtra(Constants.CHAT_DATE_INTENT_KEY,0));
        setLastActiveDate(date);

        recyclerView = findViewById(R.id.chatView);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new ChatAdapter(presenter);
        recyclerView.setAdapter(adapter);



        presenter.setDeviceAddress(intent.getStringExtra(Constants.PEER_ADDR_INTENT_KEY));
        presenter.loadMessages();
    }

    @Override
    public void setPhoneName(String phoneName) {
        phoneNameField.setText(phoneName);
    }

    @Override
    public void setLastActiveDate(Date date) {
        //TODO: will have to format date properly here.

        chatDateField.setText(date.toString());
    }

    @Override
    public void navigateBackToMain() {
        onBackPressed();
    }

    @Override
    public void refreshMessageList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearMessageField() {
        editText.setText("");
    }
}
