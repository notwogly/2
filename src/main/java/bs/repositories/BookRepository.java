package bs.repositories;

import bs.entities.BookEntity;
import bs.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//数据访问层接口
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
}
