package edu.dipae.sportify.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dipae.sportify.models.Sport;

public class KeyPairSpinnerAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<ArrayList<String>> data;

    public KeyPairSpinnerAdapter(Context context, ArrayList<ArrayList<String>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                )
        );

        TextView textView = new TextView(context);
        textView.setText(data.get(position).get(1));
        textView.setPadding(40,5,5,5);
        textView.setTextSize(18);

        linearLayout.addView(textView);

        linearLayout.setPadding(5,5,5,5);
        return linearLayout;
    }
}
