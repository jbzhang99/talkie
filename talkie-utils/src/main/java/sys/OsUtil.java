package sys;

import org.apache.commons.lang3.StringUtils;

/**
 * @author:lhq
 * @date:2017/12/11 9:39
 */
public class OsUtil {

    private static String osName = System.getProperty("os.name");
    /**
     * 判断操作系统是否Linux
     * @return true-是，false-否
     */
    public static boolean isLinux(){
        return StringUtils.containsIgnoreCase(osName, "linux");
    }
    /**
     * 判断操作系统是否Windows
     * @return true-是，false-否
     */
    public static boolean isWindows(){
        return StringUtils.containsIgnoreCase(osName,"windows");
    }


}
