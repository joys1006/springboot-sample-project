package com.toy.javaserver.api.common.converter;

import com.toy.javaserver.api.common.enums.BaseEnum;
import com.toy.javaserver.api.configuration.WebMvcConfiguration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Objects;

/**
 * 컨트롤러 메서드에 @RequestParam 으로 정의된 파라미터의 경우 Jackson 컨버터가 아닌
 * Spring 컨버터(ConverterFactory 구현체)가 동작하므로 따로 만들어준다.
 * (FormatterRegistry에 등록해야 된다.)
 * @see WebMvcConfiguration
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter(targetType);
    }

    private static class StringToEnumConverter<T extends BaseEnum> implements Converter<String, T> {
        private Class<T> enumType;

        public StringToEnumConverter(Class<T> enumType) { this.enumType = enumType; }

        public T convert(String source) {
            if (Objects.isNull(source)) return null;

            // 스트링 값으로 변환하여 검증
            return BaseEnum.getStringToEnumConverterEquals(enumType, source.toUpperCase());
        }
    }
}
