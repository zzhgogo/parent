package com.zhu.base.j8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: zhuhao
 * @Date: 2018/10/29 5:35 PM
 * @Description:
 */
public class LocalDataExmple {

    //Java 8 中 Date与LocalDateTime、LocalDate、LocalTime互转
    static class Util{
        // Obtains an instance of Date from an Instant object.
        public static Date from(Instant instant) {
            try {
                return new Date(instant.toEpochMilli());
            } catch (ArithmeticException ex) {
                throw new IllegalArgumentException(ex);
            }
        }

        // Converts this Date object to an Instant.
        public Instant toInstant() {
            return Instant.ofEpochMilli(System.currentTimeMillis());
        }



        // 这两个方法使我们可以方便的实现将旧的日期类转换为新的日期类，具体思路都是通过Instant当中介，
        // 然后通过Instant来创建LocalDateTime（这个类可以很容易获取LocalDate和LocalTime），
        // 新的日期类转旧的也是如此，将新的先转成LocalDateTime，然后获取Instant，接着转成Date，具体实现细节如下：

        // 01. java.util.Date --> java.time.LocalDateTime
        public void UDateToLocalDateTime() {
            java.util.Date date = new java.util.Date();
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        }

        // 02. java.util.Date --> java.time.LocalDate
        public void UDateToLocalDate() {
            java.util.Date date = new java.util.Date();
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            LocalDate localDate = localDateTime.toLocalDate();
        }

        // 03. java.util.Date --> java.time.LocalTime
        public void UDateToLocalTime() {
            java.util.Date date = new java.util.Date();
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            LocalTime localTime = localDateTime.toLocalTime();
        }


        // 04. java.time.LocalDateTime --> java.util.Date
        public void LocalDateTimeToUdate() {
            LocalDateTime localDateTime = LocalDateTime.now();
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            java.util.Date date = Date.from(instant);
        }


        // 05. java.time.LocalDate --> java.util.Date
        public void LocalDateToUdate() {
            LocalDate localDate = LocalDate.now();
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
            java.util.Date date = Date.from(instant);
        }

        // 06. java.time.LocalTime --> java.util.Date
        public void LocalTimeToUdate() {
            LocalTime localTime = LocalTime.now();
            LocalDate localDate = LocalDate.now();
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            java.util.Date date = Date.from(instant);
        }
    }

    @Test
    public void t1(){
        LocalDate today = LocalDate.now();
        System.out.println(today);
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        System.out.printf("Year : %d  Month : %d  day : %d t %n", year, month, day);

        LocalDate dateOfBirth = LocalDate.of(2010, 01, 14);

        System.out.println("Your Date of birth is : " + dateOfBirth);
    }

    @Test
    public void t2() {
        LocalTime time = LocalTime.now();
        System.out.println(time);
    }

    @Test
    public void t3() {
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println(formatter.format(dateTime));

        LocalDateTime localDateTime = LocalDateTime.parse("2219-04-25 15:09:53", formatter);
        System.out.println(localDateTime);

    }
}
