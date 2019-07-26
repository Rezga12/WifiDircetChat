package assignment.first.rezga.wifidirectchat.model;





import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Devices")
public class Device {




    @NonNull
    @PrimaryKey
    public String mac;

    public String name;

}
