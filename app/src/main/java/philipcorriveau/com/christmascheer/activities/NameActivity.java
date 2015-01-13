package philipcorriveau.com.christmascheer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import philipcorriveau.com.christmascheer.Constants;
import philipcorriveau.com.christmascheer.utils.FontChanger;
import philipcorriveau.com.christmascheer.R;

/**
 * Created by philipcorriveau on 12/15/14.
 */
public class NameActivity extends VisibleActionBarActivity {

    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        TextView namePrompt = (TextView) findViewById(R.id.name_prompt);
        namePrompt.setTypeface(FontChanger.getInstance(this).getTypeFace());

        name = (EditText) findViewById(R.id.name_edit_text);
        name.setTypeface(FontChanger.getInstance(this).getTypeFace());

        Button nextButton = (Button) findViewById(R.id.begin_cheer_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmName();
            }
        });
        nextButton.setTypeface(FontChanger.getInstance(this).getTypeFace());
    }

    public void confirmName() {
        Intent intent = new Intent(this, NameConfirmActivity.class);
        intent.putExtra(Constants.NAME_KEY, name.getText().toString());
        startActivity(intent);
    }
}
