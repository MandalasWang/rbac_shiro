package ink.boyuan.rbac_shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ink.boyuan.rbac_shiro.mapper")
public class RbacShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacShiroApplication.class, args);
    }

}
