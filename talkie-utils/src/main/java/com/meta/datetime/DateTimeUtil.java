package com.meta.datetime;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lhq
 * @version 1.0
 * @created 2016.12.05
 */
public class DateTimeUtil {
    public static final String yyyyMMddHHmmssPattern = "yyyyMMddHHmmss";
    public static final String MMddHHmmssPattern = "MMddHHmmss";
    public static final String simpleDateTimePattern = "yyyy-MM-dd HH:mm:ss";
    public static final String utcDateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String slashDateTimePattern = "yyyy/MM/dd HH:mm:ss";
    public static final String simpleDatePattern = "yyyy-MM-dd";
    public static final String simpleDateTimeShortPattern = "yyyy/MM/dd HH:mm";

    public static final String ISO8601Pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'ZZ";

    private static ThreadLocal<SimpleDateFormat> simpleDateTimeShortFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected synchronized SimpleDateFormat initialValue(){
            return new SimpleDateFormat(simpleDateTimeShortPattern);
        }
    };

    private static ThreadLocal<SimpleDateFormat> simpleDateTimeFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected synchronized SimpleDateFormat initialValue(){
            return new SimpleDateFormat(simpleDateTimePattern);
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyyMMddHHmmssFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected synchronized SimpleDateFormat initialValue(){
            return new SimpleDateFormat(yyyyMMddHHmmssPattern);
        }
    };
    
    private static ThreadLocal<SimpleDateFormat> MMddHHmmssFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected synchronized SimpleDateFormat initialValue(){
            return new SimpleDateFormat(MMddHHmmssPattern);
        }
    };

    private static ThreadLocal<SimpleDateFormat> utcDateTimeFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected synchronized SimpleDateFormat initialValue(){
            return new SimpleDateFormat(utcDateTimePattern);
        }
    };

    private static ThreadLocal<SimpleDateFormat> slashDateTimeFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected synchronized SimpleDateFormat initialValue(){
            return new SimpleDateFormat(slashDateTimePattern);
        }
    };

    private static ThreadLocal<SimpleDateFormat> simpleDateFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected synchronized SimpleDateFormat initialValue(){
            return new SimpleDateFormat(simpleDatePattern);
        }
    };

    private static ThreadLocal<SimpleDateFormat> iso8601Format = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected synchronized SimpleDateFormat initialValue(){
            return new SimpleDateFormat(ISO8601Pattern);
        }
    };

    public static String simpleDateTimeFormat(final Date date){
        String time = simpleDateTimeFormat.get().format(date);
        return time;
    }

    public static Date simpleDateTimeParse(final String date) throws ParseException {
        if(StringUtils.isEmpty(date)) return null;

        Date parsedDate = simpleDateTimeFormat.get().parse(date);
        return parsedDate;
    }

    public static String iso8601DateTimeFormat(final Date date){
        String time = iso8601Format.get().format(date);
        return time;
    }

    public static String yyyyMMddHHmmssFormat(final Date date){
        return yyyyMMddHHmmssFormat.get().format(date);
    }
    
    public static Date yyyyMMddHHmmssFormatParse(final String date) throws ParseException {
        if(StringUtils.isEmpty(date)) return null;

        Date parsedDate = yyyyMMddHHmmssFormat.get().parse(date);
        return parsedDate;
    }
    
    public static Date MMddHHmmssFormatParse(final String date) throws ParseException {
        if(StringUtils.isEmpty(date)) return null;

        Date parsedDate = MMddHHmmssFormat.get().parse(date);
        return parsedDate;
    }

    public static Date iso8601DateTimeParse(final String date) throws ParseException {
        if(StringUtils.isEmpty(date)) return null;

        Date parsedDate = iso8601Format.get().parse(date);
        return parsedDate;
    }

    public static String utcDateTimeFormat(final Date date){
        //为空判断
        if(date!=null) {
            String time = utcDateTimeFormat.get().format(date);
            return time;
        }
        else{
            return null;
        }
    }

    public static Date utcDateTimeParse(final String date) throws ParseException {
        if(StringUtils.isEmpty(date)) return null;

        Date parsedDate = utcDateTimeFormat.get().parse(date);
        return parsedDate;
    }

    public static String simpleDateTimeFormat(final Object date) throws ParseException {
        String time = "";
        if (date.getClass() == Date.class) {
            time = simpleDateTimeFormat.get().format(date);
        } else {
            time = simpleDateTimeFormat.get().format(simpleDateTimeFormat.get().parse(date.toString()));
        }
        return time;
    }

    public static String slashDateTimeFormat(final Date date){
        String result = slashDateTimeFormat.get().format(date);
        return result;
    }

    public static Date slashDateTimeParse(final String date) throws ParseException {
        Date parsedDate = slashDateTimeFormat.get().parse(date);
        return parsedDate;
    }

    public static String simpleDateFormat(final Date date){
        String result = simpleDateFormat.get().format(date);
        return result;
    }

    public static Date simpleDateParse(final String date) throws ParseException {
        if(StringUtils.isEmpty(date)) return null;

        Date parsedDate = simpleDateFormat.get().parse(date);
        return parsedDate;
    }

    public static String simpleDateTimeShortFormat(final Date date){
        String result = simpleDateTimeShortFormat.get().format(date);
        return result;
    }

    public static Date simpleDateTimeShortParse(final String date) throws ParseException {
        Date parsedDate = simpleDateTimeShortFormat.get().parse(date);
        return parsedDate;
    }

}
