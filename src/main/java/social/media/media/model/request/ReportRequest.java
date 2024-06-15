package social.media.media.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportRequest {

    private int createById;
    private String content_report;

}
