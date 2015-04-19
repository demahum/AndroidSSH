package com.example.muhamed.ssh_i;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class MainActivity extends ActionBarActivity {

    public int state = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startProgress(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                    doWork("linaro", "linaro", "192.168.10.6", 22);
                }
            };
        new Thread(runnable).start();
        }

    private void doWork(String username,
                        String password,
                        String hostname,
                        int port){

        try{
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, hostname, port);
            session.setPassword(password);

            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);

            session.connect();

            ChannelExec channelssh = (ChannelExec)
            session.openChannel("exec");

            if (state == 0){
                channelssh.setCommand("python upali.py");
                channelssh.connect();
                channelssh.disconnect();
                state = 1;}
            else if (state == 1){
                channelssh.setCommand("python ugasi.py");
                channelssh.connect();
                channelssh.disconnect();
                state = 0;
            }

        }catch (Exception e){
            Log.d("Error", "Exception: " + e.getMessage());
        }
    }
}
