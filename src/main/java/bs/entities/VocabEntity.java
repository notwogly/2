package bs.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vocabs", indexes = {@Index(name = "index_word", columnList = "word")})
public class VocabEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //单词内容
    private String word;

    //单词例句
    private String exampleSen ;

    //单词释义
    private String interpretation;

    //有该单词的所有书
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "vocab_book", joinColumns ={@JoinColumn(name = "vocab_id")},
    inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private List<BookEntity> books = new ArrayList<>();

    //有该单词的所属有用户单词库
//    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @JoinTable(name = "vocab_userVocabs", joinColumns ={@JoinColumn(name = "vocab_id")},
//            inverseJoinColumns = {@JoinColumn(name = "userVocabs_id")})
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST},mappedBy = "vocabs")
    private List<UserVocabsEntity> userVocabs = new ArrayList<>();

    public VocabEntity(){}
    public VocabEntity(String word, String interpretation){
        this.word = word;
        this.interpretation = interpretation;
    }

    public Integer getId(){
        return  id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getWord(){
        return word;
    }
    public void setWord(String word){
        this.word = word;
    }

    public String getExampleSen() {
        return exampleSen;
    }
    public void setExampleSen(String exampleSen) {
        this.exampleSen = exampleSen;
    }

    public String getInterpretation(){
        return interpretation;
    }
    public void setInterpretation(String interpretation){
        this.interpretation = interpretation;
    }

    public List<BookEntity> getBooks() {
        return books;
    }
    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }
    public void addToBook(BookEntity bookEntity){
        bookEntity.addTotalVocabNumber();
        books.add(bookEntity);
    }
    public void deleteFromBook(BookEntity bookEntity){
        bookEntity.subTotalVocabNumber();
        books.remove(bookEntity);
    }

    public List<UserVocabsEntity> getUserVocabs() {
        return userVocabs;
    }
    public void setUserVocabs(List<UserVocabsEntity> userVocabs) {
        this.userVocabs = userVocabs;
    }
}
