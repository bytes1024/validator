package cn.bytes1024.validator.configuration;


import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * All rights Reserved
 * 加载器
 * @author maliang
 * @date 2019/1/26 12:58
 * @version V1.0
 */
public class AutoEnableParamValidatorImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //TODO 目前只有一个值
        return new String[]{HibernateConfigurationValidator.class.getName()};
    }
}
