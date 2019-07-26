package assignment.first.rezga.wifidirectchat.model;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import assignment.first.rezga.wifidirectchat.MainActivity;


@Database(entities = {Message.class,Device.class},version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MessageBase extends RoomDatabase {


    public abstract MessageDao messageDao();


    private static final String DATABASE_NAME = "message_db";
    private static MessageBase INSTANCE;

    private static final Object lock = new Object();

    public static MessageBase getInstance(){
        synchronized (lock){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                        MainActivity.getContext(),
                        MessageBase.class,
                        DATABASE_NAME)
                        .build();
            }
        }
        return INSTANCE;
    }

}
