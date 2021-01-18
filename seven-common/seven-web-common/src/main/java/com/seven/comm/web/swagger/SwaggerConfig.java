package com.seven.comm.web.swagger;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/28 15:47
 */

public abstract class SwaggerConfig {
    private SwaggerDocket swaggerDocket;
    private boolean enable = true;

    public SwaggerConfig(SwaggerDocket swaggerDocket) {
        this.swaggerDocket = swaggerDocket;
    }

    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo())
                // 是否开启
                .enable(enable).select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage(swaggerDocket.getPackages()))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any()).build().pathMapping("/");
    }

    protected abstract ApiInfo apiInfo();
}
