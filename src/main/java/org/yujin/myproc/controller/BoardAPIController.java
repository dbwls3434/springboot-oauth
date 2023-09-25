package org.yujin.myproc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yujin.myproc.config.auth.PrincipalDetail;

@RestController
@RequestMapping("/api/board")
public class BoardAPIController {

    @GetMapping("/list")
    public @ResponseBody String listPage(Authentication authentication) {
                                        //@AuthenticationPrincipal UserDetail userDetail) {
                                        //@AuthenticationPrincipal OAuth2User oAuth2User) {
                                        //@AuthenticationPrincipal PrincipalDetail principalDetail) {
        //
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();

        return ("잘 되었습니다." + authentication.getName());
    }
}
