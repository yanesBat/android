package com.karman.ebcard.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.karman.ebcard.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.karman.ebcard.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.EBCard.class.getName(), jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.EBCard.class.getName() + ".reviews", jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.EBCard.class.getName() + ".socialContacts", jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.EBCard.class.getName() + ".phoneNumbers", jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.EBCard.class.getName() + ".addresses", jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.EBCard.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.UserPropio.class.getName(), jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.UserPropio.class.getName() + ".personals", jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.UserPropio.class.getName() + ".wallets", jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.Category.class.getName() + ".subCategories", jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.Category.class.getName() + ".eBCards", jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.Address.class.getName(), jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.PhoneNumber.class.getName(), jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.SocialContact.class.getName(), jcacheConfiguration);
            cm.createCache(com.karman.ebcard.domain.Review.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
