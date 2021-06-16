package com.example.y.fusencalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {
    private List<Date> dateArray = new ArrayList<>();
    private Context mContext;
    private DateManager mDateManager;
    private LayoutInflater mLayoutInflater;

    //カスタムセルを拡張したら、ここでWidgetを定義
    private static class ViewHolder {
        public TextView dateText;
        public RelativeLayout relativeLayout;
    }

    public CalendarAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDateManager = new DateManager();
        dateArray = mDateManager.getDays();
    }

    @Override
    public int getCount(){
        return dateArray.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.one_calendar_cell, null);
            holder = new ViewHolder();
            holder.dateText = convertView.findViewById(R.id.titleText);
//            holder.relativeLayout = convertView.findViewById(R.id.relativeLayout);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        //セルのサイズを指定
        float dp = mContext.getResources().getDisplayMetrics().density;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(parent.getWidth() / 7 - (int)dp, (parent.getHeight() - (int)dp * mDateManager.getWeeks() ) / mDateManager.getWeeks());
        convertView.setLayoutParams(params);

        //日付のみ表示させる
        SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.US);
        holder.dateText.setText(dateFormat.format(dateArray.get(position)));

        //セルをタップしたら、DailyEventListActivityへ遷移
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: DailyEventListActivityへ遷移するIntentを宣言
                //Todo: putExtraで年月日を付与
                //Todo: 遷移実行
            }
        });

        //日曜日は赤色、土曜日は青色で日付を表示する
        int colorId;
        switch (mDateManager.getDayOfWeek(dateArray.get(position))){
            case 1:
                colorId = Color.RED;
                break;
            case 7:
                colorId = Color.BLUE;
                break;
            default:
                colorId = Color.BLACK;
                break;
        }
        holder.dateText.setTextColor(colorId);

        //当月以外のセルはdateTextを灰色にする
        if (mDateManager.isCurrentMonth(dateArray.get(position))){
            convertView.setBackgroundColor(Color.WHITE);
        }else {
            convertView.setBackgroundColor(Color.WHITE);
            holder.dateText.setTextColor(Color.LTGRAY);
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    //表示月を取得
    public String getTitle(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.US);
        return format.format(mDateManager.mCalendar.getTime());
    }

    //翌月取得
    public void nextMonth(){
        mDateManager.nextMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }

    //前月表示
    public void prevMonth(){
        mDateManager.prevMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }




}
