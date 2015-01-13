package philipcorriveau.com.christmascheer.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import philipcorriveau.com.christmascheer.R;
import philipcorriveau.com.christmascheer.utils.FontChanger;

/**
 * Created by philipcorriveau on 12/21/14.
 */
public class CheerAdapter extends ParseQueryAdapter<ParseObject> {

    public CheerAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("ChristmasCheerNotificationDev");
                query.whereEqualTo("toInstallationId", ParseInstallation.getCurrentInstallation().getObjectId());
                query.addAscendingOrder("createdAt");
                return query;
            }
        });
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.cheer_element, null);
        }
        super.getItemView(object, v, parent);

        String name = object.getString("fromName");
        String location = object.getString("fromLocation");
        boolean cheerReturned = object.getBoolean("hasBeenRespondedTo");

        TextView nameTextView = (TextView) v.findViewById(R.id.name_text_view);
        nameTextView.setText(name);
        nameTextView.setTypeface(FontChanger.getInstance(getContext()).getTypeFace());

        TextView locationTextView = (TextView) v.findViewById(R.id.location_text_view);
        locationTextView.setText(location);
        locationTextView.setTypeface(FontChanger.getInstance(getContext()).getTypeFace());

        Button returnCheerButton = (Button) v.findViewById(R.id.return_cheer_button);
        returnCheerButton.setTypeface(FontChanger.getInstance(getContext()).getTypeFace());
        if (cheerReturned) {
            returnCheerButton.setVisibility(View.GONE);
        }

        return v;
    }
}
