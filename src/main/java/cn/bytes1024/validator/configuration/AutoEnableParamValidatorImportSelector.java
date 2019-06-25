package cn.bytes1024.validator.configuration;


import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * import selector
 *
 * @author 江浩
 */
public class AutoEnableParamValidatorImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //TODO 目前只有一个值
        return new String[]{HibernateConfigurationValidator.class.getName()};
    }
}
