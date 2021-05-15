package edu.dipae.sportify.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dipae.sportify.R;

public class GridListAdapter extends BaseAdapter
{
    public static final int CELL_ALIGNMENT_DEFAULT = 0;
    public static final int CELL_ALIGNMENT_LEFT    = 1;
    public static final int CELL_ALIGNMENT_CENTER  = 2;
    public static final int CELL_ALIGNMENT_RIGHT   = 3;

    private boolean singleLineHeader;

    private final Context context;

    private final ArrayList<Integer> columnWidths;
    private final ArrayList<ArrayList<String>> data;
    private ArrayList<Integer> columnAlignment;

    public GridListAdapter(Context context, ArrayList<ArrayList<String>> data, ArrayList<Integer> columnWidths){
        this.columnWidths = columnWidths;
        this.context = context;
        this.data = data;

        this.singleLineHeader = false;

        this.columnAlignment = new ArrayList<>();

        for (int i = 0 ; i < getColumnCount(); i++)
        {
            this.columnAlignment.add(GridListAdapter.CELL_ALIGNMENT_LEFT);
        }
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
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setWeightSum(100);

        for(int i = 0 ; i < getColumnCount() ; i ++) {
            TextView textView = new TextView(context);

            textView.setMaxLines(1);
            textView.setMinLines(1);

            if(position == 0 && !this.singleLineHeader)
            {
                textView.setMaxLines(2);
                textView.setMinLines(2);
            }

            if(position == 0) {
                textView.setBackgroundColor(Color.argb(255,254, 228, 0));
            } else if(position % 2 == 0) {
                if ( i % 2 == 0) {
                    textView.setBackgroundColor(Color.argb(255,230, 237, 220));
                } else {
                    textView.setBackgroundColor(Color.argb(255,238, 239, 215));
                }
            } else {
                if ( i % 2 == 0) {
                    textView.setBackgroundColor(Color.argb(255,188, 208, 159));
                } else {
                    textView.setBackgroundColor(Color.argb(255,211, 215, 141));
                }
            }

//            if( i == 0) {
//                textView.setBackgroundColor(Color.CYAN);
//            } else if ( i == 1) {
//                textView.setBackgroundColor(Color.MAGENTA);
//            } else if ( i == 2) {
//                textView.setBackgroundColor(Color.YELLOW);
//            }


            textView.setText(data.get( position ).get( i ));

            textView.setLayoutParams(getLayoutParamsForTextView(i));
            textView.setGravity(getGravity(position, i));

            linearLayout.addView(textView);
        }

        return linearLayout;
    }

    private ViewGroup.LayoutParams getLayoutParamsForTextView(int i)
    {
        return new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                columnWidths.get(i)
                );
    }

    private int getGravity(int row, int column)
    {
        if(row == 0)
        {
            return Gravity.CENTER;
        }

        switch (columnAlignment.get(column))
        {
            case CELL_ALIGNMENT_RIGHT : return Gravity.END;
            case CELL_ALIGNMENT_CENTER: return Gravity.CENTER;
            default                   : return Gravity.START;
        }
    }

    public int getColumnCount()
    {
        return data == null || data.isEmpty() ? 0 : data.get(0).size();
    }

    public String getValueAt(int row, int column) {
        return data == null ? "" : data.get(row).get(column);
    }

    public void setColumnAlignment(ArrayList<Integer> columnAlignment) {
        this.columnAlignment = columnAlignment;
    }
}