package uz.zako.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.entity.Admin;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
