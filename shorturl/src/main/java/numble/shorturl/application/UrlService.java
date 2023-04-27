package numble.shorturl.application;

import lombok.RequiredArgsConstructor;
import numble.shorturl.domain.Url;
import numble.shorturl.infrastructure.persistence.UrlQueryRepository;
import numble.shorturl.infrastructure.persistence.UrlRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final UrlQueryRepository urlQueryRepository;
    private static final String MAIN_URL = "localhost:8080";

    public String urlIssue(String url) {

        Long urlMaxId = urlQueryRepository.findUrlIdMax();
        String encodingUrl = UrlEncodingService.encoding(urlMaxId);

        urlRepository.save(
                Url.builder()
                .origin_url(url)
                .short_url(encodingUrl)
                .build());

        return MAIN_URL + "/" +encodingUrl;
    }
}
