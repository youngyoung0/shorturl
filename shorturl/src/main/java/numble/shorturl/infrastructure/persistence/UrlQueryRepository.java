package numble.shorturl.infrastructure.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import numble.shorturl.domain.Status;
import numble.shorturl.domain.Url;
import numble.shorturl.domain.dto.UrlShortDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static numble.shorturl.domain.QUrl.url;

@Repository
public class UrlQueryRepository {

    private final JPAQueryFactory query;

    public UrlQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public Long findUrlIdMax(){
        return query.select(url.id.max().coalesce(0L))
                .from(url)
                .fetchOne();
    }

    public Optional<Url> findByOriginUrlAndExpiration(String findUrl){
        return Optional.ofNullable(query.select(url)
                .from(url)
                .where(url.originUrl.eq(findUrl)
                        .and(url.status.ne(Status.REMOVE))
                )
                .fetchOne());
    }
}
