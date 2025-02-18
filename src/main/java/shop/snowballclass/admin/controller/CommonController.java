package shop.snowballclass.admin.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.snowballclass.admin.common.ApiResponse;

@Tag(name = "공통 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/common")
public class CommonController {
    private final Environment env;

    @GetMapping("/health-check")
    public ApiResponse status() {
        return ApiResponse.success(String.format("Working in Admin Service %s",
                env.getProperty("tag.version")));
    }
}
