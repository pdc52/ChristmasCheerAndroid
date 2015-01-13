package philipcorriveau.com.christmascheer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import philipcorriveau.com.christmascheer.Constants;
import philipcorriveau.com.christmascheer.utils.FontChanger;
import philipcorriveau.com.christmascheer.R;

/**
 * Created by philipcorriveau on 12/15/14.
 */
public class NameConfirmActivity extends VisibleActivity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_confirm);

        Intent intent = getIntent();
        name = intent.getStringExtra(Constants.NAME_KEY);

        Button nextButton = (Button) findViewById(R.id.confirm_name_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveName();
                launchMain();
            }
        });
        nextButton.setTypeface(FontChanger.getInstance(this).getTypeFace());


        TextView nameTextView = (TextView) findViewById(R.id.name_text_view);
        nameTextView.setText(name);
        nameTextView.setTypeface(FontChanger.getInstance(this).getTypeFace());

        TextView nameWarning = (TextView) findViewById(R.id.name_warning);
        nameWarning.setTypeface(FontChanger.getInstance(this).getTypeFace());
    }

    public void saveName() {
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putString(Constants.PREF_NAME, name).apply();
    }

    public void launchMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
