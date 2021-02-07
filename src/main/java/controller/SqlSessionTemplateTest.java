package controller;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Role;

/**
 * mybatis提供的代码失控解决方案，与JdbcTemplate解决同一个问题
 */
public class SqlSessionTemplateTest {
    public static void main(String[] args) {
        //简单测试一下SqlSessionTemplate
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        SqlSessionTemplate sqlSessionTemplate = context.getBean("sqlSessionTemplate", SqlSessionTemplate.class);
        /**
         * 不符合面向对象，且idea没有提示，一般不用
         */
        Role role = sqlSessionTemplate.selectOne("mapper.RoleMapper.findRoleById",1);
        System.out.println(role);
    }
}
