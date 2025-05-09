package com.slotwash.security.provider;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.slotwash.errors.ErrorConstants;
import com.slotwash.models.User;
import com.slotwash.repository.UserRepository;
import com.slotwash.security.ActiveUser;
import com.slotwash.utils.SecurityUtils;

public class ActiveUserArgumentResolver implements HandlerMethodArgumentResolver{

    @Autowired UserRepository userRepository;

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.getParameterAnnotation(ActiveUser.class)!=null && parameter.getParameterType() == User.class;
    }

    @Override
    @Nullable
    public Object resolveArgument(@NonNull MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        return userRepository.findById(SecurityUtils.getCurrentUserLogin()).orElseThrow(()-> new BadRequestException(ErrorConstants.ERR_INVALID_USER));
    }
    
}
