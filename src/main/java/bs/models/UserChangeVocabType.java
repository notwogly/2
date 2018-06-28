package bs.models;

public class UserChangeVocabType {
    private Integer vocabId;
    private int type;
    private int oldType;

    public UserChangeVocabType() {
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getOldType() {
        return oldType;
    }
    public void setOldType(int oldType) {
        this.oldType = oldType;
    }
    public Integer getVocabId() {
        return vocabId;
    }
    public void setVocabId(Integer vocabId) {
        this.vocabId = vocabId;
    }
}
