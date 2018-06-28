package bs.models;

import bs.entities.BookEntity;
import bs.entities.BookLearningEntity;

import javax.validation.constraints.NotNull;

public class BookLearning {
    private Integer id;
    private long totalNum;
    private long mastered;
    private long newVocab;
    private long dailyNum;
    private long learning;
    private Integer bookId;
    private String bookName;
    private String bookIntro;

    public BookLearning(){
        this.totalNum = 0;
        this.mastered = 0;
        this.newVocab = 0;
        this.dailyNum = 0;
        this.learning = 0;
        this.bookName = "无正在学习的书籍";
        this.bookIntro = "请先设置要学习的书籍";
    }

    public BookLearning(@NotNull BookLearningEntity bookLearningEntity){
        id = bookLearningEntity.getId();
        totalNum = bookLearningEntity.getTotalNum();
        mastered = bookLearningEntity.getMastered();
        newVocab = bookLearningEntity.getNewVocab();
        dailyNum = bookLearningEntity.getDailyNum();
        learning = bookLearningEntity.getLearning();
        BookEntity bookEntity = bookLearningEntity.getBookEntity();
        if(bookEntity == null)
            return;
        bookId = bookEntity.getId();
        bookName = bookEntity.getBookName();
        bookIntro = bookEntity.getBookIntro();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public long getDailyNum() {
        return dailyNum;
    }
    public void setDailyNum(long dailyNum) {
        this.dailyNum = dailyNum;
    }

    public long getNewVocab() {
        return newVocab;
    }
    public void setNewVocab(long newVocab) {
        this.newVocab = newVocab;
    }

    public long getMastered() {
        return mastered;
    }
    public void setMastered(long mastered) {
        this.mastered = mastered;
    }

    public long getLearning() {
        return learning;
    }
    public void setLearning(long learning) {
        this.learning = learning;
    }

    public long getTotalNum() {
        return totalNum;
    }
    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getBookId() {
        return bookId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookIntro() {
        return bookIntro;
    }
    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }
}
