package foadkhezri.github.com.commonenglish;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(Activity context, ArrayList<Word> Word) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, Word);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Word currentWord = getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        TextView miwok = listItemView.findViewById(R.id.miwok);

        assert currentWord != null;
        miwok.setText(currentWord.getMiwokTranslation());

        TextView english = listItemView.findViewById(R.id.english);

        english.setText(currentWord.getDefaultTranslation());

        ImageView iconView = listItemView.findViewById(R.id.list_item_image);

        if(currentWord.hasImage()){
            iconView.setImageResource(currentWord.getmImageResourceId());
            iconView.setVisibility(View.VISIBLE);
        }
        else {
            iconView.setVisibility(View.GONE);
        }

        RelativeLayout relativeLayout = listItemView.findViewById(R.id.relative2);

        relativeLayout.setBackgroundColor(listItemView.getResources().getColor(currentWord.getBackgroundColor()));


        return listItemView;
    }
}