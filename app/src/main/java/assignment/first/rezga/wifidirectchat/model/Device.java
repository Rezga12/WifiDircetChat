package assignment.first.rezga.wifidirectchat.model;





import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

@Entity(tableName = "Devices")
public class Device {

    public Device(){

    }

    public Device(String mac, String name){
        this.mac = mac;
        this.name = name;
    }

    @NonNull
    @PrimaryKey
    public String mac;

    public String name;

}
