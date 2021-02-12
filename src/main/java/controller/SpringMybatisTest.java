package controller;

import mapper.RoleMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Role;

public class SpringMybatisTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        RoleMapper roleMapper = context.getBean(RoleMapper.class);
        Role role = roleMapper.findRoleById(1);
        System.out.println(role);
    }
}
