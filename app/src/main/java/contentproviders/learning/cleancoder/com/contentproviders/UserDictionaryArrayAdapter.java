package contentproviders.learning.cleancoder.com.contentproviders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cleancoder.base.common.data.TableRow;

import java.util.List;

/**
 * Created by Leonid on 29.10.2014.
 */
public class UserDictionaryArrayAdapter extends ArrayAdapter<TableRow> {

    private static final int LAYOUT_ID = R.layout.list_item_user_dictionary;

    private final LayoutInflater layoutInflater;

    public UserDictionaryArrayAdapter(Context context, List<TableRow> items) {
        super(context, LAYOUT_ID, items);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView : layoutInflater.inflate(LAYOUT_ID, null);
        TableRow word = getItem(position);
        setText(itemView, R.id.word_text_view, word.<String>get(WordAttributes.WORD));
        setText(itemView, R.id.frequency_text_view, String.valueOf(word.<Integer>get(WordAttributes.FREQUENCY)));
        setText(itemView, R.id.locale_text_view, word.<String>get(WordAttributes.LOCALE));
        return itemView;
    }

    private static void setText(View itemView, int textViewId, String text) {
        TextView textView = (TextView) itemView.findViewById(textViewId);
        textView.setText(text);
    }

}
