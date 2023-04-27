package numble.shorturl.domain;

import jakarta.persistence.*;

@Entity
@Table(schema = "url_call")
public class UrlCall extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Browser browser;
}
