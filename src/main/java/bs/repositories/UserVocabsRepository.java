package bs.repositories;

import bs.entities.UserEntity;
import bs.entities.UserVocabsEntity;
import bs.entities.VocabEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//数据访问层接口
public interface UserVocabsRepository extends JpaRepository<UserVocabsEntity, Integer> {
    UserVocabsEntity findByTypeAndUser(int type, UserEntity userEntity);
    List<UserVocabsEntity> findByUser(UserEntity userEntity);
}
