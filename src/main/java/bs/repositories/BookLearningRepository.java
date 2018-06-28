package bs.repositories;

import bs.entities.BookEntity;
import bs.entities.BookLearningEntity;
import bs.entities.UserEntity;
import bs.entities.VocabEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//数据访问层接口
public interface BookLearningRepository extends JpaRepository<BookLearningEntity, Integer> {
    List<BookLearningEntity> findByUser(UserEntity userEntity);
    BookLearningEntity findByUserAndBook(UserEntity userEntity, BookEntity bookEntity);
    BookLearningEntity findByUserAndLearned(UserEntity userEntity, boolean learned);
}
