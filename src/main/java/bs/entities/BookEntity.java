package bs.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books", indexes = {@Index(name = "index_book_name", columnList = "bookName")})
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //书名
    private String bookName;

    //书的简介
    private String bookIntro;

    //书中总的单词数
    private long totalVocabNumber;

    //书中的所有单词
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST},mappedBy = "books")
    private List<VocabEntity> vocabs = new ArrayList<>();

   //用户对书本的学习状态
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
//    @JoinTable(name = "book_leaning_book", joinColumns = {@JoinColumn(name = "book_id")},
//            inverseJoinColumns = {@JoinColumn(name = "book_learning_id")})
    private List<BookLearningEntity> bookLearnings = new ArrayList<>();

    public BookEntity(){}
    public BookEntity(String name, String intro){
        bookName = name;
        bookIntro = intro;
        totalVocabNumber = 0;
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

    public String getBookIntro() {
        return bookIntro;
    }
    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    public long getTotalVocabNumber() {
        return totalVocabNumber;
    }
    public void setTotalVocabNumber(long totalVocabNumber) {
        this.totalVocabNumber = totalVocabNumber;
    }
    public void addTotalVocabNumber(){
        totalVocabNumber++;
    }
    public void subTotalVocabNumber(){
        totalVocabNumber--;
    }

    public List<VocabEntity> getVocabs() {
        return vocabs;
    }
    public void setVocabs(List<VocabEntity> vocabs) {
        this.vocabs = vocabs;
    }
    public void addVocab(VocabEntity vocabEntity){
        totalVocabNumber++;
        vocabEntity.addToBook(this);
        vocabs.add(vocabEntity);
    }

    public List<BookLearningEntity> getBookLearnings() {
        return bookLearnings;
    }
    public void setBookLearnings(List<BookLearningEntity> bookLearnings) {
        this.bookLearnings = bookLearnings;
    }
    public void addBookLearning(BookLearningEntity bookLearningEntity){
        bookLearningEntity.setBookEntity(this);
        bookLearnings.add(bookLearningEntity);
    }
}
