package com.genius.flashcard.config;


import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.genius.flashcard.CurrentUserHandlerMethodArgumentResolver;
import com.genius.flashcard.api.auth.dto.User;
import com.genius.flashcard.api.v1.cardpacks.dto.Cardpack;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyActLog;
import com.genius.flashcard.api.v1.cardpacks.dto.StudyStatus;
import com.genius.flashcard.interceptor.AuthInterceptor;


@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
	@Value("${app.db.driver}")
	String DB_DRIVER;

	@Value("${app.db.url}")
	String DB_URL;

	@Value("${app.db.username}")
	String DB_USERNAME;

	@Value("${app.db.password}")
	String DB_PASSWORD;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(authInterceptor());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(currentUserHandlerMethodArgumentResolver());
	}

	@Bean
	public CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver() {
		return new CurrentUserHandlerMethodArgumentResolver();
	}

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cmfb.setShared(true);
		return cmfb;
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName(DB_DRIVER);
		dataSource.setUrl(DB_URL);
		dataSource.setUsername(DB_USERNAME);
		dataSource.setPassword(DB_PASSWORD);

		return dataSource;
	}

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
		SessionFactory sf = builder
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Cardpack.class)
				.addAnnotatedClass(StudyStatus.class)
				.addAnnotatedClass(StudyActLog.class)
//				.addAnnotatedClass(StudyActLogStatistics.class)
				.buildSessionFactory();

		return sf;
	}

	@Bean
	public HibernateTemplate hibernateTemplate() {
		HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory());
		return hibernateTemplate;
	}
}
