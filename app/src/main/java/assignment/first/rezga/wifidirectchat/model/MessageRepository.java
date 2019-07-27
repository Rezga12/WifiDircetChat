package assignment.first.rezga.wifidirectchat.model;


import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class MessageRepository {

    public interface DevicePostHandler{
        void handleResponse(List<DeviceInfo> devices);
    }

    public interface MessagePostHandler{
        void handleResponse(List<Message> messages);
    }

    private static MessageDao dao;



    public MessageRepository(){

        MessageBase db = MessageBase.getInstance();

        dao = db.messageDao();
    }

    public void insertDevice(Device device){


        new InsertDeviceTask().execute(device);
    }

    public void insertMessage(Message message){
        new InsertMessageTask().execute(message);
    }

    public void loadAllDevices(DevicePostHandler handler){
        new LoadDevicesTask(handler).execute();
    }

    public void loadAllMessages(String peerAddr, MessagePostHandler handler){
        new LoadMessagesTask(handler).execute(peerAddr);
    }

    public void deleteAllMessages(){
        new DeleteAllTask().execute();
    }

    public void deleteMessagesTask(String peerAddr){
        new DeleteMessagesTask().execute(peerAddr);
    }

    public void deleteDeviceTask(String peerAddr){
        new DeleteDevicesTask().execute(peerAddr);
    }

    private static class InsertDeviceTask extends AsyncTask<Device,Void,Void>{

        @Override
        protected Void doInBackground(Device... devices) {
            if(devices != null && devices.length > 0){
                dao.insertDevice(devices[0]);
            }


            return null;
        }
    }

    private static class InsertMessageTask extends AsyncTask<Message,Void,Void>{

        @Override
        protected Void doInBackground(Message... messages) {
            if(messages != null && messages.length > 0){
                dao.insertMessage(messages[0]);
            }


            return null;
        }
    }



    private static class LoadDevicesTask extends  AsyncTask<Void,Void,List<DeviceInfo>>{



        DevicePostHandler handler;

        public LoadDevicesTask(DevicePostHandler handler){
            super();
            this.handler = handler;
        }

        @Override
        protected List<DeviceInfo> doInBackground(Void... voids) {

            List<DeviceInfo> infos = dao.getDeviceInfos();
            //Log.i("AAAA","size:  " + infos.size() +infos.get(0).name + " " + infos.get(0).messageNum +  " ommg " + infos.get(0).lastDate + " lel");


            return infos;



        }

        @Override
        protected void onPostExecute(List<DeviceInfo> devices) {
            super.onPostExecute(devices);

            handler.handleResponse(devices);

        }
    }

    private static class LoadMessagesTask extends AsyncTask<String,Void,List<Message>>{


        MessagePostHandler handler;

        public LoadMessagesTask(MessagePostHandler handler){
            super();
            this.handler = handler;
        }


        @Override
        protected List<Message> doInBackground(String... ids) {
            if (ids != null && ids.length != 0) {
                return dao.loadMessages(ids[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            super.onPostExecute(messages);

            handler.handleResponse(messages);

        }
    }

    private static class DeleteAllTask extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            dao.deleteAllDevices();
            dao.deleteAllMessages();

            return null;
        }
    }

    private static class DeleteMessagesTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {

            if(strings!=null && strings.length > 0){
                dao.deleteMessages(strings[0]);
            }

            return null;
        }
    }


    private static class DeleteDevicesTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {

            if(strings!=null && strings.length > 0){
                dao.deleteDevice(strings[0]);
            }

            return null;
        }
    }




}
