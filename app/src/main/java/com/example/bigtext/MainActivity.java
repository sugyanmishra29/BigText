package com.example.bigtext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bigtext.adapter.RecyclerViewAdapter;
import com.example.bigtext.data.MyDbHandler;
import com.example.bigtext.model.Convo;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Convo> convoArrayList;
    private ArrayAdapter<String> arrayAdapter;
    // private boolean flag = false;
    private int ticks = 0;
    private static int txtSize = 16; //default text size
    Convo convo = new Convo();

    public class BGprevDataLoader extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MyDbHandler db = new MyDbHandler(MainActivity.this);
            // the code snippet below will ensure that conversations are displayed as soon as the app is opened (like whatsapp)
            convoArrayList = new ArrayList<>();
            List<Convo> convoList = db.getAllConvos();

            for (Convo convos : convoList) {
                Log.d("dbSugyan", "\nId: " + convos.getId() + "\n" +
                        "Convo Text: " + convos.getConvoText() + "\n" +
                        "Convo Size: " + convos.getConvoSize() + "\n");
                convoArrayList.add(convos);
            }
            recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, convoArrayList);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.scrollToPosition(convoArrayList.size()-1);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BGprevDataLoader myTask1 = new BGprevDataLoader();
        myTask1.execute();

        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyDbHandler db = new MyDbHandler(MainActivity.this);

        EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);


        button.setOnTouchListener(new View.OnTouchListener() {
            CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // after each tick
                    // text size increase and reflect in recyclerView
                    ticks++;
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(40);
//                    Toast.makeText(MainActivity.this, "Ticks = " + ticks, Toast.LENGTH_SHORT).show();
                    txtSize += 8*ticks;
                    convo.setConvoSize(txtSize);
                }

                @Override
                public void onFinish() {
                    // after countdown is over, textSize = max and reflect in recyclerView
                    Log.d("ticked", "countdown finished");

                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // start your timer
                    String item = editText.getText().toString();

                    if (item.equals("")) {
                        Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                        vb.vibrate(500);
                        Toast.makeText(MainActivity.this, "Please provide an input!", Toast.LENGTH_SHORT).show();

                    } else {

                        convo.setConvoText(item);
                        countDownTimer.start();

                        Log.v("ticking", "size raised by 8 and ticks = " + ticks + " and txtSize = " + txtSize);
//                        Toast.makeText(MainActivity.this, "countdown started & ticks is " + ticks + " & textsize is " + txtSize, Toast.LENGTH_SHORT).show();

//                        Log.v("txtSize", "txtSize is " + txtSize);
//                        convo.setConvoSize(txtSize);

                        convoArrayList = new ArrayList<>();

                        db.addConvo(convo);
                        List<Convo> convoList = db.getAllConvos();

                        for (Convo convos : convoList) {
                            Log.d("dbSugyan", "\nId: " + convos.getId() + "\n" +
                                    "Convo Text: " + convos.getConvoText() + "\n" +
                                    "Convo Size: " + convos.getConvoSize() + "\n");
                            convoArrayList.add(convos);
                        }
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // stop your timer.
                    // Use your recyclerView
                    Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(500);
//                    Toast.makeText(MainActivity.this, "Ending Countdown", Toast.LENGTH_SHORT).show();
                    countDownTimer.cancel();
                    recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, convoArrayList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.scrollToPosition(convoArrayList.size()-1);
                    Log.v("ticks", "ticks = " + ticks);
                    editText.setText("");  //after sending msg, msg box should become blank
                    txtSize = 16;
                    ticks=0;
                    Log.d("dbSugyan", "Bro you have " + db.getCount() + " conversations in your database");
//                    countDownTimer.onFinish();
                    db.close();

                }
                return false;
            }
        });

    }
}
// service class = press service chalu. action up service stop