package bs.models;

import bs.entities.BookEntity;
import bs.entities.VocabEntity;

import javax.validation.constraints.NotNull;

public class Vocab {
    private Integer id;
    private String word;
    private String interpretation;
    private String exampleSen;

    public Vocab() {
    }
    public Vocab(@NotNull VocabEntity vocabEntity) {
        id = vocabEntity.getId();
        word = vocabEntity.getWord();
        exampleSen = vocabEntity.getExampleSen();
        interpretation = vocabEntity.getInterpretation();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setExampleSen(String exampleSen) {
        this.exampleSen = exampleSen;
    }
    public String getExampleSen() {
        return exampleSen;
    }
    public String getInterpretation() {
        return interpretation;
    }
    public String getWord() {
        return word;
    }
    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }
    public void setWord(String word) {
        this.word = word;
    }
}
