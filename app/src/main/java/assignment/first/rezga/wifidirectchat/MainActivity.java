package assignment.first.rezga.wifidirectchat;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import assignment.first.rezga.wifidirectchat.model.Device;
import assignment.first.rezga.wifidirectchat.model.Message;
import assignment.first.rezga.wifidirectchat.model.MessageRepository;
import assignment.first.rezga.wifidirectchat.presenter.HistoryPresenterImpl;
import assignment.first.rezga.wifidirectchat.view.ChatFragment;
import assignment.first.rezga.wifidirectchat.view.HistoryFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {




    private static Context context;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    public static  Context getContext(){
        return context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        context = getApplicationContext();



        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HistoryFragment frag = HistoryFragment.newInstance();
        fragmentTransaction.add(R.id.frame,frag);
        fragmentTransaction.commit();

        MessageRepository repo = new MessageRepository();
        final Message message = new Message();
        message.peerAddress ="asdasdas";
        message.sendTime = new Date(System.currentTimeMillis());
        message.message = "message";

        /*repo.insertMessage(message);
        repo.insertMessage(message);
        repo.insertMessage(message);
        repo.insertMessage(message);*/

//        repo.loadAllMessages("DDFF", new MessageRepository.MessagePostHandler() {
//            @Override
//            public void handleResponse(List<Message> messages) {
//                Log.i("AAAA",messages.size() + " ");
//            }
//        });
        //Device a = new Device();
        //a.mac ="asdasdasd";
        //a.name = "asdasdas";
        //repo.insertDevice(a);
//        repo.loadAllDevices(new MessageRepository.DevicePostHandler() {
//            @Override
//            public void handleResponse(List<Device> devices) {
//                Log.i("AAAA",devices.size() + " aasd");
//            }
//        });

        showDebugDBAddressLogToast(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.nav_home) {
            ChatFragment frag = ChatFragment.newInstance();
            fragmentTransaction.replace(R.id.frame,frag);

        } else if (id == R.id.nav_gallery) {
            HistoryFragment frag = HistoryFragment.newInstance();
            fragmentTransaction.replace(R.id.frame,frag);

        }
        fragmentTransaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void showDebugDBAddressLogToast(Context context) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);
                Toast.makeText(context, (String) value, Toast.LENGTH_LONG).show();
            } catch (Exception ignore) {

            }
        }
    }
}
