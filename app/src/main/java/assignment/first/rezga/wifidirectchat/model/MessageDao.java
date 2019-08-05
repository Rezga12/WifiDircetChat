package assignment.first.rezga.wifidirectchat.model;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDevice(Device device);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMessage(Message message);

    @Query("select * from devices")
    List<Device> loadAllDevices();


    @Query("select * from messages where peerAddress = :peerAddr order by sendTime desc")
    List<Message> loadMessages(String peerAddr);

    @Query("delete from messages where peerAddress = :peerAddr")
    int deleteMessages(String peerAddr);

    @Query("delete from devices where mac = :macAddr")
    int deleteDevice(String macAddr);

    @Query("delete from messages")
    int deleteAllMessages();

    @Query("delete from devices")
    int deleteAllDevices();

    @Query("select devices.mac as mac, devices.name as name, count(*) as messageNum,max(sendTime) as lastDate " +
            "from devices left join messages " +
            "on devices.mac = messages.peerAddress " +
            "group by devices.mac")
    List<DeviceInfo> getDeviceInfos();
}
