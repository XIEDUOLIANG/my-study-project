package org.xdl.springboot.oauth2.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 加密自定义类
 * @author XieDuoLiang
 * @date 2021/10/20 上午11:13
 */
@Component
public class HashPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return String.valueOf(charSequence.hashCode());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(encode(charSequence));
    }
}
