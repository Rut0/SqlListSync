package com.rut0.sqllistsync;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView view;
    MessageCursorAdapter adapter;
    DbHelper helper;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (ListView) findViewById(R.id.listView);

        helper = new DbHelper(this);
        adapter = new MessageCursorAdapter(this, helper.getMessages());
        view.setAdapter(adapter);

        Button btn = (Button) findViewById(R.id.btn);
        editText = (EditText) findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString().trim();
                if (msg != null && msg.length() > 0) {
                    if (helper.addMessage("ME", msg)) {
                        updateMessage();
                    }
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 1000; i++) {
                    helper.addMessage("BOT", i + " hi");
                }
                updateMessage();
                for (int i = 1000; ; i++) {
                    helper.addMessage("BOT", i + " hi");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateMessage();
                }
            }
        }).start();

    }

    @SuppressLint("NewApi")
    public void updateMessage() {
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                adapter.swapCursor(helper.getMessages());
            } else {
                adapter.changeCursor(helper.getMessages());
            }
            view.post(new Runnable() {
                @Override
                public void run() {
                    // Select the last row so it will scroll into view...
                    view.setSelection(adapter.getCount() - 1);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateMessage();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
