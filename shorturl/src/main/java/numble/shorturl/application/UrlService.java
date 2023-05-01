package numble.shorturl.application;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import numble.shorturl.domain.Browser;
import numble.shorturl.domain.Status;
import numble.shorturl.domain.Url;
import numble.shorturl.domain.UrlCall;
import numble.shorturl.domain.dto.UrlShortDto;
import numble.shorturl.infrastructure.persistence.UrlCallRepository;
import numble.shorturl.infrastructure.persistence.UrlQueryRepository;
import numble.shorturl.infrastructure.persistence.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final UrlCallRepository urlCallRepository;
    private final UrlQueryRepository urlQueryRepository;
    private final UrlEncodingService urlEncodingService;
    private final RefererServlet refererServlet;

    private static final String MAIN_URL = "localhost:8080/";

    @Transactional
    public String urlIssue(UrlShortDto urlShortDto) {

        // 생성하려는 URL이 있는지 확인 있으면 삭제
        findOriginUrl(urlShortDto);

        long urlMaxId = urlQueryRepository.findUrlIdMax() + 1;
        String encodingUrl = UrlEncodingService.encoding(urlMaxId);
        String shortUrl = MAIN_URL + encodingUrl;

        urlRepository.save(
                Url.builder()
                        .originUrl(urlShortDto.getUrl())
                        .shortUrl(shortUrl)
                        .status(urlShortDto.getStatus())
                        .build());
        return shortUrl;
    }

    @Transactional
    public String callUrl(HttpServletRequest request, String encodingUrl) {

        Url findUrl = findUrl(encodingUrl);

        urlHistorySave(request, findUrl);

        // TODO 만료 인증 확인

        return findUrl.getOriginUrl();
    }

    private void findOriginUrl(UrlShortDto urlShortDto) {
        Optional<Url> findUrl = urlQueryRepository.findByOriginUrlAndExpiration(urlShortDto.getUrl());
        findUrl.ifPresent(url -> url.setStatus(Status.REMOVE));
    }


    private Url findUrl(String encodingUrl) {
        Long urlId = urlEncodingService.decoding(encodingUrl);
        return urlRepository.findById(urlId).get();
    }

    private Browser searchBrowser(HttpServletRequest request) {
        String referer = refererServlet.getReferer(request);
        return Arrays.stream(Browser.values())
                .filter(b -> referer.contains(b.toString()))
                .findAny().get();
    }

    private void urlHistorySave(HttpServletRequest request, Url url) {
        Browser browser = searchBrowser(request);

        urlCallRepository.save(
                UrlCall.builder()
                        .browser(browser)
                        .url(url)
                        .build()
        );

    }


}
