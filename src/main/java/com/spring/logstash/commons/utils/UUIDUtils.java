package com.spring.logstash.commons.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UUIDUtils {

  private RandomBasedGenerator RANDOM = Generators.randomBasedGenerator();
  private TimeBasedGenerator TYPE_2 =
      Generators.timeBasedGenerator(EthernetAddress.fromInterface());

  public String generateUUID() {
    return UUID.randomUUID().toString();
  }

  public String generateUUID(String source) {
    try {
      return generateType3UUID(source + RANDOM.generate().toString());
    } catch (Exception e) {
      return RANDOM.generate().toString();
    }
  }

  /**
   * Type 3 UUID Generation
   *
   * @throws UnsupportedEncodingException
   */
  public String generateType3UUID(String source) {
    try {
      byte[] bytes = source.getBytes("UTF-8");
      UUID uuid = UUID.nameUUIDFromBytes(bytes);
      return uuid.toString();
    } catch (Exception e) {
      return RANDOM.generate().toString();
    }
  }

  /**
   * Type 2 UUID Generation
   *
   * @throws UnsupportedEncodingException
   */
  public String generateType2UUID() {
    return TYPE_2.generate().toString();
  }

  /**
   * Type 4 UUID Generation
   *
   * @throws UnsupportedEncodingException
   */
  public String generateType4UUID() {
    return RANDOM.generate().toString();
  }

  /**
   * Type 5 UUID Generation
   *
   * @throws UnsupportedEncodingException
   * @see https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java/src/main/java/com/baeldung/uuid/UUIDGenerator.java
   */
  public String generateType5UUID(String source) {
    byte[] bytes;
    try {
      bytes = source.getBytes("UTF-8");
    } catch (Exception e) {
      bytes = source.getBytes();
    }
    UUID uuid = type5UUIDFromBytes(bytes);
    return uuid.toString();
  }

  private UUID type5UUIDFromBytes(byte[] source) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException nsae) {
      throw new InternalError("SHA-1 not supported", nsae);
    }
    byte[] bytes = Arrays.copyOfRange(md.digest(source), 0, 16);
    bytes[6] &= 0x0f; /* clear version */
    bytes[6] |= 0x50; /* set to version 5 */
    bytes[8] &= 0x3f; /* clear variant */
    bytes[8] |= 0x80; /* set to IETF variant */
    return constructType5UUID(bytes);
  }

  private UUID constructType5UUID(byte[] data) {
    long msb = 0;
    long lsb = 0;
    assert data.length == 16 : "data must be 16 bytes in length";

    for (int i = 0; i < 8; i++) {
      msb = (msb << 8) | (data[i] & 0xff);
    }

    for (int i = 8; i < 16; i++) {
      lsb = (lsb << 8) | (data[i] & 0xff);
    }
    return new UUID(msb, lsb);
  }
}
