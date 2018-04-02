package com.meta.heweather;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.heweather.MHeWeather;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author:lhq
 * @date:2017/12/15 10:49
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "he_weather", description = "天气接口")
public class HeWeatherController extends BaseControllerUtil {
    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(HeWeatherController.class);

    @RequestMapping(value = ServiceUrls.HeWeather.HeWeather, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取组信息", notes = "根据id获取组信息")
    public Result<MHeWeather> get(
            @ApiParam(name = "address", value = "address", defaultValue = "")
            @RequestParam(value = "address") String address) {
        Result<MHeWeather> result = new  Result<MHeWeather>();
        MHeWeather mHeWeather = new MHeWeather();
        try {

            String httpUrl = "https://free-api.heweather.com/v5/weather?city="+address+"&key=89634a0030dc4931b8068466065c379f";
            BufferedReader reader = null;

            StringBuffer sbf = new StringBuffer();
            try {
                URL url = new URL(httpUrl);
                HttpsURLConnection connection = (HttpsURLConnection) url
                        .openConnection();
                //GET方式访问
                connection.setRequestMethod("GET");
                connection.connect();
                //输入流
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sbf.append(strRead);
                    sbf.append("\r\n");
                }
                reader.close();
                String temp = sbf.toString();
                JsonParser jsonParser = new JsonParser();
                JsonObject object = (JsonObject) jsonParser.parse(temp);
                JsonArray array = object.get("HeWeather5").getAsJsonArray();
                JsonObject sJsonObject = array.get(0).getAsJsonObject();
                JsonObject sJsonObject1 = sJsonObject.get("aqi").getAsJsonObject();
                JsonObject city = sJsonObject1.get("city").getAsJsonObject();
                //空气类
                JsonArray sJsonObject2 = sJsonObject.get("daily_forecast").getAsJsonArray(); //当前天气类  是组
                JsonObject curr = sJsonObject2.get(0).getAsJsonObject(); //当前的天气
                JsonObject heat = curr.get("tmp").getAsJsonObject();
                JsonObject wind = curr.get("wind").getAsJsonObject();
                JsonObject weatherThing = curr.get("cond").getAsJsonObject();
                mHeWeather.setHeat(heat.get("max").getAsString() + "℃ - " + heat.get("min").getAsString() + "℃");
                mHeWeather.setPm25(city.get("pm25").getAsString());
                mHeWeather.setQlty(city.get("qlty").getAsString());
                mHeWeather.setWind(wind.get("sc").getAsString());
                mHeWeather.setWeatherThing(weatherThing.get("txt_d").getAsString());
                result.setObj(mHeWeather);
                result.setSuccessFlg(true);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            logger.error("获取天气异常！");
            logger.error(e.getMessage(),e);
            return error("获取天气异常！");
        }

        return result;

    }
}
