package online.githuboy.retwis.domain;

import lombok.Builder;
import lombok.Data;

/**
 * the user pojo
 *
 * @author suchu
 * @since 2019/3/23 13:49
 */
@Data
@Builder
public class User {

    String userId;

    String userName;
}
