package tk.dinud11.worddb

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_edit.*
import model.DatabaseHelper

class EditActivity : AppCompatActivity() {

    val databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val wordId = intent.getIntExtra("wordid", -1)
        val word = databaseHelper.getWord(wordId)
        edit_word.setText(word.word)
        edit_def.setText(word.definition)

        val adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,
                databaseHelper.allCategoriesStringArray)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        edit_categ.adapter = adapter
        edit_categ.setSelection(databaseHelper.allCategoriesStringArray.indexOf(word.category))
        val types = arrayOf("subst", "verb", "adj", "adv")
        for(i in types.indices) {
            if(word.type == types[i])
                edit_type.setSelection(i)
        }

        edit_save.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                Log.v("ayy", "let's gooo")
                val ent_word = edit_word.text.toString()
                val ent_def = edit_def.text.toString()
                val ent_categ = edit_categ.selectedItem.toString()
                val ent_type = edit_type.selectedItem.toString()

                if(ent_word!=word.word || ent_def!=word.definition || ent_type!=word.type
                        || ent_categ!=word.category) {
                    val resultIntent = Intent()
                    resultIntent.putExtra("word", ent_word)
                    resultIntent.putExtra("definition", ent_def)
                    resultIntent.putExtra("word_type", ent_type)
                    resultIntent.putExtra("category", ent_categ)
                    resultIntent.putExtra("id", wordId)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        })

        amic_add.setOnClickListener(View.OnClickListener { edit_word.append(amic_add.getText()) })

        amare_add.setOnClickListener(View.OnClickListener { edit_word.append(amare_add.getText()) })

        smic_add.setOnClickListener(View.OnClickListener { edit_word.append(smic_add.getText()) })

        omare_add.setOnClickListener(View.OnClickListener { edit_word.append(omare_add.getText()) })

        omic_add.setOnClickListener(View.OnClickListener { edit_word.append(omic_add.getText()) })

        umare_add.setOnClickListener(View.OnClickListener { edit_word.append(umare_add.getText()) })

        umic_add.setOnClickListener(View.OnClickListener { edit_word.append(umic_add.getText()) })
    }
}
