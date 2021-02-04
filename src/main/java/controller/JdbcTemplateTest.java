package controller;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pojo.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 测试spring封装jdbc的template
 */
public class JdbcTemplateTest {

    private static Logger logger = Logger.getLogger(JdbcTemplateTest.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate",JdbcTemplate.class);
        testQuery(jdbcTemplate);
    }

    /**
     * 测试jdbcTemplate
     * @param jdbcTemplate
     */
    static void testQuery(JdbcTemplate jdbcTemplate){
        String sql = "select role_id,role_name,note from role where role_id = 1";
        Role role = jdbcTemplate.queryForObject(sql, new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
                role.setNote(rs.getString("note"));
                return role;
            }
        });
        logger.info(role.getRoleName());
    }
}
