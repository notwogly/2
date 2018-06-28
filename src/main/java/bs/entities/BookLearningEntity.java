package bs.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_learning")
public class BookLearningEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean learned;
    private long totalNum;
    private long mastered;
    private long learning;
    private long newVocab;
    private long dailyNum;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "book_learning_user")
    private UserEntity user;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "book_learning_book")
    private BookEntity book;

    public BookLearningEntity(){}
    public BookLearningEntity(UserEntity userEntity, BookEntity bookEntity, long newVocab){
        this.user = userEntity;
        this.book = bookEntity;
        this.totalNum = bookEntity.getTotalVocabNumber();
        this.mastered = 0;
        this.learning = 0;
        this.newVocab = newVocab;
        this.learned = true;
        this.dailyNum = 20;
        if( 20 > this.totalNum || 20> this.newVocab)
            this.dailyNum = (this.totalNum>this.newVocab)?this.newVocab:this.totalNum;
        System.out.println(this.dailyNum);
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isLearned() {
        return learned;
    }
    public void setLearned(boolean learned) {
        this.learned = learned;
    }

    public BookEntity getBook() {
        return book;
    }
    public void setBook(BookEntity book) {
        this.book = book;
    }

    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }

    public long getTotalNum() {
        return totalNum;
    }
    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public long getMastered() {
        return mastered;
    }
    public void setMastered(long mastered) {
        this.mastered = mastered;
    }
    public void addMastered(){
        this.mastered++;
    }
    public void subMastered(){
        this.mastered--;
    }

    public long getLearning() {
        return learning;
    }
    public void setLearning(long learning) {
        this.learning = learning;
    }
    public void addLearning(){
        this.learning++;
    }
    public void subLearning(){
        this.learning--;
    }

    public long getNewVocab() {
        return newVocab;
    }
    public void setNewVocab(long newVocab) {
        this.newVocab = newVocab;
    }
    public void addNewVocab(){
        this.newVocab++;
    }
    public void subNewVocab(){
        this.newVocab--;
    }

    public long getDailyNum() {
        return dailyNum;
    }
    public void setDailyNum(long dailyNum) {
        this.dailyNum = dailyNum;
    }
    public void updateDailyNum(){
        if( this.dailyNum > this.totalNum || this.dailyNum> this.newVocab)
            this.dailyNum = (this.totalNum>this.newVocab)?this.newVocab:this.totalNum;

    }

    public UserEntity getUserEntity() {
        return user;
    }
    public void setUserEntity(UserEntity userEntity) {
        this.user = userEntity;
    }

    public BookEntity getBookEntity() {
        return book;
    }
    public void setBookEntity(BookEntity bookEntity) {
        totalNum = bookEntity.getTotalVocabNumber();
        newVocab = totalNum;
        this.book = bookEntity;
    }
}
