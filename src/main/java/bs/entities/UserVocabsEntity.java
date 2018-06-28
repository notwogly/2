package bs.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_vocabs")
public class UserVocabsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //表示该单词库的类型
    private int type;

    //该用户
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "user_vocabs_user")
    private UserEntity user;

    //词库中的所有单词
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "vocab_user_vocabs", joinColumns ={@JoinColumn(name = "user_vocabs_id")},
            inverseJoinColumns = {@JoinColumn(name = "vocab_id")})
    private List<VocabEntity> vocabs = new ArrayList<>();

    public UserVocabsEntity(){}
    public UserVocabsEntity(int type, UserEntity userEntity){
        this.type = type;
        this.user = userEntity;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<VocabEntity> getVocabs() {
        return vocabs;
    }
    public void setVocabs(List<VocabEntity> vocabs) {
        this.vocabs = vocabs;
    }
    public void addVocabs(VocabEntity vocabEntity){
        vocabs.add(vocabEntity);
    }
    public void deleteVocab(VocabEntity vocabEntity){
        vocabs.remove(vocabEntity);
    }
}
