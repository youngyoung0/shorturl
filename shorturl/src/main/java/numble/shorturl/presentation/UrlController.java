package numble.shorturl.presentation;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import numble.shorturl.application.UrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @GetMapping("issue")
    public String urlIssue (String url){
        return urlService.urlIssue(url);
    }
}
