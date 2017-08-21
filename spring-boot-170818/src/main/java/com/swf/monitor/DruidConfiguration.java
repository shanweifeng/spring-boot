package com.swf.monitor;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

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
    @Primary  
    public DataSource dataSource() {  
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
    //@Bean
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
       return servletRegistrationBean;
    }
   
    /**
     * 注册一个：filterRegistrationBean
     * @return
     */
    //@Bean
    public FilterRegistrationBean druidStatFilter2(){
      
       FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
      
       //添加过滤规则.
       filterRegistrationBean.addUrlPatterns("/*");
      
       //添加不需要忽略的格式信息.
       filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
       return filterRegistrationBean;
    }
}
