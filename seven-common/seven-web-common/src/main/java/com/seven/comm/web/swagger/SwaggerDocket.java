package com.seven.comm.web.swagger;

import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/28 15:49
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SwaggerDocket {
    private String groupName;
    private String packages;
    private String token;

}
