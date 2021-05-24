package com.epam.esm.security;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {


    List<Locale> LOCALES = Arrays.asList(new Locale("en"), new Locale("ru"));


    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (!StringUtils.hasText(request.getHeader("Accept-Language"))) {
            return Locale.getDefault();
        }
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
        Locale locale = Locale.lookup(list, LOCALES);
        return locale;
    }
}
