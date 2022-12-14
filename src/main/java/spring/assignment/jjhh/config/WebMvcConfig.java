package spring.assignment.jjhh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
//	@Value(value = "${uploadPath}")
//    private String uploadPath;
	
	@Value("${resource.location}")
    private String resourceLocation;
	
	@Value("${resource.filelocation}")
    private String resourcefileLocation;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    	registry.addResourceHandler("/assets/img/data/**").addResourceLocations(uploadPath);
    	registry.addResourceHandler("/images/**").addResourceLocations(resourceLocation);
    	registry.addResourceHandler("/data/**").addResourceLocations(resourcefileLocation);
    }

}
