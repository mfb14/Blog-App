package com.project.questapp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.questapp.business.concretes.UserDetailsServiceImpl;






/*
 * Frontend den backend tarafına bir request geldiğinde spring security nin yaptığı filtrelemeler var.
 * Bunlara ek olarak bizde ekstra bir Jwt filter ekliyoruz filtreleme daha ekliyoruz
 * 
 * */
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	UserDetails userDetails;
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	/*
	 * Gelen request autherize olmuş mu olmamış mı onu kontrol etmek için kullanıyoruz
	 * SecurityContextHolder => Local Thread objesi yaratıyyor ve bu user için userName password gibi gerekli authenticate ettiği objeleri tutuyor
	 * Biz bu contextin içerisine authenticate ettiğimiz requesti koyduğumuzda filter işleminin devamında bu istek authenticate edilmiş olur
	 * Bunun sonucunda arka planda servişe erişebilecek 
	 * */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
						String jwtToken = extractJwtFromRequest(request);
						//gelen token dolu mu  ve valid mi diye kontrol ediyoruz.
						//Token validse ve doluysa tokenımız içerisinden user ı alacaz
						if (StringUtils.hasText(jwtToken)&& jwtTokenProvider.validateToken(jwtToken)) {
							Long userId = jwtTokenProvider.getUserIdFromJwt(jwtToken);
							userDetails = userDetailsService.loadByUserId(userId);
							//Eğer böylle bi user varsa bu requesti authenticate ederiz
							if (userDetails != null) {
								UsernamePasswordAuthenticationToken autToken = 
										new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
								autToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
								SecurityContextHolder.getContext().setAuthentication(autToken);
							}
						}
			
		} catch (Exception e) {
			return;
		}
		filterChain.doFilter(request, response);
	}

	/*
	 * Gelen tokenımızı gelen request header ından extract etmek için
	 *İstek atarken Jwt tokenimizi headerlarda Autherization header ı altında gönderiyoruz
	 *(bearer(String) ve yanında Token olacak şekilde)
	 * */
	private String extractJwtFromRequest(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		//Bearer dolu mu  geldi kontrol ediyoruz(doğru mu yazılmış onu da kontrol ederiz)
		if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
			//Başındaki 'Bearer' ı atıyoruz ve kalan Jwt token kısmını dönüyoruz
			return bearer.substring("Bearer".length()+1);
		}
		return null;
	}

}
