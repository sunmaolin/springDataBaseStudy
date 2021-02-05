package controller;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pojo.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

/**
 * 测试spring封装jdbc的template
 */
public class JdbcTemplateTest {

    private static Logger logger = Logger.getLogger(JdbcTemplateTest.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate",JdbcTemplate.class);
//        testQuery(jdbcTemplate);
//        testInsert(jdbcTemplate);
//        testDelete(jdbcTemplate);
//        testUpdate(jdbcTemplate);
        testQueryList(jdbcTemplate);
    }

    /**
     * 测试jdbcTemplate
     * @param jdbcTemplate
     */
    public static void testQuery(JdbcTemplate jdbcTemplate){
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

    /**
     * 测试查询集合
     * @param jdbcTemplate
     */
    public static void testQueryList(JdbcTemplate jdbcTemplate){
        String sql = "select * from role";
        List<Role> list = jdbcTemplate.query(sql, new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                Role role = new Role(roleId,roleName);
                return role;
            }
        });
        list.stream().forEach(role->System.out.println(role.getRoleId()));
    }

    /**
     * 测试增加
     * @param jdbcTemplate
     */
    public static void testInsert(JdbcTemplate jdbcTemplate){
        String sql = "insert role (role_name,note) values (?,?)";
        String roleName = "孙茂林";
        String note = "测试jdbcTemplate增加";
        int row = jdbcTemplate.update(sql,roleName,note);
        System.out.println("受影响行数："+row);
    }

    /**
     * 测试删除
     * @param jdbcTemplate
     */
    public static void testDelete(JdbcTemplate jdbcTemplate){
        String sql = "delete from role where role_id = ?";
        int row = jdbcTemplate.update(sql,10);
        System.out.println("受影响行数："+row);
    }

    /**
     * 测试更新
     * @param jdbcTemplate
     */
    public static void testUpdate(JdbcTemplate jdbcTemplate){
        String sql = "update role set note=? where role_id=?";
        String note = "测试jdbcTemplate更新";
        int row = jdbcTemplate.update(sql,note,1);
        System.out.println("受影响行数："+row);
    }
}
