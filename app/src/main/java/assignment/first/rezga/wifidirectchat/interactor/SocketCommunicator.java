package assignment.first.rezga.wifidirectchat.interactor;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import assignment.first.rezga.wifidirectchat.ChatContract;
import assignment.first.rezga.wifidirectchat.view.ChatActivity;

public class SocketCommunicator implements ChatContract.ChatCommunicator {
    private  ChatContract.ChatPresenter presenter;

    private Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public SocketCommunicator(ChatContract.ChatPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void sendMessage(String message) {
        Log.i("AAAA","in communicator " + message);
        new SendingAsyncTask(socket,message).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void startCommunication(String address, boolean isOwner) {
        new CommunicateAsyncTask(isOwner,address,this,presenter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void closeConnection() {
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    private static class CommunicateAsyncTask extends AsyncTask<Void,String,Void> {
        private final ChatContract.ChatPresenter presenter;
        private boolean isOwner;
        private String address;
        private SocketCommunicator communicator;


        CommunicateAsyncTask(boolean isOwner, String address, SocketCommunicator communicator, ChatContract.ChatPresenter presenter) {
            this.isOwner = isOwner;
            this.address = address;
            this.communicator = communicator;
            this.presenter = presenter;
        }

        @Override
        protected Void doInBackground(Void... voids) {


            Socket mySocket = null;
            ServerSocket serverSocket = null;
            if (isOwner) {


                try {
                    serverSocket = new ServerSocket(8888);
                    mySocket = serverSocket.accept();

//                    DataOutputStream outStream = new DataOutputStream(client.getOutputStream());
//                    publishProgress();
//
//                    outStream.writeUTF("rezoylezo\0");
//                    outStream.flush();


                } catch (IOException e) {
                    Log.e("AAAA",e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            } else {
                mySocket = new Socket();
                try {
                    mySocket.bind(null);
                    mySocket.connect((new InetSocketAddress(address, 8888)));
//                    publishProgress();
//
//                    DataInputStream dIn = new DataInputStream(socket.getInputStream());
//
//                    String str = dIn.readUTF();
//                    Log.i("AAAA","text: " + str);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            communicator.setSocket(mySocket);
            //presenter.loadMessages();
            publishProgress("");

            while (true) {

                try {
                    if (mySocket == null || mySocket.isClosed()) {
                        break;
                    }
                    Log.i("AAAA","trying to receive : " +  mySocket.getLocalAddress() + " " + mySocket.getLocalSocketAddress() + " " + mySocket.getRemoteSocketAddress());

                    DataInputStream dIn = new DataInputStream(mySocket.getInputStream());
                    String str = dIn.readUTF();
                    Log.i("AAAA", "text: " + str);
                    publishProgress(str);

                } catch (IOException e) {
                    e.printStackTrace();


                    publishProgress("``lol``");

                    break;
                }
            }
            try {
                if(serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... messages) {
            super.onProgressUpdate(messages);

            if(messages[0].equals("``lol``")){
                presenter.onBackPressed();
                return;
            }

            if(messages[0].equals("")){
                //Toast.makeText(ChatActivity.getContext(), "sockets connected with yoeac other", Toast.LENGTH_LONG).show();
                presenter.loadMessages();
            }else{
                presenter.messageReceived(messages[0]);
                Log.i("AAAA","message recieved " + messages[0]);
            }


        }


//
//        @Override
//        protected void onPostExecute(Socket... sockets) {
////            super.onPostExecute(aVoid);
//
//        }
    }



    private static class SendingAsyncTask extends AsyncTask<Void,Void,Void>{

        private Socket socket;
        private String message;

        SendingAsyncTask(Socket socket, String message){
            this.socket = socket;
            this.message = message;
        }


        @Override
        protected Void doInBackground(Void... voids) {

            Log.i("AAAA","in doing background meeesage " + message);
            try {
                DataOutputStream outStream = new DataOutputStream(this.socket.getOutputStream());


                outStream.writeUTF(message);

                outStream.flush();
                Log.i("AAAA","meeesage " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
