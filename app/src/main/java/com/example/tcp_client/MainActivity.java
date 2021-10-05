package com.example.tcp_client;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public TextView text;
    public TcpClient mTcpClient;
    public EditText editTextIP;
    public EditText editTextPORT;
    public ToggleButton toggleButton;
    public  int connect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button 初始化
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(this);
        text=(TextView) findViewById(R.id.textView3);
        editTextIP = findViewById(R.id.editTextTextPersonName5);
        editTextPORT = findViewById(R.id.editTextTextPersonName2);
        toggleButton = findViewById(R.id.toggleButton);

    }
    public void ontoggle(View v)
    {

        int inT = toggleButton.isChecked()?1:0;
        String s =String.valueOf(inT);
        Log.d("s","s");
        if (mTcpClient != null) {
            mTcpClient.sendMessage(s);
        }
    }
       @Override
    public void onClick(View v) {
        if(connect==0){
            mTcpClient.SERVER_IP = editTextIP.getText().toString();
            mTcpClient.SERVER_PORT =  Integer.valueOf(editTextPORT.getText().toString());
            connect= 1;
            new ConnectTask().execute("");
            text.setText("連線成功");
        }

           Log.d("s","s");
        //text.setText("大丈夫生於亂世，當帶三尺劍立不世功");//設定文字內容
        //text.setTextColor(Color.parseColor("#ff5e9cff"));//設定顏色
        //text.setTextSize(30);;//設定字型大小

        if (mTcpClient != null) {
           mTcpClient.sendMessage("testing");
        }
    }


    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            //process server response here....
            text.setText("response from server: " + values[0]);
        }


    }


}