package com.example.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;
    private Activity activity;
//    Animation translate_anim;

    public CustomAdapter(Activity activity, Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.activity = activity;
    }

    //目的是創建並反回一個新的MyViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //指定活動的頁面
        LayoutInflater inflater = LayoutInflater.from(context);
        //指定RecyclerView的layout
        View view = inflater.inflate(R.layout.my_row, parent,false);

        return new MyViewHolder(view);
    }

    //指定RecyclerView中my_row layout的元件內容
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.note_title.setText(dataList.get(position).getNote_title());
        holder.note_content.setText(dataList.get(position).getNote_content());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("show_msg","mainLayout onClick");
//                Log.d("show_msg", String.valueOf(holder.getLayoutPosition()));//取得選取位置索引
//                Log.d("show_msg",dataList.get(holder.getLayoutPosition()).getNote_title());
//                Log.d("show_msg",dataList.get(holder.getLayoutPosition()).getNote_content());
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("Key_id", dataList.get(holder.getLayoutPosition()).getNote_id());
                intent.putExtra("Key_title", dataList.get(holder.getLayoutPosition()).getNote_title());
                intent.putExtra("Key_content", dataList.get(holder.getLayoutPosition()).getNote_content());
                activity.startActivityForResult(intent,1);
//                activity.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //RecyclerView中表示每個項目的視圖元素
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView note_title, note_content;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_title = itemView.findViewById(R.id.note_title);
            note_content = itemView.findViewById(R.id.note_content);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animation RecyclerView
//            translate_anim= AnimationUtils.loadAnimation(context,R.anim.translate_anim);
//            mainLayout.setAnimation(translate_anim);
        }

    }
}
