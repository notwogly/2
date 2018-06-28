package bs.repositories;

import bs.entities.BookEntity;
import bs.entities.VocabEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//数据访问层接口
public interface VocabRepository extends JpaRepository<VocabEntity, Integer> {
    VocabEntity findByWord(String word);
}
