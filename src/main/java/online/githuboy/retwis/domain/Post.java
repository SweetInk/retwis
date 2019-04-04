package online.githuboy.retwis.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author suchu
 * @since 2019/4/4 9:50
 */
@Data
@Builder
public class Post {
    private String userName;

    private String content;

    private String time;
}
