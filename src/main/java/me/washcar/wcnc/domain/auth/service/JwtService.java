package me.washcar.wcnc.domain.auth.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import me.washcar.wcnc.domain.member.entity.Member;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	@NonNull
	private String SECRET;
	private final int ACCESS_EXPIRE_MILLIS = 1000 * 60 * 60 * 2; // 2시간
	private final int REFRESH_EXPIRE_MILLIS = 1000 * 60 * 60 * 5; // 5시간

	public String generateAccessToken(Member member) {
		return Jwts.builder()
			.setSubject(member.getUuid())
			.claim("role", member.getMemberRole())
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRE_MILLIS))
			.signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
			.compact();
	}

	public String generateRefreshToken(Member member) {
		return Jwts.builder()
			.setSubject(member.getUuid())
			.claim("role", member.getMemberRole())
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE_MILLIS))
			.signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
			.compact();
	}

	public Claims extractClaims(String token) throws RuntimeException {
		JwtParser jwtParser = Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
			.build();
		return jwtParser.parseClaimsJws(token).getBody();
	}

}
