package com.infomanagesys.InfoManageSys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.infomanagesys.InfoManageSys.dao.mapper")
public class InfoManageSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoManageSysApplication.class, args);
	}
}
