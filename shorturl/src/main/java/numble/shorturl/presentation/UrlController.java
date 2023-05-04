package numble.shorturl.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import numble.shorturl.application.UrlService;
import numble.shorturl.domain.dto.UrlShortDto;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("url/issue")
    public String urlIssue (@RequestBody UrlShortDto urlShortDto){
        System.out.println(urlShortDto);
        return urlService.urlIssue(urlShortDto);
    }

    @GetMapping("{url}")
    public void callUrl(HttpServletRequest request , HttpServletResponse response, @PathVariable("url")String encodingUrl) throws IOException {
        response.sendRedirect(urlService.callUrl(request, encodingUrl));

    }
}
