package com.zhu.base.j8;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @Author: zhuhao
 * @Date: 2018/10/29 5:35 PM
 * @Description:
 */
public class LocalDataExmple {

    @Test
    public void t1(){
        LocalDate today = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println(today);
        System.out.println(localDateTime);

        ZonedDateTime zonedDateTime = today.atStartOfDay(ZoneId.systemDefault());
        Date date = Date.from(zonedDateTime.toInstant());

        System.out.println(date);


    }
}
