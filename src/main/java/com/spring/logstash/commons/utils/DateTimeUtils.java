package com.spring.logstash.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

  private static final Locale localeBr = new Locale("pt", "BR");

  public static long now() {
    return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
  }

  // public static boolean isPeriodGreaterThanInterval(Date startDate, Date endDate, int hours) {
  // final int MILLISECONDS_TO_HOUR = 1000 * 60 * 60;
  // Date diff = new Date(endDate.getTime() - startDate.getTime());
  // return (diff.getTime() / MILLISECONDS_TO_HOUR) > hours;
  // }

  public static LocalDateTime addHoursToDate(LocalDateTime date, int hours) {
    return date.plusHours(hours);
  }

  public static LocalDateTime addMinutesToDate(LocalDateTime date, int minutes) {
    return date.plusMinutes(minutes);
  }

  public static String newEpochWithoutMilisseconds() {
    LocalDateTime date = LocalDateTime.now();
    ZoneId zoneId = ZoneId.systemDefault();
    long epoch = date.atZone(zoneId).toEpochSecond();
    return String.valueOf(epoch);
  }

  public static String removeMillisecondsFromEpoch(String epoch) {
    epoch =
        (epoch == null || epoch.length() <= 3) ? null : (epoch.substring(0, epoch.length() - 3));
    return epoch;
  }

  public static String addSlashToCardExpiryDate(String ccExpiryDate) {
    if (ccExpiryDate.length() >= 6) {
      char[] charArray = ccExpiryDate.toCharArray();
      ccExpiryDate = "";
      for (int i = 0; i < charArray.length; i++) {
        ccExpiryDate = ccExpiryDate.concat(String.valueOf(charArray[i]));
        if (i == 1 && charArray[2] != '/') {
          ccExpiryDate = ccExpiryDate.concat("/");
        }
      }
    }
    return ccExpiryDate;
  }

  public static LocalDateTime stringEpochToDate(String epoch) {
    Long epochL = Long.valueOf(epoch);
    LocalDateTime date =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(epochL), ZoneId.systemDefault());
    return date;
  }

  public static LocalDateTime stringEpochToLocalTimeDate(String epoch) {
    epoch = epochToLocalTimeZone(epoch);
    Long epochL = Long.valueOf(epoch);
    LocalDateTime date =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(epochL), ZoneId.systemDefault());
    return date;
  }

  public static String epochToLocalTimeZone(String epoch) {
    final int MILLISECONDS_TO_HOUR = 1000 * 60 * 60;
    epoch = String.valueOf((Long.valueOf(epoch) - (3 * MILLISECONDS_TO_HOUR)));
    return epoch;
  }

  /**
   * String epoch without milliseconds to local timezone Date
   *
   * @param epoch
   * @return
   */
  public static LocalDateTime epochWithoutMilitoLocalTimeZone(String epoch) {
    epoch = addMillisecondsToEpoch(epoch);
    final int MILLISECONDS_TO_HOUR = 1000 * 60 * 60;
    epoch = String.valueOf((Long.valueOf(epoch) - (3 * MILLISECONDS_TO_HOUR)));
    return stringEpochToDate(epoch);
  }

  /**
   * @param date
   * @return
   */
  public static String convertDateToString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String dateString = formatter.format(date);
    return dateString;
  }

  /**
   * @param date
   * @return
   */
  public static String convertForYearMonthDayTogether(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
    String dateString = formatter.format(date);
    return dateString;
  }


  /**
   * @param date
   * @param format desired for return
   * @return
   */
  public static String convertDateToString(Date date, String format) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    String dateString = formatter.format(date);
    return dateString;
  }

  /**
   * @param strDate in format "dd/MM/yyyy"
   * @return
   */
  public static Date convertStringDate(String strDate) {
    try {
      return new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
    } catch (ParseException ex) {
      ex.getMessage();
    }
    return null;
  }

  /**
   * String epoch without milliseconds Date
   *
   * @param epoch
   * @return
   */
  public static LocalDateTime epochWithoutMiliToDate(String epoch) {
    epoch = addMillisecondsToEpoch(epoch);
    return stringEpochToDate(epoch);
  }

  /**
   * Adiciona um mes calendario ao epoch
   *
   * @param epochString
   * @return String epoch
   */
  public static String addMonthToEpoch(String epochString) {
    LocalDateTime expiryDate = stringEpochToDate(epochString);
    expiryDate = expiryDate.plusMonths(1l);
    ZoneId zoneId = ZoneId.systemDefault();
    long epoch = expiryDate.atZone(zoneId).toInstant().toEpochMilli();
    return String.valueOf(epoch);
  }

  /**
   * Adiciona um ano calendario ao epoch
   *
   * @param epochString
   * @return String epoch
   */
  public static String addYearToEpoch(String epochString) {
    LocalDateTime expiryDate = stringEpochToDate(epochString);
    expiryDate = expiryDate.plusYears(1L);
    ZoneId zoneId = ZoneId.systemDefault();
    long epoch = expiryDate.atZone(zoneId).toInstant().toEpochMilli();
    return String.valueOf(epoch);
  }

  /**
   * Adiciona milisegundos (000 no fim da string) no epoch
   *
   * @param epochString
   * @return String epoch
   */
  public static String addMillisecondsToEpoch(String epochString) {
    return String.valueOf(epochString.concat("000"));
  }

  /**
   * Retorna o long mili do LocalDateTime
   *
   * @param date
   * @return
   */
  public static long localDateTimeToLong(LocalDateTime date) {
    ZoneId zoneId = ZoneId.systemDefault();
    return date.atZone(zoneId).toInstant().toEpochMilli();
  }

  public static Integer getDayOfTheMonthAfterParamDays(Integer days) {
    LocalDateTime ld = LocalDateTime.now();
    ld = ld.plusDays(days);
    return ld.getDayOfMonth();
  }

  /**
   * Retorna o epoch de um ZonedDateTime
   *
   * @param date
   * @return long
   */
  public static long zonedDateTimeToLong(ZonedDateTime date) {
    return localDateTimeToLong(date.toLocalDateTime());
  }

  /**
   * Pega uma data em formato string, e formata ela para o formato que o salesforce aceita
   *
   * @param date
   * @return
   * @throws ParseException
   */
  public static String formatDateToStandardSalesforceFormat(String date) throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Date formatedDate = format.parse(date);
    format.applyPattern("yyyy-MM-dd");
    return format.format(formatedDate);
  }

  /**
   * Converte uma data que esta em string para um local date time
   *
   * @param date
   * @return
   * @throws ParseException
   */
  public static LocalDateTime convertStringDateToLocalDateTime(String date) throws ParseException {
    //convertendo para date
    Date data = new SimpleDateFormat("dd/MM/yyyy").parse(date);
    //convertendo data para localdatetime
    LocalDateTime dateTime = LocalDateTime.ofInstant(data.toInstant(), ZoneId.systemDefault());
    return dateTime;
  }

  /**
   * Converte uma data que esta em string para um local date time
   *
   * @param date
   * @return
   * @throws ParseException
   */
  public static LocalDateTime convertZonedStringDateToLocalDateTime(String date)
      throws ParseException {
    //convertendo para date
    Date data = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
    //convertendo data para localdatetime
    LocalDateTime dateTime = LocalDateTime.ofInstant(data.toInstant(), ZoneId.systemDefault());
    return dateTime;
  }


  /**
   * @param date
   * @return
   */
  public static String convertForHyphenDayMonthYearString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    String dateString = formatter.format(date);
    return dateString;
  }

  /**
   * Converte uma LocalDateTime para uma String ano mês.
   *
   * @param date data para conversão
   * @return String formatada
   */
  public static String convertJoinYearMon(LocalDateTime date) {
    DateTimeFormatter dateFormatterYearMonth = DateTimeFormatter.ofPattern("yyyyMM");
    return dateFormatterYearMonth.format(date);
  }


  /**
   * Converte uma Date para uma String ano mês dia.
   *
   * @param date data para conversão
   * @return String formatada
   */
  public static String convertJoinYearMonthDay(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(date);
  }

  /**
   * Converte uma Date para uma String ano mês.
   *
   * @param date data para conversão
   * @return String formatada
   */
  public static String convertJoinYearMon(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(date);
  }

  /**
   * Converte uma Date para uma String ano-mês.
   *
   * @param date data para conversão
   * @return String formatada
   */
  public static String convertForMonthYear(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("MMM-yy", localeBr);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(date);
  }

  /**
   * Converte uma Date para uma String diamêsano.
   *
   * @param date data para conversão
   * @return String formatada
   */
  public static String convertForDayMontyYearTogether(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(date);
  }


  /**
   * Converte uma Date para uma String data/hora com timezone GMT.
   *
   * @param date data para conversão
   * @return String formatada
   */
  public static String convertDateToStringGMTDateTime(Date date) {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sssZ");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(date);
  }

  /**
   * Gera um epoch em string passando uma data de agora já formatada para o formato que o brm
   * aceita
   *
   * @return
   */
  public static String generateEpochStringInBrmFormatFromTimeNow() {
    return String.valueOf(Instant.now().getEpochSecond());
  }

  /**
   * Converte um epoch em Long para uma String no formato ISO_LOCAL_DATE_TIME
   *
   * @param epoch
   * @return string
   */
  public static String convertEpochToIsoLocalDateTime(Long epoch) {
    String epochString = String.valueOf(epoch);
    if (epochString.length() < 13) {
      epoch = Long.valueOf(addMillisecondsToEpoch(epochString));
    }
    Date date = new Date(epoch);
    LocalDateTime localDateTime = LocalDateTime
        .ofInstant(date.toInstant(), ZoneOffset.systemDefault());
    return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public static LocalDateTime convertEpochToLocalDateTimePlusDays(String epoch, int days) {
    long epochInLong = Long.parseLong(addMillisecondsToEpoch(epoch));

    LocalDateTime localDateTime = LocalDateTime
        .ofInstant(new Date(epochInLong).toInstant(), ZoneOffset.systemDefault());

    return localDateTime.plusDays(days);
  }

  public static String convertBrmDateToSalesforceDatePlusDays(String brmDate, int days) {
    String epoch = brmDate.substring(1, 11);

    LocalDateTime localDateTime = convertEpochToLocalDateTimePlusDays(epoch, days);

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    return format.format(new Date(localDateTimeToLong(localDateTime)));
  }

  public static String convertBrmDateToStandardSalesforceDatePlusDays(String brmDate, int days) {
    LocalDateTime localDateTime = convertEpochToLocalDateTimePlusDays(brmDate, days);

    long epoch = localDateTime.toEpochSecond(ZoneOffset.UTC);

    String epochWithMilliSeconds = addMillisecondsToEpoch(String.valueOf(epoch));
    return epochWithMilliSeconds;
  }

  public static String convertEpochToSalesforceDateTime(String epochString) {

    long epoch;

    if (epochString.length() < 13) {
      epoch = Long.parseLong(addMillisecondsToEpoch(epochString));
    } else {
      epoch = Long.parseLong(epochString);
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    return format.format(new Date(epoch));
  }

  /**
   * Converte uma data instant no formato "yyyy-MM-dd HH:mm:ss.SSS'Z'" para um epoch
   *
   * @param instant
   * @return string
   */
  public static String convertInstantToEpoch(String instant) throws ParseException {
    Date data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'").parse(instant);
    //convertendo data para localdatetime
    LocalDateTime dateTime = LocalDateTime.ofInstant(data.toInstant(), ZoneId.systemDefault());

    ZoneId zoneId = ZoneId.systemDefault();

    return Long.toString(dateTime.atZone(zoneId).toEpochSecond());
  }

  /**
   * Extrai e retorna o ano de um Date
   *
   * @param date
   * @return
   */
  public static int getYearFromDate(Date date){
    if (date != null){
      LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      return localDate.getYear();
    }
    return 0;
  }

  /**
   * Extrai e retorna o mes de um Date
   *
   * @param date
   * @return
   */
  public static int getMonthFromDate(Date date){
    if (date != null){
      LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      return localDate.getMonthValue();
    }
    return 0;
  }
}
