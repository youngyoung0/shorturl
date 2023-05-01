package numble.shorturl.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url extends BaseTimeEntity {

    private static final int expireDate = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originUrl;
    private String shortUrl;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String expiredTime;

    @Builder
    public Url(String originUrl, String shortUrl){
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.status = Status.ACTIVATION;
        this.expiredTime = LocalDateTime.now(ZoneOffset.UTC).plusDays(expireDate).toString();
    }

}
