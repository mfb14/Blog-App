package com.project.questapp.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/*
 * To Generate JwtToken
 * */
@Component
public class JwtTokenProvider {
	
	//Token ımızın bu application a özel oluşturacağımız key
	@Value("${questapp.app.secret}")
	private String APP_SECRET;
	
	//Bu tokenlar kaç saniyede bir geçerliliğini yitiriyor.
	@Value("${questapp.app.expires.in}")
	private Long EXPIRES_IN;
	
	/*
	 * Tokenımızın generate olacağı method
	 * @param Authentiacation objesi
	 * getPrincipal()=> Authenticate edeceğimiz userı almak için kullandık.
	 * ------------Jwts => bizim dependency olarak eklediğimiz-------------
	 * .builder() => Tokenımızı build etmeye yarıyor
	 * .setSubject() =>  Authenticate edeceğimiz userın id sini verdik
	 * .setIssuedAt() => Ne zaman key oşuşturldu
	 * .
	 * .compact()=>Oluşturmaya yarar
	 * 	 * */
	
	public String generateJwtToken(Authentication auth) {
		
		//JwtUserDetails luşturup Authenticate objesinin içinden user ımızı alıp cast ediyoruz
		JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
		//Ne zaman expire olacağını hesaplamak için şimdiki zamanı alıp oluşturuduğumuz EXPIRES_IN i ekliyoruz
		Date date = new Date(new Date().getTime() + EXPIRES_IN);
		
		return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
				.setIssuedAt(new Date()).setExpiration(date)
				.signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
	}
	
	/*
	 * Oluşturulan key'e göre token i çözüyor ve elinde bir body(obje)(subject,expiration date) kalıyor. Bu body den de user id yi alıyoruz
	 * @param token Bize gelen Jwt tokenimiz
	 * */
	public Long getUserIdFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
	/*
	 * Token in expired olup olmadığını kontrol etmek için
	 * */
	private boolean isTokenExpired(String token) {
		
		Date expireDate = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
		return expireDate.before(new Date());
	}
	
	/*
	 * Tokenımızı validate etmek için
	 * */
	public boolean validateToken(String token) {
		try {
			//Burda eğer parse edebiliyosak bu tokenı bu demek oluyor bu bizim uygulamamızın oluşturduğu bir key oluyor
			Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
			return !isTokenExpired(token);
		
		} catch (SignatureException e) {
			return false;
		}catch (MalformedJwtException e) {
			return false;
		}catch (ExpiredJwtException e) {
			return false;
		}catch (UnsupportedJwtException e) {
			return false;
		}catch (IllegalArgumentException e) {
			return false;
		}
	}

	
}
