package timbo.homebrew.ziroomcrawler.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.time.Duration;

@ComponentScan("timbo.homebrew.ziroomcrawler")
@Configuration
@MapperScan(basePackages = "timbo", sqlSessionTemplateRef = "sqlSessionTemplate", annotationClass = Mapper.class)
@EnableScheduling
@EnableTransactionManagement
@PropertySource(value = "classpath:ziroomcrawler/config.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file://${CONFIG_HOME}/ziroomcrawler/config.properties", ignoreResourceNotFound = true)
public class ZiroomCrawlerConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName(env.getProperty("db.driver.classname"))
                .url(env.getProperty("db.url"))
                .username(env.getProperty("db.username"))
                .password(env.getProperty("db.password"))
                .build();

        hikariDataSource.setPoolName("Postgres-CP");
        return hikariDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        return sqlSessionFactory;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.setCallSettersOnNulls(true);
        configuration.setMapUnderscoreToCamelCase(true);
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder()
                .requestFactory(OkHttp3ClientHttpRequestFactory::new)
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10));
    }

}
