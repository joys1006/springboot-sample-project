package com.toy.javaserver.api.common.utils.response;

import com.toy.javaserver.api.common.annotation.EnableApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ApiResponseImportRegistrar implements ImportBeanDefinitionRegistrar {

    private static Set<String> includes = new HashSet<>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetaData, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String, Object> metaData = annotationMetaData.getAnnotationAttributes(EnableApiResponse.class.getName());
        includes.addAll(Arrays.asList((String[]) metaData.get("includes")));

        if (annotationMetaData instanceof StandardAnnotationMetadata) {
            // @Configuration 에서 온 경우
            includes.add(ClassUtils.getPackageName(annotationMetaData.getClassName()) + ".**");
        } else {
            String introspectedClass = annotationMetaData.getClassName();
            includes.add(introspectedClass);
        }

        String adviceName = ApiResponseBodyAdvice.class.getSimpleName();

        if (beanDefinitionRegistry.containsBeanDefinition(adviceName)) {
            beanDefinitionRegistry.removeBeanDefinition(adviceName);
        }

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ApiResponseBodyAdvice.class);

        builder.addConstructorArgValue(includes.toArray());

        beanDefinitionRegistry.registerBeanDefinition(adviceName, builder.getBeanDefinition());
    }
}
