package backend.library.config;

import backend.library.entity.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private String theAllowOrigins = "http://localhost:3000";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration repositoryRestConfiguration, CorsRegistry corsRegistry){
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH,HttpMethod.POST};

        repositoryRestConfiguration.exposeIdsFor(Book.class);

        disableHttpMethods(Book.class,repositoryRestConfiguration,theUnsupportedActions);

        corsRegistry.addMapping(repositoryRestConfiguration.getBasePath()+"/**").allowedOrigins(theAllowOrigins);
    }

    private void disableHttpMethods(Class<Book> bookClass, RepositoryRestConfiguration repositoryRestConfiguration, HttpMethod[] theUnsupportedActions) {
        repositoryRestConfiguration.getExposureConfiguration().forDomainType(bookClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }
}
