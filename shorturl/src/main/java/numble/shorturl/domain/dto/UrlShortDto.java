package numble.shorturl.domain.dto;

import lombok.Data;
import numble.shorturl.domain.Status;

@Data
public class UrlShortDto {
    private String url;
    private int expireDate;
    private Status status;
}
