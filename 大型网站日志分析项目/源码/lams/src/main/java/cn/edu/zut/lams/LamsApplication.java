package cn.edu.zut.lams;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.zut.lams.mapper")
public class LamsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LamsApplication.class, args);
    }
}
