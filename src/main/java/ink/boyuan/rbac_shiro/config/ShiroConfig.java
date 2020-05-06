package ink.boyuan.rbac_shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wyy
 * @version 1.0
 * @Classname ShiroConfig
 * @date 2020/4/16 10:56
 * @description
 **/
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        System.out.println("ShiroFilterFactoryBean.shiroFilter() 执行了");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //这里设置了登录接口  如果访问了某个接口没有登录  就会调用这个接口（如果不是前后端分离就返回页面）
        shiroFilterFactoryBean.setLoginUrl("/pub/need_login");

        //如果是前后端分离则不需要这个
        //shiroFilterFactoryBean.setSuccessUrl();

        //这个是用户登录了  但没有权限 就会调用这个接口
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/need_permission");


        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("roleOrFilter",new CustomRolesOrAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        //拦截器路径 坑1：部分路径无法拦截，拦截效果时有时无，因为使用了hashMap 无序的应该使用linkedHashMap
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //匿名访问  游客访问路径
        filterChainDefinitionMap.put("/pub/**", "anon");

        //退出过滤器
        filterChainDefinitionMap.put("/user/logout", "logout");

        //登录用户才能访问
        filterChainDefinitionMap.put("/authc/**", "authc");

        //只有管理员才能访问
        filterChainDefinitionMap.put("/admin/**", "roleOrFilter[admin,root]");

        //视频修改权限
        filterChainDefinitionMap.put("/goods/video/update", "perms[video_update]");

        //坑2：过滤链是顺序执行的，从上而下，一般来讲/** 是放到最下面的

        //authc : url 必须通过认证才能访问
        //anon : url可以匿名访问
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());

        //设置自定义cacheManager
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    @Bean
    public CustomRealm customRealm() {
        //自定义realm 实现AuthorizingRealm
        CustomRealm customRealm = new CustomRealm();
        //设置加密
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;

    }

    @Bean
    public SessionManager sessionManager() {
        //新建自定义sessionManager 实现DefaultSessionManager
        CustomSessionManager customSessionManager = new CustomSessionManager();

        //设置自定义session持久化
        customSessionManager.setSessionDAO(redisSessionDAO());
        //设置session过期时间  默认30分钟  单位是毫秒
        customSessionManager.setGlobalSessionTimeout(180000);
        return customSessionManager;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置散列算法 ：这里设置的MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置多重加密算法 ：这里设置的是1次加密(md5(xxx))
        credentialsMatcher.setHashIterations(1);
        return credentialsMatcher;
    }


    /**
     * 配置RedisManager
     * @return
     */
    public RedisManager getRedisManager(){

        RedisManager redisManager = new RedisManager();
        redisManager.setPort(6379);
        return redisManager;
    }

    /**
     * 实例化cache
     * @return
     */
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();

        //设置缓存key过期时间，默认单位是S
        redisCacheManager.setExpire(20);
        redisCacheManager.setRedisManager(getRedisManager());
        return redisCacheManager;
    }


    /**
     * 自定义session持久化
     * @return
     */
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(getRedisManager());
        //设置sessionid 生成器
        redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
        //设置持久化超时时间  默认和下面这句话时间设置一样   customSessionManager.setGlobalSessionTimeout(200000);
        return redisSessionDAO;
    }


    /**
     * 管理shiro一些bean的生命周期 即bean初始化 与销毁
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 开启shiro注解的使用(shiro的注解 例如 @RequiresGuest)
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * 用来扫描上下文寻找所有的Advistor(通知器), 将符合条件的Advisor应用到切入点的Bean中，需要在LifecycleBeanPostProcessor创建后才可以创建
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }
}