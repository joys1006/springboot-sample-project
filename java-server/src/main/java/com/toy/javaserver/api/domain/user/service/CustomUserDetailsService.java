package com.toy.javaserver.api.domain.user.service;

import com.toy.javaserver.api.common.exception.ApiException;
import com.toy.javaserver.api.domain.user.dto.UserDto;
import com.toy.javaserver.api.domain.user.orm.UserOrm;
import com.toy.javaserver.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Security loadUserByUsername Method
     * @param userId 사용자 아이디
     * @return AccountResponse
     * @throws UsernameNotFoundException
     */
    @Override
    public UserOrm loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException("Not found", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()));
    }
}
