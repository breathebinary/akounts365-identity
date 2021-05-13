package com.breathebinary.finava.security.jwt

import com.breathebinary.finava.security.ANONYMOUS
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.util.ReflectionTestUtils
import tech.jhipster.config.JHipsterProperties
import java.security.Key
import java.util.Date

private const val ONE_MINUTE: Long = 60000

class TokenProviderTest {
}
