package bs.models;

import bs.entities.VocabEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Check {
    private int right;
    private Vocab vocabDetail;
    private List<String> selection;

    public Check() {
    }
    public Check(@NotNull VocabEntity vocabEntity, @NotNull List<VocabEntity> vocabEntityList) {
        Random random = new Random();
        selection = new ArrayList<>();
        right = random.nextInt(4);
        vocabDetail = new Vocab(vocabEntity);
        //System.out.println(this.right);
        for(int i = 0; i<right; i++)
        {
            selection.add(vocabEntityList.get(i).getInterpretation());
        }
        selection.add(vocabEntity.getInterpretation());
        for(int i = right+1; i<4; i++)
        {
            selection.add(vocabEntityList.get(i-1).getInterpretation());
        }
    }

    public int getRight() {
        return right;
    }
    public List<String> getSelection() {
        return selection;
    }
    public void setRight(int right) {
        this.right = right;
    }
    public void setSelection(List<String> selection) {
        this.selection = selection;
    }
    public Vocab getVocabDetail() {
        return vocabDetail;
    }
    public void setVocabDetail(Vocab vocabDetail) {
        this.vocabDetail = vocabDetail;
    }
}
