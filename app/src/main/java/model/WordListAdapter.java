package model;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tk.dinud11.worddb.MainActivity;
import tk.dinud11.worddb.R;

/**
 * Created by dinud11 on 12/28/17.
 */

public class WordListAdapter extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private ArrayList words;
    private static LayoutInflater inflater = null;
    public Resources resources;
    private MyInterface anInterface;
    Word tempValues = null;

    public WordListAdapter(Activity act, ArrayList arrlist, Resources res, MyInterface myInterface) {
        activity = act;
        words = arrlist;
        resources = res;
        anInterface = myInterface;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if (words.size() <= 0)
            return 1;
        return words.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {

    }

    public static class ViewHolder {
        public TextView word;
        public TextView def;
        public TextView category;
        public TextView type;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView == null) {
            vi = inflater.inflate(R.layout.list_layout, null);

            holder = new ViewHolder();
            holder.word = vi.findViewById(R.id.word);
            holder.def = vi.findViewById(R.id.def);
            holder.category = vi.findViewById(R.id.category);
            holder.type = vi.findViewById(R.id.type);

            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if(words.size() > 0) {
            tempValues = null;
            tempValues = (Word) words.get(position);

            holder.word.setText(" " + tempValues.getWord());
            holder.def.setText(" " + tempValues.getDefinition());
            holder.category.setText(tempValues.getCategory());
            holder.type.setText(tempValues.getType());

            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Word word = (Word) words.get(position);
                    Log.v("ayy", "we got here");
                    anInterface.launchInfo(word.getId());
                }
            });
        }
        return vi;
    }

    public interface MyInterface {
        void launchInfo(int id);
    }
}
