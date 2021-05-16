package com.example.bigtext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

//    public class BGrecyclerViewUpdation extends AsyncTask<Void, Void, Void>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, convoArrayList);
//            recyclerView.setAdapter(recyclerViewAdapter);
//            recyclerView.scrollToPosition(convoArrayList.size()-1);
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//            super.onPostExecute(unused);
//        }
//
//    }

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

//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Convo convo = new Convo();
//                String item = editText.getText().toString();
//                if (item.equals("")) {
//                    Toast.makeText(MainActivity.this, "Please provide an input!", Toast.LENGTH_SHORT).show();
//                } else {
//                    convo.setConvoText(item);
//                    db.addConvo(convo);
//
//                    convoArrayList = new ArrayList<>();
//
//                    List<Convo> convoList = db.getAllConvos();
//
//                    for (Convo convos : convoList) {
//
//                        Log.d("dbharry", "\nId: " + convos.getId() + "\n" +
//                                "Convo Text: " + convos.getConvoText() + "\n" +
//                                "Convo Size: " + convos.getConvoSize() + "\n");
//
//                        convoArrayList.add(convos);
//                    }
//
//                    // Use your recyclerView
//                    recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, convoArrayList);
//                    recyclerView.setAdapter(recyclerViewAdapter);
//                    recyclerView.scrollToPosition(convoArrayList.size()-1);
//                    editText.setText("");  //after sending msg, msg box should become blank
//
//                    Log.d("dbharry", "Bro you have " + db.getCount() + " conversations in your database");
//                }
//            }
//        })

        button.setOnTouchListener(new View.OnTouchListener() {
            int txtSize = 16; //default text size

            CountDownTimer countDownTimer = new CountDownTimer(10000,100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // after each tick
                    // text size increase and reflect in recyclerView
                    Vibrator vb = (Vibrator)   getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(20);
                    txtSize = 24;
                    Log.d("ticking", "size+8 done");
                }

                @Override
                public void onFinish() {
                    // after countdown is over, textSize = max and reflect in recyclerView
                    Log.d("ticked", "countdown finished");
//                    if (txtSize!=12) txtSize=12;
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // start your timer
                    //  txtSize++;
                    Convo convo = new Convo();
                    String item = editText.getText().toString();

                    if (item.equals("")) {
                        Vibrator vb = (Vibrator)   getSystemService(Context.VIBRATOR_SERVICE);
                        vb.vibrate(500);
                        Toast.makeText(MainActivity.this, "Please provide an input!", Toast.LENGTH_SHORT).show();
                    } else {
                        convo.setConvoText(item);
                        countDownTimer.start();
                        convo.setConvoSize(txtSize);

                        db.addConvo(convo);
                        convoArrayList = new ArrayList<>();
                        List<Convo> convoList = db.getAllConvos();

                        for (Convo convos : convoList) {
                            Log.d("dbSugyan", "\nId: " + convos.getId() + "\n" +
                                    "Convo Text: " + convos.getConvoText() + "\n" +
                                    "Convo Size: " + convos.getConvoSize() + "\n");
                            convoArrayList.add(convos);
                        }
                    }

//                    BGrecyclerViewUpdation myTask2 = new BGrecyclerViewUpdation();
//                    myTask2.execute();

                    // delay of 500ms before the size increase code
                    // 2 simultaneous processes - size increase due to button hold AND
                    // textsize increment in textView being reflected in recyclerView

//                    Handler h =new Handler();
//
//                    // 500 ms is the delayed time before execute the code inside the function
//                    // code inside function - to start setting new text size after the button is held for 500ms
//                    h.postDelayed(new Runnable() {
//                        public void run() {
//                            //put your code here
//
//                        }
//
//                    }, 500);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // stop your timer.
                    // Use your recyclerView
                    countDownTimer.cancel();
                    recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, convoArrayList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.scrollToPosition(convoArrayList.size()-1);
                    editText.setText("");  //after sending msg, msg box should become blank
                    Log.d("dbSugyan", "Bro you have " + db.getCount() + " conversations in your database");
//                    countDownTimer.onFinish();
                    db.close();
                }
                return false;
            }
        });

    }
}
