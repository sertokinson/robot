package ru.sertok.robot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class Config {
    private final String DB_PROPERTY = "hibernate.hbm2ddl.auto";

    @Autowired
    private Environment environment;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) throws IOException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("ru.sertok.robot.entity");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty(DB_PROPERTY, getDB_PROPERTY());


        em.setJpaProperties(properties);

        return em;
    }

    private String getDB_PROPERTY() throws IOException {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0 && activeProfiles[0].equals("dev"))
            return "create";
        String pathName = System.getProperty("java.io.tmpdir") + "robot";
        File file = new File(pathName, "0.21.txt");
        if (!file.exists()) {
            new File(pathName).mkdir();
            file.createNewFile();
            return "create";
        }
        return "update";
    }
}