package numble.shorturl.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity
public class Url extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin_url;
    private String short_url;
    private Status status;

    @Builder
    public Url(String origin_url, String short_url){
        this.origin_url = origin_url;
        this.short_url = short_url;
        this.status = Status.ACTIVATION;
    }

    public Url() {

    }
}
