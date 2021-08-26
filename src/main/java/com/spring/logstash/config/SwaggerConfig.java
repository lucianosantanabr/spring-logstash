package com.spring.logstash.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

  private @Autowired(required = false)
  BuildProperties build;

  @Value("${spring.application.name:app-spring-logstash}")
  private String application;

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)//
        .select()//
        .apis(RequestHandlerSelectors.basePackage("com.spring.logstash"))//
        .build()//
        .useDefaultResponseMessages(false)//
        .tags(//
            new Tag("Logs", "Endpoints for Logs Management"))//
        .apiInfo(info());

  }

  private ApiInfo info() {
    return new ApiInfoBuilder()//
        .title("Spring Logstash API")//
        .version(build != null ? build.getVersion() : null)//
        .description("RESTful API for ".concat(application))//
        .contact(new Contact(//
            "Luciano S. Santana", //
            "https://github.com/lucianosantanabr", //
            "lucianosantanabr@gmail.com"))
        .build();
  }

  @Bean
  public UiConfiguration uiConfig() {
    return UiConfigurationBuilder//
        .builder() //
        .deepLinking(true)//
        .maxDisplayedTags(6)//FIXME
        .displayOperationId(true)//
        .defaultModelsExpandDepth(1)//
        .defaultModelExpandDepth(1)//
        .defaultModelRendering(ModelRendering.EXAMPLE)//
        .displayRequestDuration(false)//
        .docExpansion(DocExpansion.NONE)//
        .filter(true)//
        .operationsSorter(OperationsSorter.ALPHA)//
        .showExtensions(false)//
        .tagsSorter(TagsSorter.ALPHA)//
        .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)//
        .validatorUrl(null)//
        .build();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry//
        .addResourceHandler("swagger-ui.html")//
        .addResourceLocations("classpath:/META-INF/resources/");
    registry//
        .addResourceHandler("/webjars/**")//
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController(//
        "/api/v2/api-docs", //
        "/v2/api-docs");
    registry.addRedirectViewController(//
        "/api/swagger-resources/configuration/ui", //
        "/swagger-resources/configuration/ui");
    registry.addRedirectViewController(//
        "/api/swagger-resources/configuration/security", //
        "/swagger-resources/configuration/security");
    registry.addRedirectViewController(//
        "/api/swagger-resources", //
        "/swagger-resources");
  }

}
