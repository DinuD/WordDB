package tk.dinud11.worddb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.DatabaseHelper;
import model.Word;

public class ModifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        Button add = findViewById(R.id.addbtn);
        final Spinner categ = findViewById(R.id.categspin);
        final Spinner type = findViewById(R.id.typespin);
        final EditText word = findViewById(R.id.entword);
        final EditText definition = findViewById(R.id.entdef);
        final Button amic = findViewById(R.id.amic_add);
        final Button amare = findViewById(R.id.amare_add);
        final Button smic = findViewById(R.id.smic_add);
        final Button umic = findViewById(R.id.umic_add);
        final Button umare = findViewById(R.id.umare_add);
        final Button omare = findViewById(R.id.omare_add);
        final Button omic = findViewById(R.id.omic_add);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,
                databaseHelper.getAllCategoriesStringArray());
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categ.setAdapter(adapter);

        amic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.append(amic.getText());
            }
        });

        amare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.append(amare.getText());
            }
        });

        smic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.append(smic.getText());
            }
        });

        omare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.append(omare.getText());
            }
        });

        omic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.append(omic.getText());
            }
        });

        umare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.append(umare.getText());
            }
        });

        umic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.append(umic.getText());
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entword = word.getText().toString();
                String entcateg = String.valueOf(categ.getSelectedItem());
                String enttype = String.valueOf(type.getSelectedItem());
                String entdef = definition.getText().toString();

                try {
                    JSONObject req = new JSONObject();
                    req.put("word", entword);
                    req.put("definition", entdef);
                    req.put("word_type", enttype);
                    req.put("category", entcateg);

                    WordRequesst.makePOSTRequest(req);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
