package org.springsource.sawt.ioc.manybeans.bfpp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.util.Assert;
import org.springsource.sawt.ioc.manybeans.aop.MethodTimeLoggingAspect;

/**
 * This simply ensures that all the required beans are defined in your
 * application context.
 * <p/>
 * Installs:
 * <OL>
 * <LI>{@link AnnotationAwareAspectJAutoProxyCreator}</LI>
 * <LI> {@link org.springsource.sawt.ioc.manybeans.aop.MethodTimeLoggingAspect}</LI>
 * </OL>
 */
public class SoxComplianceSuite implements BeanFactoryPostProcessor {

	private Log log = LogFactory.getLog(getClass());

	// often a very useful question to ask: has this bean been registered
	// before?
	private boolean definitionExists(Class<?> clzz,
			ConfigurableListableBeanFactory r) {
		boolean exists = r.getBeansOfType(clzz).size() != 0;
		if (log.isDebugEnabled())
			log.debug(clzz.getSimpleName() + " is" + (exists ? " " : " not ")
					+ "already registered");
		return exists;
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {

		Assert.isInstanceOf(BeanDefinitionRegistry.class, beanFactory);
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

		if (!definitionExists(AnnotationAwareAspectJAutoProxyCreator.class,
				beanFactory))
			BeanDefinitionReaderUtils.registerWithGeneratedName(
					new RootBeanDefinition(
							AnnotationAwareAspectJAutoProxyCreator.class),
					registry);

		if (!definitionExists(MethodTimeLoggingAspect.class, beanFactory))
			BeanDefinitionReaderUtils.registerWithGeneratedName(
					new RootBeanDefinition(MethodTimeLoggingAspect.class),
					registry);
	}
}
