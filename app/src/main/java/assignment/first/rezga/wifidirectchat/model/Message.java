package assignment.first.rezga.wifidirectchat.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Messages")
public class Message {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String peerAddress;
    public String message;

    public boolean isOwnMessage;

    public Date sendTime;


}
