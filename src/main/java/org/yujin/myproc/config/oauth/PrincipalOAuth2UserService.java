package org.yujin.myproc.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.yujin.myproc.config.auth.PrincipalDetail;
import org.yujin.myproc.config.oauth.provider.*;
import org.yujin.myproc.dto.RoleType;
import org.yujin.myproc.dto.UserDTO;
import org.yujin.myproc.entity.User;
import org.yujin.myproc.repository.UserRepository;
import org.yujin.myproc.service.UserService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> userInfo = oAuth2User.getAttributes();
        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UsrInfo oAuth2UsrInfo = null;
        if("google".equals(provider)) {
            oAuth2UsrInfo = new GoogleUserInfo(userInfo);
        } else if("facebook".equals(provider)) {
            oAuth2UsrInfo = new FacebookUserInfo(userInfo);
        } else if("naver".equals(provider)) {
            oAuth2UsrInfo = new NaverUserInfo((Map<String, Object>) userInfo.get("response"));
        } else if("kakao".equals(provider)) {
            oAuth2UsrInfo = new KakaoUserInfo(userInfo);
        } else {
            //
        }

        User existUser = userRepository.findByEmail(oAuth2UsrInfo.getEmail());

        User returnUser = null;

        if(existUser == null || existUser.getEmail().length() == 0) {

            UserDTO userDTO = UserDTO.builder()
                    .username(provider + "_" + oAuth2UsrInfo.getProviderId())
                    .email(oAuth2UsrInfo.getEmail())
                    .password(provider + "_" + oAuth2UsrInfo.getProviderId())
                    .role(RoleType.ROLE_USER.toString())
                    .oauth(provider)
                    .oauthid(oAuth2UsrInfo.getProviderId())
                    .build();

            UserDTO returnUserDTO = null;

            try {
                returnUserDTO = userService.saveUser(userDTO);
            } catch (Exception e) {
                //
            }

            returnUser = returnUserDTO.makeDtoToEntity(returnUserDTO);
        } else {
            returnUser = existUser;
        }

        //로그인 처리

        return new PrincipalDetail(returnUser, userInfo);
    }
}
