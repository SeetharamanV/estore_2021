package com.seetharamanv.estores.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseMessageBuilder
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@Configuration
class SwaggerConfig {
    @Bean
    fun swaggerDocket(): Docket {
        val res200 = ResponseMessageBuilder()
                .code(200)
                .message("Ok")
                .build()
        val res201 = ResponseMessageBuilder()
                .code(201)
                .message("Created")
                .build()
        val res204 = ResponseMessageBuilder()
                .code(204)
                .message("Resource Updated")
                .build()
        val res400 = ResponseMessageBuilder()
                .code(400)
                .message("Bad Request")
                .build()
        val res401 = ResponseMessageBuilder()
                .code(401)
                .message("Unauthorized")
                .build()
        val res403 = ResponseMessageBuilder()
                .code(403)
                .message("Forbidden")
                .build()
        val res404 = ResponseMessageBuilder()
                .code(404)
                .message("Not found")
                .build()
        val res500 = ResponseMessageBuilder()
                .code(500)
                .message("Internal server error")
                .build()
        val apiInfo = ApiInfoBuilder()
                .title("Estores API")
                .description("Estores API endpoints")
                .build()

        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.seetharamanv.estores"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage( RequestMethod.GET, listOf(res200,res400,res404,res500))
                .globalResponseMessage(RequestMethod.POST, listOf(res201,res401,res403,res500))
                .globalResponseMessage(RequestMethod.PUT, listOf(res200,res401,res403,res500))
                .globalResponseMessage(RequestMethod.DELETE, listOf(res204,res401,res403,res404,res500))
                .apiInfo(apiInfo)
    }
}
