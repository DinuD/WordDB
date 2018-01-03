package tk.dinud11.worddb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import model.DatabaseHelper;
import model.Word;
import model.WordListAdapter;

public class MainActivity extends AppCompatActivity implements WordListAdapter.MyInterface {

    public static RequestQueue queue;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get all words from the server and add them to the db
        queue = Volley.newRequestQueue(this);
        WordRequesst.makeGETRequest(databaseHelper);

        // Set up the word list
        ListView wordList = findViewById(R.id.wordList);
        final WordListAdapter adapter = new WordListAdapter(this, databaseHelper.getAllWords(), getResources(), this);
        wordList.setAdapter(adapter);
        wordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int position = i;
                Object object = adapter.getItem(position);
                Word word = (Word) object;
                Log.v("ayy", "we got here");
                launchCustomInfo(word.getId());
            }
        });

        // Button to enable custom selections
        Button custom = findViewById(R.id.customSelect);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a popup menu
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.bytype:
                                showInputDialog(true);
                                return true;
                            case R.id.bycateg:
                                showInputDialog(false);
                            default:
                                return false;
                        }
                    }
                });
                popup.inflate(R.menu.menu_popup);
                popup.show();
            }
        });
    }

    private void showInputDialog(boolean by) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Custom selection");
        final Intent intent = new Intent(getApplicationContext(), CustomActivity.class);
        if(by) {
            final CharSequence[] items = {
                    "subst",
                    "verb",
                    "adj",
                    "adv",
            };
            builder.setSingleChoiceItems(items, 0, null);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    int selectedpos = ((AlertDialog)dialogInterface).getListView().getCheckedItemPosition();
                    intent.putExtra("by", "type");
                    intent.putExtra("selection", items[selectedpos]);
                    startActivity(intent);
                }
            });
            builder.show();
        } else {
            builder.setSingleChoiceItems(databaseHelper.getAllCategories(), 0, null);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    int selectedpos = ((AlertDialog)dialogInterface).getListView().getCheckedItemPosition();
                    intent.putExtra("by", "category");
                    intent.putExtra("selection", databaseHelper.getAllCategories()[selectedpos]);
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }

    public void launchCustomInfo(int id) {
        Intent intent = new Intent(MainActivity.this, CustomInfoActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void launchInfo(int id) {
        Intent intent = new Intent(MainActivity.this, CustomInfoActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modify:
                Intent modify = new Intent(getApplicationContext(), ModifyActivity.class);
                startActivity(modify);

            case R.id.delete:
                databaseHelper.deleteAllWords();
                finish();
                Log.v("del", "Deleted all words");

            case R.id.refresh:
                databaseHelper.deleteAllWords();
                WordRequesst.makeGETRequest(databaseHelper);
        }
        return super.onOptionsItemSelected(item);
    }
}
