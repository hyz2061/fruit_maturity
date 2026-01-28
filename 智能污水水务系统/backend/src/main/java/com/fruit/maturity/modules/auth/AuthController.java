package com.fruit.maturity.modules.auth;

import com.fruit.maturity.common.ApiResult;
import com.fruit.maturity.modules.auth.dto.AuthMeResp;
import com.fruit.maturity.modules.auth.dto.LoginReq;
import com.fruit.maturity.modules.auth.dto.LoginResp;
import com.fruit.maturity.modules.auth.dto.RegisterReq;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/auth/login")
  public ApiResult<LoginResp> login(@Valid @RequestBody LoginReq req) {
    return ApiResult.ok(authService.login(req));
  }

  @PostMapping("/auth/register")
  public ApiResult<Void> register(@Valid @RequestBody RegisterReq req) {
    authService.register(req);
    return ApiResult.ok(null);
  }

  @GetMapping("/auth/me")
  public ApiResult<AuthMeResp> me() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return ApiResult.ok(authService.me(username));
  }
}
