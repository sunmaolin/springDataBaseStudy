package controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 编程式事务测试
 */
public class ProgrammerTransactionTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        //事务定义类
        TransactionDefinition def = new DefaultTransactionDefinition();
        PlatformTransactionManager platformTransactionManager = context.getBean("transactionManager",PlatformTransactionManager.class);
        //获取事务的状态
        TransactionStatus status = platformTransactionManager.getTransaction(def);
        //指定sql
        String sql = "update role set note='测试事务' where role_id=?";
        try{
            int row = jdbcTemplate.update(sql,1);
//            if(true){
//                throw new RuntimeException("测试事务异常！");
//            }
            //提交事务
            platformTransactionManager.commit(status);
            System.out.println("生效行数："+row);
        }catch (Exception e){
            e.printStackTrace();
            //回滚事务
            platformTransactionManager.rollback(status);
        }
    }
}
