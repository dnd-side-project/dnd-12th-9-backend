package com.dnd.book.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//fixme : package명 변경시 같이 변경해줘야 함!!
@Configuration
@EntityScan(basePackages = "com.dnd")
@EnableJpaRepositories(basePackages = "com.dnd")
class CoreJpaConfig {

}
