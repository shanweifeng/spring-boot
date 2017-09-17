package com.swf.monitor;

import java.sql.SQLException;
import java.util.EventListener;

import javax.annotation.PostConstruct;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.swf.monitor.listener.globalListener;

import lombok.Data;

/**
 * 监控 第二种监控druid方式
 * @author Administrator
 *
 */
@Configuration
//@ConfigurationProperties //此处不能有这个注解否则会出现异常
@Data
public class DruidConfiguration {
	
	@PostConstruct
	public void test() {
		return ;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(DruidConfiguration.class);  
	//下面这些属性如果没有注入的话 页面查询会没有数据的
	@Value("${spring.datasource.driver-class-name}")  
    private String driverClassName;  
    @Value("${spring.datasource.url}")  
    private String url;  
    @Value("${spring.datasource.username}")  
    private String username;  
    @Value("${spring.datasource.password}")  
    private String password;  
    @Value("${spring.datasource.initialSize}")  
    private int initialSize;  
    @Value("${spring.datasource.minIdle}")  
    private int minIdle;  
    @Value("${spring.datasource.maxActive}")  
    private int maxActive;  
    @Value("${spring.datasource.maxWait}")  
    private int maxWait;  
    @Value("${spring.datasource.validationQuery}")  
    private String validationQuery;  
    @Value("${spring.datasource.testOnBorrow}")  
    private boolean testOnBorrow;  
    @Value("${spring.datasource.testOnReturn}")  
    private boolean testOnReturn;  
    @Value("${spring.datasource.testWhileIdle}")  
    private boolean testWhileIdle;  
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")  
    private int timeBetweenEvictionRunsMillis;  
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")  
    private int minEvictableIdleTimeMillis;  
    //@Value("${spring.datasource.removeAbandoned}")  
    //private boolean removeAbandoned;  
    //@Value("${spring.datasource.removeAbandonedTimeout}")  
    //private int removeAbandonedTimeout;  
    //@Value("${spring.datasource.logAbandoned}")  
    //private boolean logAbandoned;  
    @Value("${spring.datasource.filters}")  
    private String filters;  
    @Value("${spring.datasource.poolPreparedStatements}")  
    private boolean poolPreparedStatements;  
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
  
    @Bean  
    @Primary//如果同时进行了编程式的注入和配置的注入，配置的就无效了。
    public DataSource dataSource() {//可以将driverClassName属性配置到方法参数中如@Value("${spring.datasource.url}") String url
        DruidDataSource datasource = new DruidDataSource();  
  
        datasource.setDriverClassName(driverClassName);  
        datasource.setUrl(url);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        //其它配置  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        try {
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            LOGGER.error("druid configuration initialization filter", e);  
        }  
        return datasource;  
    } 
	
	/**
     * 注册一个StatViewServlet  这样的方式不需要添加注解：@ServletComponentScan
     * @return
     */
    @Bean
    public ServletRegistrationBean DruidStatViewServle2(){
       //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
       ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    	/*ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
    	servletRegistrationBean.setServlet(new TestServlet());
    	servletRegistrationBean.setUrlMappings(new ArrayList<String>() {{add("/druid2/*");}});
    	servletRegistrationBean.setLoadOnStartup(1);*/
       //添加初始化参数：initParams
      
       //白名单：
       //servletRegistrationBean.addInitParameter("allow","127.0.0.1");
       //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
       //servletRegistrationBean.addInitParameter("deny","192.168.1.73");
       //登录查看信息的账号密码.
       servletRegistrationBean.addInitParameter("loginUsername","admin");
       servletRegistrationBean.addInitParameter("loginPassword","123456");
       //是否能够重置数据.
       servletRegistrationBean.addInitParameter("resetEnable","false");
       System.out.println("DruidConfiguration.DruidStatViewServle2()");
       return servletRegistrationBean;
    }
   
    /**
     * 注册一个：filterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter2(){
      
       FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
      
       //添加过滤规则.
       filterRegistrationBean.addUrlPatterns("/*");
      
       //添加不需要忽略的格式信息.
       filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
       System.out.println("DruidConfiguration.druidStatFilter2()");
       //filterRegistrationBean.setEnabled(false); //怎么取消  Filter自动注册,不会添加到FilterChain中. 如果不想将@Bean注册的类与已经存在的Bean平级，但是又需要将其注入到filter中而不进入filterChain中，使用当前设置原filter需要@Bean注解
       return filterRegistrationBean;
    }
    
    //@Bean //通过代码注册的servlet、filter、listener不需要再对应的实现类上面添加注解  只有通过@ServletComponentScan扫描的东西才需要在实现类上面添加对应的注解@WebServlet @WebFilter @WebListener
    public ServletListenerRegistrationBean<EventListener> listener(){// 代码注册listener
    	ServletListenerRegistrationBean<EventListener> sl = new ServletListenerRegistrationBean<EventListener>();
    	sl.setListener(new globalListener());
    	//registrationBean.setOrder(1);  标注执行顺序
    	System.out.println("DruidConfiguration.listener()");
    	return sl;
    }
}
