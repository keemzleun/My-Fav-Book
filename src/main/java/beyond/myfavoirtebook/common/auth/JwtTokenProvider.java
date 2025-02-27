package beyond.myfavoirtebook.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.secretKeyRt}")
    private String secretKeyRt;

    @Value("${jwt.expirationRt}")
    private int expirationRt;


    public String createToken(String email){
        // claims는 사용자 정보(페이로드 정보)
        Claims claims = Jwts.claims().setSubject(email);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)   // 생성 시간
                .setExpiration(new Date(now.getTime() + expiration * 60 * 1000L))    // 만료 시간: 30분
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }

    public String createRefreshToken(String email){
        // claims는 사용자 정보(페이로드 정보)
        Claims claims = Jwts.claims().setSubject(email);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)   // 생성 시간
                .setExpiration(new Date(now.getTime() + expirationRt * 60 * 1000L))    // 만료 시간: 30분
                .signWith(SignatureAlgorithm.HS256, secretKeyRt)
                .compact();
        return token;
    }

}
