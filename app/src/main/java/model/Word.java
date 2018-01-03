package model;

/**
 * Created by dinud11 on 12/26/17.
 */

public class Word {

    private String word;
    private String definition;
    private String category;
    private String type;
    private int id;

    public Word() {}

    public Word(String word, String definition, String category, String type, int id) {
        this.id = id;
        this.word = word;
        this.definition = definition;
        this.category = category;
        this.type = type;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
