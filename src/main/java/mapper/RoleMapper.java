package mapper;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pojo.Role;

@Repository
public interface RoleMapper {
    /**
     * 注意，使用该注解时，使用的是反射，若该方法为静态或非public方法是调用失效的
     * @param roleId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public Role findRoleById(int roleId);
}
