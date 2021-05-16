package com.example.bigtext.adapter;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigtext.R;
import com.example.bigtext.model.Convo;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Convo> convoList;
    private int textSize;

    public RecyclerViewAdapter(Context context, List<Convo> convoList) {
        this.context = context;
        this.convoList = convoList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Convo convo = convoList.get(position);
//        holder.contactName.setText(contact.getName());
//        holder.phoneNumber.setText(contact.getPhonenumber());
        holder.convoText.setText(convo.getConvoText());
        holder.convoText.setTextSize(TypedValue.COMPLEX_UNIT_SP, convo.getConvoSize());
    }


    @Override
    public int getItemCount() {
        return convoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView convoText;
        // variable for text-msg-size

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            convoText = itemView.findViewById(R.id.message_text);
            // message size not yet implemented

        }

        @Override
        public void onClick(View v) {
            Log.d("ClickFromViewHolder", "Clicked");
        }

//        public void onTextSizeIncrement(int txtSize){
//            convoText.setTextSize(txtSize);
//        }
    }
}
