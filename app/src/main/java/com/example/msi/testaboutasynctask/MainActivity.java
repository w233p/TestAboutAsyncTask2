package com.example.msi.testaboutasynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView message;
    private Button open;
    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (TextView) findViewById(R.id.messageTv);
        open = (Button) findViewById(R.id.bt1);
        url = (EditText) findViewById(R.id.et1);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
    }

    private void connect() {
        MyAsyncTask myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute(url.getText().toString());
    }

    class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private static final String TAG = "MyAsyncTask";
        ProgressDialog mProgressDialog;

        public MyAsyncTask(Context context) {
            mProgressDialog = new ProgressDialog(context, 0);
            mProgressDialog.setButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
//                    finish();
                    Log.e(TAG, "onCancel");
                }
            });
            mProgressDialog.setCancelable(true);//返回键可取消dialog
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                int i = 0;
                while (i <= 100) {
                    publishProgress(i++);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "加载完成";
        }

        protected void onCancelled() {
            super.onCancelled();
        }

        protected void onPostExecute(String result) {
            message.setText(result);
        }

        protected void onPreExecute(){
            message.setText("开始加载");
        }

        protected void onProgressUpdate(Integer... value) {
            System.out.println("" + value[0]);
            message.setText("" + value[0]);
            mProgressDialog.setProgress(value[0]);
        }
    }
}
