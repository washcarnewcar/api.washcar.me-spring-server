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
import me.washcar.wcnc.domain.member.MemberRole;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	@NonNull
	private String SECRET;
	@SuppressWarnings("FieldCanBeLocal")
	private final int ACCESS_EXPIRE_MILLIS = 1000 * 60 * 60 * 2; // 2시간
	@SuppressWarnings("FieldCanBeLocal")
	private final int REFRESH_EXPIRE_MILLIS = 1000 * 60 * 60 * 5; // 5시간

	public String generateAccessToken(String uuid, MemberRole role) {
		return Jwts.builder()
			.setSubject(uuid)
			.claim("role", role.name())
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRE_MILLIS))
			.signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
			.compact();
	}

	@SuppressWarnings("unused")
	public String generateRefreshToken(String uuid, MemberRole role) {
		return Jwts.builder()
			.setSubject(uuid)
			.claim("role", role.name())
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
