package com.ping.noahbutler.ping.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ping.noahbutler.ping.Network.Sender;
import com.ping.noahbutler.ping.R;

/**
 * Created by Noah Butler on 8/20/2015.
 */
public class SignUpFragment extends Fragment {

    private Button confirmButton;
    private Button backButton;

    private EditText usernameInput;
    private EditText passwordInput;

    private String username;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View masterView = inflater.inflate(R.layout.fragment_signup, container, false);

        confirmButton = (Button)masterView.findViewById(R.id.signup_button);
        backButton = (Button)masterView.findViewById(R.id.signup_back_button);

        usernameInput = (EditText)masterView.findViewById(R.id.username_input);
        passwordInput = (EditText)masterView.findViewById(R.id.password_input);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();

                if(username == "" || password == "") {
                    Log.e("INVALID", "Enter a password or username you dumb fuck");
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Sender sender = new Sender();
                            String[] params = new String[3];
                            params[0] = "newUser";
                            params[1] = username;
                            params[2] = password;
                            sender.execute(params);
                        }
                    }).start();

                }
            }
        });

        return masterView;
    }
}
