package com.akera.config;


import com.akera.filter.DefaultAccessDeniedHandler;
import com.akera.filter.DefaultAuthenticationEntryPointHandler;
import com.akera.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //开启Security
@EnableGlobalMethodSecurity(prePostEnabled = true) //AOP
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Spring会自动寻找同样类型的具体类注入，这里就是JwtUserDetailsServiceImpl了
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				// 设置UserDetailsService
				.userDetailsService(this.userDetailsService);
		//自定义设置登录账号密码
		authenticationManagerBuilder.inMemoryAuthentication()
		.withUser("username").password("password").roles("ADMIN");
				
		// 使用BCrypt进行密码的hash
		// .passwordEncoder(passwordEncoder());
	}
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// 由于使用的是JWT，我们这里不需要csrf
				.csrf().disable()

				// 基于token，所以不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

				// 允许对于网站静态资源的无授权访问
				.antMatchers(HttpMethod.GET, "/", "/*.html", 
						"/favicon.ico", 
						"/**/*.html",
						"/**/*.css",
						"/**/*.js",
						"/**/*.map",
						"/**/*.ttf",
						"/**/*.eot",
						"/**/*.woff2",
						"/**/*.woff",
						"/**/*.svg",
						"/**/*.jpg",
						"/**/*.png")
				.permitAll()
				//需要admin权限
				.antMatchers("/**").anonymous()
				.antMatchers("/user/login").hasRole("ADMIN")
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated()
				//登录页面可以访问
			//	and().formLogin().loginPage("/login").permitAll().

		// 禁用缓存
		.and().headers().cacheControl();
		//登出处理
		//httpSecurity.logout().logoutUrl("/auth/logout");
		//自定义登录逻辑
		//	.logoutSuccessHandler(new MyLogoutHandler());
		// 添加JWT filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.exceptionHandling()
        .accessDeniedHandler(new DefaultAccessDeniedHandler())
        .authenticationEntryPoint(new DefaultAuthenticationEntryPointHandler());
	}
	
	@Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
	
}
