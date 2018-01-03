package tk.dinud11.worddb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import model.DatabaseHelper;
import model.Word;
import model.WordListAdapter;

public class CustomActivity extends AppCompatActivity implements WordListAdapter.MyInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ListView customList = findViewById(R.id.customlist);

        Intent intent = getIntent();
        if(intent.getStringExtra("by").equals("type")) {
            String type = intent.getStringExtra("selection");
            customList.setAdapter(new WordListAdapter(this, databaseHelper.getWordsByType(type), getResources(), this) {
                @Override
                public void onClick(View view) {

                }
            });
        } else {
            String category = intent.getStringExtra("selection");
            customList.setAdapter(new WordListAdapter(this, databaseHelper.getWordsByCategory(category), getResources(), this) {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public void launchInfo(int id) {
        Intent intent = new Intent(CustomActivity.this, CustomInfoActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
