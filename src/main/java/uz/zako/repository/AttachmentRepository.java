package uz.zako.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.zako.entity.Attachment;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Attachment findByHashId(String hashId);

    @Query(value = "select * from attachment a where a.hash_id=:hashId", nativeQuery = true)
    Optional<Attachment> findByHashId1(@Param("hashId") String hashId);

    @Query(nativeQuery = true, value = "delete from attachment a where a.hash_id=:hashId")
    Boolean deleteById(@Param("hashId") String hashId);

    @Query(nativeQuery = true, value = "select a.hash_id from attachment a where a.product_id=?1 limit 1")
    String getByProductsId(Long productId);

}
