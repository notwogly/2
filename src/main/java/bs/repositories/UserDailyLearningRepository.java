package bs.repositories;

import bs.entities.UserDailyLearningEntity;
import bs.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//数据访问层接口
public interface UserDailyLearningRepository extends JpaRepository<UserDailyLearningEntity, Integer> {
    List<UserDailyLearningEntity> findByUser(UserEntity userEntity);
}
