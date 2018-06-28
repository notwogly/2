package bs.models;

import bs.entities.VocabEntity;

import javax.validation.constraints.NotNull;

public class UserVocabType {
    private Integer vocabId;
    private int type;

    public UserVocabType() {
    }
    public UserVocabType(Integer vocabId, int type) {
        this.vocabId = vocabId;
        this.type = type;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Integer getVocabId() {
        return vocabId;
    }
    public void setVocabId(Integer vocabId) {
        this.vocabId = vocabId;
    }
}
