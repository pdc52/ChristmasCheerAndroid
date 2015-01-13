package philipcorriveau.com.christmascheer.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import philipcorriveau.com.christmascheer.Constants;
import philipcorriveau.com.christmascheer.utils.FontChanger;
import philipcorriveau.com.christmascheer.R;
import philipcorriveau.com.christmascheer.models.Cheer;

/**
 * Created by philipcorriveau on 12/14/14.
 */
public class CheerReturnedActivity extends VisibleActivity {

    Cheer cheer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheer_returned);

        String jsonData = getIntent().getStringExtra(Constants.CHEER_RETURNED_DATA_KEY);
        Log.d("com.parse.push", jsonData);

        Gson gson = new Gson();
        cheer = gson.fromJson(jsonData, Cheer.class);

        TextView cheerReturnedTitle = (TextView) findViewById(R.id.cheer_returned_title);
        cheerReturnedTitle.setTypeface(FontChanger.getInstance(this).getTypeFace());

        TextView cheerMessage = (TextView) findViewById(R.id.cheer_returned_message);
        cheerMessage.setText(cheer.getAlert());
        cheerMessage.setTypeface(FontChanger.getInstance(this).getTypeFace());

        Button thanksButton = (Button) findViewById(R.id.cheer_returned_button);
        thanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cheerReturnedTitle.setTypeface(FontChanger.getInstance(this).getTypeFace());
    }
}
