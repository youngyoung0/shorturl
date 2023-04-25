package numble.shorturl.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "url_call")
public class UrlCall extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String browser;
}
