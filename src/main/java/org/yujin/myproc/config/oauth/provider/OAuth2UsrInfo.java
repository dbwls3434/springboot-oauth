package org.yujin.myproc.config.oauth.provider;

import lombok.ToString;

public interface OAuth2UsrInfo {

    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
