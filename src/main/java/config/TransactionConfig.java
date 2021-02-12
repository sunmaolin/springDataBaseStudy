package config;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"mapper","pojo"})
//启动事务管理器
@EnableTransactionManagement
public class TransactionConfig implements TransactionManagementConfigurer {
    //数据源
    private DataSource dataSource;

    /**
     * 配置数据源
     * @return
     */
    @Bean("dataSource")
    public DataSource initDataSource(){
        if(dataSource != null){
            return dataSource;
        }
        Properties properties = new Properties();
        properties.setProperty("driverClassName","com.mysql.cj.jdbc.Driver");
        properties.setProperty("url","jdbc:mysql://localhost:3306/mybatisStudy?serverTimezone=Asia/Shanghai");
        properties.setProperty("username","root");
        properties.setProperty("password","000");
        properties.setProperty("maxActive","200");
        properties.setProperty("maxIdle","20");
        properties.setProperty("maxWait","30000");
        try {
            this.dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    /**
     * 初始化jdbcTemplate模板
     * @return
     */
    @Bean("jdbcTemplate")
    public JdbcTemplate initJdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(this.initDataSource());
        return jdbcTemplate;
    }

    /**
     * 实现TransactionManagementConfigurer的方法，使得返回数据库事务管理器
     * 这样在Spring上下文中使用事务注解@Transaction，Spring就知道该使用哪个数据库事务管理器了
     * @return
     */
    @Bean("transactionManager")
    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        //设置事务管理器管理的数据源
        dataSourceTransactionManager.setDataSource(this.initDataSource());
        return dataSourceTransactionManager;
    }
}
