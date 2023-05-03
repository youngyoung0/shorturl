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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        urlToStatus(urlShortDto);

        long urlMaxId = urlQueryRepository.findUrlIdMax() + 1;
        String encodingUrl = UrlEncodingService.encoding(urlMaxId);
        String shortUrl = MAIN_URL + encodingUrl;

        urlRepository.save(
                Url.builder()
                        .originUrl(urlShortDto.getUrl())
                        .shortUrl(shortUrl)
                        .status(urlShortDto.getStatus())
                        .expireDate(urlShortDto.getExpireDate())
                        .build());
        return shortUrl;
    }

    @Transactional
    public String callUrl(HttpServletRequest request, String encodingUrl) {

        Url findUrl = findUrl(request, encodingUrl);

        if(checkUrlExpiration(findUrl)){
            urlHistorySave(request, findUrl);
            return findUrl.getOriginUrl();
        }
        return "url이 만료되었습니다.";
    }

    private void urlToStatus(UrlShortDto urlShortDto) {
        Optional<Url> findUrl = urlQueryRepository.findNonRemoveByOriginUrl(urlShortDto.getUrl());
        findUrl.ifPresent(url -> url.setStatus(Status.REMOVE));
    }


    private Url findUrl(HttpServletRequest request, String encodingUrl) {

        Long urlId = urlEncodingService.decoding(encodingUrl);
        return urlQueryRepository.findNonRemoveUrlById(urlId).get();
    }

    private boolean checkUrlExpiration(Url findUrl){

        LocalDateTime severTime = LocalDateTime.now();
        return severTime.isBefore(findUrl.getExpiredTime());
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
