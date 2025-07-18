//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.repository;

import java.time.Duration;
import java.util.Objects;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {
    public RedisTemplate<String, Object> redisTemplate;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveOtp(String userId, String otp) {
        String otpKey = "OTP:" + userId;
        String retryKey = "RETRY:" + userId;
        this.redisTemplate.opsForValue().set(otpKey, otp, Duration.ofMinutes(5L));
        this.addCooldown(userId, 120L);
        this.redisTemplate.opsForValue().set(retryKey, "0", Duration.ofMinutes(10L));
    }

    public int retryCount(String userId) {
        String retryKey = "RETRY:" + userId;
        String temp = Objects.requireNonNull(this.redisTemplate.opsForValue().get(retryKey)).toString();
        int attempt = Integer.parseInt(temp) + 1;
        this.redisTemplate.opsForValue().set(retryKey, "" + attempt);
        return attempt;
    }

    public void resetRetryCount(String userId) {
        this.redisTemplate.delete("RETRY:" + userId);
        this.redisTemplate.delete("OTP:" + userId);
    }

    public String getOtp(String userId) {
        String key = "OTP:" + userId;
        return (String)this.redisTemplate.opsForValue().get(key);
    }

    public void deleteOtp(String userId) {
        String key = "OTP:" + userId;
        this.redisTemplate.delete(key);
    }

    public boolean validForOps(String userId) {
        String key = "COOLDOWN:" + userId;
        return Boolean.TRUE.equals(this.redisTemplate.hasKey(key));
    }

    public void clear(String userId) {
        this.redisTemplate.delete("OTP:" + userId);
        this.redisTemplate.delete("COOLDOWN:" + userId);
        this.redisTemplate.delete("RETRY:" + userId);
    }

    public void addCooldown(String userId, long duration) {
        String cooldownKey = "COOLDOWN:" + userId;
        this.redisTemplate.opsForValue().set(cooldownKey, "1", Duration.ofSeconds(duration));
    }

    public String getTimeoutTime(String userId) {
        String key = "COOLDOWN:" + userId;
        if (Boolean.TRUE.equals(this.redisTemplate.hasKey(key))) {
            Long expire = this.redisTemplate.getExpire(key);
            Duration duration = Duration.ofSeconds(expire);
            int hours = duration.toHoursPart();
            int minutes = duration.toMinutesPart();
            int seconds = duration.toSecondsPart();
            StringBuilder time = new StringBuilder();
            if (hours > 0) {
                time.append(hours).append("h ");
            }

            if (minutes > 0) {
                time.append(minutes).append("m ");
            }

            if (seconds > 0) {
                time.append(seconds).append("s");
            }

            return time.toString().trim();
        } else {
            return "-1";
        }
    }
}
