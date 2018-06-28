package bs.models;

import bs.entities.BookEntity;
import bs.entities.BookLearningEntity;

import javax.validation.constraints.NotNull;

public class Book {
    private Integer id;
    private String bookName;
    private String bookIntro;
    private long totalVocabNumber;

    public Book(){}
    public Book(@NotNull BookEntity bookEntity){
        id = bookEntity.getId();
        bookName = bookEntity.getBookName();
        bookIntro = bookEntity.getBookIntro();
        totalVocabNumber = bookEntity.getTotalVocabNumber();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }
    public String getBookIntro() {
        return bookIntro;
    }

    public long getTotalVocabNumber() {
        return totalVocabNumber;
    }
    public void setTotalVocabNumber(long totalVocabNumber) {
        this.totalVocabNumber = totalVocabNumber;
    }
}
