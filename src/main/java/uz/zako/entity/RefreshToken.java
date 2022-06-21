package uz.zako.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.zako.entity.abstractentity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
public class RefreshToken extends AbstractEntity {

    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Date expiredTime;

}


