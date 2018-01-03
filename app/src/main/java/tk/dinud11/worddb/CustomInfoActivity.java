package tk.dinud11.worddb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import model.DatabaseHelper;
import model.Word;

public class CustomInfoActivity extends AppCompatActivity {

    static final int EDIT_WORD = 1;
    final DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_info);

        final int wordId = getIntent().getExtras().getInt("id");
        final Word word = databaseHelper.getWord(wordId);

        TextView word_textview = findViewById(R.id.info_word);
        TextView def_textview = findViewById(R.id.info_def);
        TextView type = findViewById(R.id.info_type);
        TextView categ = findViewById(R.id.info_categ);
        Button delbtn = findViewById(R.id.delbtn);
        Button editbtn = findViewById(R.id.editbtn);

        word_textview.setText(word.getWord());
        def_textview.setText(word.getDefinition());
        type.setText(word.getType());
        categ.setText(word.getCategory());

        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject request = new JSONObject();
                try {
                    request.put("word", word.getWord());
                    request.put("definition", word.getDefinition());
                    request.put("word_type", word.getType());
                    request.put("category", word.getCategory());

                    WordRequesst.makeDELETERequest(request, databaseHelper, word);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(getApplicationContext(), EditActivity.class);
                edit.putExtra("wordid", wordId);
                startActivityForResult(edit, EDIT_WORD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("ayy", "hmmm");
        if(requestCode == EDIT_WORD)
            if(resultCode == RESULT_OK) {
                JSONObject request = new JSONObject();
                Log.v("ayy", "finished");

                try {
                    request.put("id", data.getIntExtra("id", -1));
                    request.put("word", data.getStringExtra("word"));
                    request.put("definition", data.getStringExtra("definition"));
                    request.put("word_type", data.getStringExtra("word_type"));
                    request.put("category", data.getStringExtra("category"));

                    Word word = new Word(data.getStringExtra("word"),
                            data.getStringExtra("definition"),
                            data.getStringExtra("word_type"),
                            data.getStringExtra("category"),
                            data.getIntExtra("id", -1));

                    WordRequesst.makePUTRequest(request, word, databaseHelper);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
    }
}
