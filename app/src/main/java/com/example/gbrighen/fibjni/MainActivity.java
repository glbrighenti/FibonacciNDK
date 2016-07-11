package com.example.gbrighen.fibjni;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("fib-jni");
    }

    private ProgressDialog pd;

    public native long fibJni(long n);

    private long resultFib = 0;
    private long startTime, stopTime, diffTimeJava, diffTimeNative;
    private long n = 0;
    TextView textResult;
    TextView textTimeFibJava;
    TextView textTimeFibJni;
    EditText textNumberToFib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //init ui
        textTimeFibJava = (TextView) findViewById(R.id.text_time_java);
        textTimeFibJni = (TextView) findViewById(R.id.text_time_native);
        textNumberToFib = (EditText) findViewById(R.id.text_number_fib);
        textResult = (TextView) findViewById(R.id.text_result);

        pd = new ProgressDialog(this);
        pd.setTitle(R.string.dialog_title);
        pd.setMessage(getString(R.string.dialog_message));
        pd.setIndeterminate(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = Long.parseLong(textNumberToFib.getText().toString());
                if (n <= 0) {
                    Snackbar.make(view, R.string.input_error, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    startFibCalculations(n-1);
                }


            }
        });
    }


    private long fibJava(long n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 1;
        }
        return fibJava(n - 1) + fibJava(n - 2);
    }


    private void startFibCalculations(long n) {


        new AsyncTask<Long, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd.show();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                View view = findViewById(android.R.id.content);

                Snackbar.make(view,  String.format(getString(R.string.message_success),(Math.abs(diffTimeJava - diffTimeNative))), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                textTimeFibJava.setText("" + diffTimeJava + "ms");
                textTimeFibJni.setText("" + diffTimeNative + "ms");
                textResult.setText("" + resultFib);
                pd.dismiss();
            }

            @Override
            protected Void doInBackground(Long... params) {
                //java fib
                Long n = params[0];
                startTime = System.currentTimeMillis();
                resultFib = fibJava(n);
                stopTime = System.currentTimeMillis();
                diffTimeJava = ((stopTime - startTime));

                //native fib
                startTime = System.currentTimeMillis();
                resultFib = fibJni(n);
                stopTime = System.currentTimeMillis();
                diffTimeNative = ((stopTime - startTime));
                return null;
            }


        }.execute(n);
    }

}
