package com.meta.controller.terminal;

import com.cloud.commons.config.AppConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cloud.common.crypto.DESCoder;
import com.cloud.common.enctype.Encrypter;
import com.cloud.module.base.pojo.Response;
import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.user.User;
import com.meta.redissync.RedisSyncService;
import com.meta.service.user.UserService;

/**
 * Created by xry on 2017/11/13.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "auth", description = "设备终端接入鉴权")
public class AuthController extends BaseControllerUtil {
    private final String aeskey = "yq2X3KFyhKu9s2du7iuqpg==";
    Logger logger = BaseControllerUtil.logger;
    @Autowired
    UserService userService;
    @Autowired
    RedisSyncService redisHolderService;

    /**
     * auth device, and return server address info
     *
     * @param token
     * @return
     */
    @RequestMapping(value = ServiceUrls.Terminal.TERMINAL_AUTH, method = RequestMethod.GET)
    @ApiOperation(value = "设备鉴权", notes = "设备鉴权")
    public Result<String> auth(
            @ApiParam(name = "token", value = "鉴权字串", defaultValue = "")
            @RequestParam(value = "token", required = true) String token) {
        Response<Object> resp = new Response<Object>();
        logger.info("terminal auth str:" + token);

        Map<String, Long> mapServerOnline = redisHolderService.getBusiServerOnlineNumber();
        logger.info("server online:" + JSON.toJSONString(mapServerOnline));
        String pickServerAddr = null;
        Long onlineCnt = Long.MAX_VALUE;
        for (Map.Entry<String, Long> entry : mapServerOnline.entrySet()) {
            if (entry.getValue() < onlineCnt) {
                onlineCnt = entry.getValue();
                pickServerAddr = entry.getKey();
            }
        }

        int retCode = validateToken(token);
        if (retCode == 0) {
            String server_ip = AppConfig.getItemValue("protocol.UNKNOWN.info.ip");
            String server_tcp_port = AppConfig.getItemValue("protocol.UNKNOWN.info.port.tcp");
            String server_udp_port = AppConfig.getItemValue("protocol.UNKNOWN.info.port.udp");
            String server_sock_type = AppConfig.getItemValue("talkie.busi.server.sock_type");
            if (pickServerAddr != null) {
                logger.info("pickServerAddr:" + JSON.toJSONString(pickServerAddr));
                server_tcp_port = AppConfig.getItemValue("protocol.UNKNOWN.info.port." + pickServerAddr + ".tcp");
                server_udp_port = AppConfig.getItemValue("protocol.UNKNOWN.info.port." + pickServerAddr + ".udp");
                logger.info("server_tcp_port:" + server_tcp_port + " server_udp_port:" + server_udp_port);
            }
            ByteBuffer buf = ByteBuffer.allocate(512);
            for (String sec : StringUtils.split(server_ip, ".")) {
                buf.put(Integer.valueOf(sec).byteValue());
            }
            buf.putShort(Integer.valueOf(server_tcp_port).shortValue());
            buf.putShort(Integer.valueOf(server_udp_port).shortValue());
            buf.flip();
            byte[] plain = new byte[buf.limit()];
            byte[] cipher = null;
            buf.get(plain);
            try {
                DESCoder.setALGORITHM("AES");
                cipher = AesEncryptDecrypt.encryptPad(plain, DESCoder.decryptBASE64(aeskey));
                logger.info("encrypt ip_port =" + toHexString(cipher));
            } catch (Exception e) {
                logger.info("aes encryp exception:" + e);
            }

            if (cipher != null) {
                buf.clear();
                buf.put((byte) Integer.valueOf(server_sock_type).byteValue()); // udp
                buf.put((byte) cipher.length);
                buf.put((byte) 1); // server count
                buf.put(cipher);
                buf.flip();
                byte[] pack = new byte[buf.limit()];
                buf.get(pack);
                logger.info("auth return pack:" + toHexString(pack));
                resp.setData(pack);
            } else {
                resp.setRetCode(1003);
            }

        } else {
            resp.setRetCode(retCode);
        }

        return success(JSON.toJSONString(resp) + "\r\n");

    }

    public int validateToken(String token) {
        int retCode = 0;
        long timeStart = 0L;
        long devTime1 = 0L;
        long devTime2 = 0L;
        int len = 0;
        try {
//			byte[] testData = {0, 65, 49, 48, 48, 48, 48, 48, 48, 50, 50, 53, 56, 65, 56, (byte)225, 10, (byte)220, 57, 73, (byte)186, 89, (byte)171, (byte)190, 86, (byte)224, 87, (byte)242, 15, (byte)136, 62, 83, 110, (byte)152, 17, 0, 16, (byte)166, (byte)233, (byte)135, (byte)191, (byte)157, 67, 96, (byte)242, (byte)179, 38, 4, 2, 5, (byte)251, (byte)186, 125, 0, 16, (byte)228, 5, (byte)168, 123, (byte)241, (byte)177, (byte)189, (byte)185, 62, (byte)221, 0, (byte)214, (byte)154, 114, 52, 121, 0, 0, 0, 0, 0, 0, 73, 84, 53, 48, 49, 95, 118, 49, 46, 48, 0, 1, (byte)172, (byte)136};
//			token = "AEExMDAwMEYwMDAwMDA2WLLFNEGp2xnhWb7GhtaF2FNjfL8AEO1xisJjO2EN4agZDSA2eQ0AEPB4fmcI2tAJ2r1cppq2bWAAAAABEQYIDTkaSVQ1MDFfdjEuMA==";
            token = token.replaceAll(" ", "+");
            byte[] authData = Encrypter.decryptBASE64(token);
            ByteArrayInputStream bis = new ByteArrayInputStream(authData);
            byte[] deviceByte = new byte[15];
            bis.read(deviceByte);
            byte[] accmd5 = new byte[16];
            bis.read(accmd5);
            byte[] ts = new byte[4];
            bis.read(ts);
            devTime1 = ((toUnsignedByte(ts[0]) << 24) + (toUnsignedByte(ts[1]) << 16) + (toUnsignedByte(ts[2]) << 8) + toUnsignedByte(ts[3]));
            devTime1 = toUnsignedInt((int) ((~devTime1) << 1));
            timeStart = System.currentTimeMillis() / 1000;
            logger.info("timeStart=" + timeStart + " devTime1=" + devTime1 + " ts:" + toHexString(ts));
            if (devTime1 < 0 || (timeStart - devTime1) > 60) {
                retCode = 1005;
                throw new Exception("time error");
            }

            byte[] enclen1 = new byte[2];
            bis.read(enclen1);
            len = (int) ((toUnsignedByte(enclen1[0]) << 8) + toUnsignedByte(enclen1[1]));
            byte[] encstr1 = new byte[len];
            bis.read(encstr1);
            DESCoder.setALGORITHM("AES");
            timeStart = System.currentTimeMillis();
            byte[] plain1 = AesEncryptDecrypt.decryptUnPad(encstr1, DESCoder.decryptBASE64(aeskey));
            logger.info("decrypt encstr1 =" + toHexString(plain1));
            ByteArrayInputStream enc1is = new ByteArrayInputStream(plain1);
            byte[] accl = new byte[2];
            enc1is.read(accl);
            len = (int) ((toUnsignedByte(accl[0]) << 8) + toUnsignedByte(accl[1]));
            logger.info("acclen=" + len + "  " + toHexString(accl));
            byte[] acc = new byte[len];
            enc1is.read(acc);
            ts = new byte[4];
            enc1is.read(ts);
            logger.info("acc=" + new String(acc) + " ts=" + toHexString(ts) + " systime=" + System.currentTimeMillis() / 1000);
            devTime2 = ((toUnsignedByte(ts[0]) << 24) + (toUnsignedByte(ts[1]) << 16) + (toUnsignedByte(ts[2]) << 8) + toUnsignedByte(ts[3]));
            devTime2 = toUnsignedInt((int) ((~devTime2) << 1));
            logger.info("time1=" + devTime1 + " time2=" + devTime2);
            if (devTime1 != devTime2) {
                retCode = 1005;
                throw new Exception("time not match");
            }
            byte[] calcmd5 = DESCoder.encryptMD5(acc);
            if (!Arrays.equals(calcmd5, accmd5)) {
                retCode = 1003;
                throw new Exception("md5 not match");
            }
            // get user passwd
            String account = new String(acc);
            User user = userService.findByAccount(account);
            if (user == null) {
                retCode = 1003;
                throw new Exception("account not exist");
            }

            String aeskey2 = DESCoder.encryptBASE64(DESCoder.encryptMD5(user.getPassword().getBytes()));
            logger.info("aeskey2=" + aeskey2);
            byte[] enclen2 = new byte[2];
            bis.read(enclen2);
            len = (int) ((toUnsignedByte(enclen2[0]) << 8) + toUnsignedByte(enclen2[1]));
            logger.info("encstr 2 len=" + len);
            byte[] encstr2 = new byte[len];
            bis.read(encstr2);
            DESCoder.setALGORITHM("AES");
            byte[] aescalc = AesEncryptDecrypt.encryptPad(acc, DESCoder.decryptBASE64(aeskey2));
            logger.info("encrypt account =" + toHexString(aescalc));
            if (!Arrays.equals(aescalc, encstr2)) {
                retCode = 1003;
                throw new Exception("account aes calc not match, upload aes:" + toHexString(encstr2));
            }
        } catch (Exception e) {
            logger.error("decrpt exception:" + e);
            if (retCode == 0) {
                retCode = 1006;
            }
        }

        return retCode;
    }

    public long toUnsignedByte(byte b) {
        return (long) (b & 0x0FF);
    }

    public long toUnsignedInt(int i) {
        return (long) (i & 0x0FFFFFFFF);
    }

    public String toHexString(byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x ", b));
        }
        return builder.toString();
    }
}
