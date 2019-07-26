package assignment.first.rezga.wifidirectchat.model;


import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class MessageRepository {

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

    public void loadAllDevices(LoadDevicesTask.PostHandler handler){
        new LoadDevicesTask(handler).execute();
    }

    public void loadAllMessages(String peerAddr, LoadMessagesTask.PostHandler handler){
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

    private static class LoadDevicesTask extends  AsyncTask<Void,Void,List<Device>>{

        public interface PostHandler{
            void handleResponse(List<Device> devices);
        }

        PostHandler handler;

        public LoadDevicesTask(PostHandler handler){
            super();
            this.handler = handler;
        }

        @Override
        protected List<Device> doInBackground(Void... voids) {

            return dao.loadAllDevices();



        }

        @Override
        protected void onPostExecute(List<Device> devices) {
            super.onPostExecute(devices);

            handler.handleResponse(devices);

        }
    }

    private static class LoadMessagesTask extends AsyncTask<String,Void,List<Message>>{

        public interface PostHandler{
            void handleResponse(List<Message> messages);
        }

        PostHandler handler;

        public LoadMessagesTask(PostHandler handler){
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
