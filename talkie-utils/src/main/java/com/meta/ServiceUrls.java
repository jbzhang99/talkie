package com.meta;

/**
 * @author
 * @create <p>
 * description：
 * ServiceUrls 为全局服务访问 url 类
 */
public class ServiceUrls {

    /**
     * 用户基础url
     */
    public static final class User {

        public static final String LOGIN_BY_ACCOUNT = "login_by_account";
        public static final String USER = "user";
        public static final String USER_SEARCH_AND_GROUP_DATE = "user_search_and_group_date";
        public static final String USER_SEARCH_AND_GROUP = "user_search_and_group";
        public static final String user_search_user_isOnline = "user_search_user_isOnline";//查看用户是否在线
        public static final String USER_FIND_BY_ACCOUNT = "user_find_by_account";
        public static final String USER_ALREADY_ADD_FRIEND = "user_already_add_friend";
        public static final String USER_FIND_CAN_ADD_FRIEND = "user_find_can_add_friend";
        public static final String USER_FIND_ALREADY_USER_GROUP = "user_find_already_user_group";
        public static final String USER_WAIT_USER_GROUP = "user_wait_user_group";
        public static final String USER_MODIFY_PASSWORD_BY_ID = "user_modify_password_by_id";
        public static final String USER_FIND_BY_ACCOUNT_AND_MERCHANT_LEVEL_AND_PARENT_ID = "user_find_by_account_and_merchant_level_and_parent_id";
        public static final String USER_BY_ID = "user_by/{id}";
        public static final String USER_ACCOUNT = "user_account";
        public static final String USERS = "users";
        public static final String USERS_BY_ID_AND_LANGUAGE = "users_by_id_and_language";
        public static final String USERS_BATCH_SAVE = "users_batch_save";
        public static final String ICCIDUSERS_BATCH_SAVE = "iccIduser_batch_save";
        public static final String USER_MODIYY_BY_ID = "user_modify_by_id";
        public static final String USER_COUNT_COMPANY_BY_PARENT_ID = "user_count_company_by_parent_id";
        public static final String USER_ONLINE_STATUS = "user_online_status";
    }

    /**
     * 代理操作记录URL
     */
    public static final class MerchantEvent {
        public static final String MERCHANT_EVENT = "merchant_event";
        public static final String MERCHANT_EVENTS = "merchant_events";
    }


    /**
     * 基站url
     */
    public static final class BaseStation {
    public  static final String BASESTATION="base_station";
    public  static final String BASESTATIONS="base_stations";
    }

    /**
     * 邮箱URL
     */
    public static final class Email {
        public static final String EMAIL = "email";
        public static final String EMAILS = "emails";
    }

    /**
     * 代理记录URL
     */
    public static final class Merchant {
        public static final String MERCHANT = "merchant";
        public static final String MERCHANT_COUNT_PARNET_ID_AND_MERCHANT_LEVEL = "merchant_count_parnet_id_and_merchant_level";
        public static final String MERCHANT_SEC_TOTAL_INFO = "merchant_sec_total_info";
        public static final String MERCHANT_TOTAL_INFO = "merchant_total_info";
        public static final String MERCHANT_SEARCH_ACCOUNT = "merchant_search_account";
        public static final String MERCHANT_COUNT_COMPANY_BY_PARENT_ID = "merchant_count_company_by_parent_id";
        public static final String MERCHANT_MODIFY_ID = "merchant_modify_id";
        public static final String MERCHANT_FIND_BY_ACCOUNT_AND__PARENT_ID = "merchant_find_by_account_and_parent_id";
        public static final String MERCHANT_BY_ID = "merchant_by/{id}";
        public static final String MERCHANTS = "merchants";
        public static final String MERCHANTS_NO_PAGE = "merchants_no_page";

        public static final String MERCHANTS_BY_ID_AND_LANGUAGE = "merchants_by_id_and_language";
    }

    /**
     * 子代理URL
     */
    public static final class SecMerchant {
        public static final String SEC_MERCHANT = "sec_merchant";
        public static final String SEC_MERCHANT_COUNT_COMPANY_BY_PARENT_ID = "sec_merchant_count_company_by_parent_id";
        public static final String SEC_MERCHANT_MODIFY_STATUS_BY_ID = "sec_merchant_modify_status_by_id";
        public static final String SEC_MERCHANT_PASSWORD_STATUS_BY_ID = "sec_merchant_modify_password_by_id";
        public static final String SEC_MERCHANT_FIND_BY_ACCOUNT_AND__PARENT_ID = "sec_merchant_find_by_account_and_parent_id";
        public static final String SEC_MERCHANTS = "sec_merchants";
        public static final String SEC_MERCHANTS_BY_ID_AND_LANGUAGE = "sec_merchants_by_id_and_language";
        public static final String SEC_MERCHANT_BY_ID = "sec_merchant_by/{id}";
    }

    /**
     * 企业 操作记URL
     */
    public static final class EnterpriseEvent {
        public static final String ENTERPRISE_EVENT = "enterprise_event";
        public static final String ENTERPRISE_EVENTS = "enterprise_events";
    }

    /**
     * 企业URL
     */
    public static final class Enterprise {
        public static final String ENTERPRISE = "enterprise";
        public static final String ENTERPRISE_COUNT_PARENT_ID_AND_MERCHANT_LEVEL = "enterprise_count_parent_id_and_merchant_level";
        public static final String ENTERPRISE_BY_TERMINAL = "enterprise_by_terminal";
        public static final String ENTERPRISE_NO_PAGE = "enterprise_no_page";
        public static final String ENTERPRISE_TOTAL_INFO = "enterprise_total_info";
        public static final String ENTERPRISE_COUNT_COMPANY_BY_PARENT_ID = "enterprise_count_company_by_parent_id";
        public static final String ENTERPRISE_MODIFY_STATUS_BY_ID = "enterprise_modify_status_by_id";
        public static final String ENTERPRISES = "enterprises";
        public static final String ENTERPRISES_FIND_BY_ACCOUNT_AND__PARENT_ID = "enterprise_find_by_account_and_parent_id";
        public static final String ENTERPRISE_BY_ID = "enterprise/by/{id}";
        public static final String ENTERPRISE_BY_ID_AND_LANGUAGE = "enterprise_by_id_and_language";
    }

    /**
     * 二级管理(企业)URL
     */
    public static final class SecEnterPrise {
        public static final String SEC_ENTER_PRISE = "sec_enter_prise";
        public static final String SEC_ENTER_PRISE_COUNT_PARENT_ID_AND_MENCHANT_LEVEL = "sec_enter_prise_count_parent_id_and_menchant_level";
        public static final String SEC_ENTER_PRISE_FIND_ALREADY_USER_GROUP = "sec_enter_prise_find_already_user_group";
        public static final String SEC_ENTER_PRISE_WAIT_USER_GROUP = "sec_enter_prise_wait_user_group";
        public static final String SEC_ENTER_PRISE_BY_ID = "sec_enter_prise/by/{id}";
        public static final String SEC_ENTER_PRISE_COUNT_COMPANY_BY_PARENT_ID = "sec_enter_prise_count_company_by_parent/id";
        public static final String SEC_ENTER_PRISE_FIND_BY_ACCOUNT_AND__PARENT_ID = "sec_enter_prise_find_by_account/and/parent/id";
        public static final String SEC_ENTER_PRISE_MODIFY_STATUS_BY_ID = "sec_enter_prise_modify_status_by_id";
        public static final String SEC_ENTER_PRISES = "sec_enter_prises";
        public static final String SEC_ENTER_PRISES_NO_PAGE = "sec_enter_prises_no_page";

    }


    /**
     * 二级管理与用户URL
     */
    public static final class SecEnterPriseUser {

        public static final String SEC_ENTER_PRISE_USER = "sec_enter_prise_user";
        public static final String SEC_ENTER_PRISE_USERS = "sec_enter_prise_users";
    }

    /**
     * 总代操作记录URL
     */
    public static final class GeneralAgentEvent {
        public static final String GENERAL_AGENT_EVENT = "general_agent_event";
        public static final String GENERAL_AGENT_EVENTS = "general_agent_events";
    }

    /**
     * 企业账号管理URL
     */
    public static final class EnterPriseAccount {
        public static final String ENTER_PRISE_ACCOUNT = "enter_prise_account";
        public static final String ENTER_PRISE_ACCOUNT_MODIFY_STATUS_BY_ID = "enter_prise_account_modify_status_id";
        public static final String ENTER_PRISE_ACCOUNTS = "enter_prise_accounts";
        public static final String ENTER_PRISE_ACCOUNT_BY_ID = "enter_prise_account/by/{id}";
    }

    /**
     * 代理-账号管理URL
     */
    public static final class MerchantAccount {
        public static final String MERCHANT_ACCOUNT = "merchant_account";
        public static final String MERCHANT_ACCOUNTS = "merchant_accounts";
        public static final String MERCHANT_ACCOUNT_MODIFY_STATUS_BY_ID = "merchant_account_modify_status_by_id";

    }

    /**
     * 子代理 --账号管理url
     */
    public static final class SecMerchantAccount {
        public static final String SEC_MERCHANT_ACCOUNT = "sec_merchant_account";
        public static final String SEC_MERCHANT_ACCOUNTS = "sec_merchant_accounts";
        public static final String SEC_MERCHANT_ACCOUNT_MODIFY_STATUS_BY_ID = "sec_merchant_account_modify_status_by_id";
        public static final String SEC_MERCHANT_ACCOUNTS_BY_ID = "sec_merchant_accounts/by/{id}";
    }


    /**
     * 总代级 账号管理URL
     */
    public static final class GeneralAgentAccount {
        public static final String GENERAL_AGENT_ACCOUNT = "general_agent_account";
        public static final String GENERAL_AGENT_ACCOUNTS = "general_agent_accounts";
        public static final String GENERAL_AGENT_ACCOUNT_MODIFY_STATUS_BY_ID = "general_agent_account_modify/status/by/id";
    }


    /**
     * 会计管理URL
     */

    public  static final class AccountantManage{
        public  static final String  ACCOUNTANT_MANAGE="accountant_manage";
        public  static final String  ACCOUNTANT_MANAGE_BY_ID="accountant_manage_by/{id}";
        public  static final String  ACCOUNTANT_MANAGE_MODIFY_STATUS="accountant_manage_modify_status";
        public  static final String  ACCOUNTANT_MANAGES="accountant_manages";
    }


    /**
     * 会计操作记录URL
     */
    public  static final class AccountantEvent{
        public  static final String ACCOUNTANT_EVENT="accountant_event";
        public  static final String ACCOUNTANT_EVENTS="accountant_events";
    }

    /**
     * 用户组URL
     */
    public static final class UserGroup {
        public static final String USER_GROUP = "user_group";
        public static final String USER_GROUP_BATCH = "user_group_batch";
        public static final String USER_GROUPS = "user_groups";
    }


    /**
     * 二级管理与组URL
     */
    public static final class SecEnterPriseUserGroup {
        public static final String SEC_ENTER_PRISE_USER_GROUP = "sec_enter_prise_user_group";
        public static final String SEC_ENTER_PRISE_USER_GROUP_DEL_BATCH = "sec_enter_prise_user_group_del_batch";
        public static final String SEC_ENTER_PRISE_USER_GROUP_BATCH = "sec_enter_prise_user_group_batch";
        public static final String SEC_ENTER_PRISE_USER_GROUPS = "sec_enter_prise_user_groups";

    }


    /**
     * 用户操作事件URL
     */
    public static final class UserEvent {

        public static final String USER_EVENT = "user_event";
        public static final String USER_EVENTS = "user_events";
    }

    /**
     * 用户好友URL
     */
    public static final class UserFriend {
        public static final String USER_FRIEND = "user_friend";
        public static final String USER_FRIEND_BATCH_ADD_OR_DEL = "user_friend_batch_add_or_del";
        public static final String USER_FRIENDS = "user_friends";
    }

    /**
     * 权限URLf
     */

    public static final class Role {
        public static final String ROLE = "role";
        public static final String ROLES = "roles";
    }

    /**
     * 菜单url
     */
    public static final class Menu {
        public static final String MUEN = "menu";
        public static final String MUEN_FIND_BY_ROLE = "menu_find_by_role";
        public static final String MENU = "menus";
    }

    /**
     * 卫星定位，--基于百度地图
     */

    public static final class BaiDuMap {

        public static final String BAIDU_MAP = "baidu_map";
        public static final String BAIDU_MAPS = "baidu_maps";

    }

    /**
     * 组 URL
     */
    public static final class Group {
        public static final String GROUP = "group";
        public static final String GROUP_SEARCH_NOPAGE = "group_search_nopage";
        public static final String GROUP_ASSIGNMENT = "group_assignment";
        public static final String GROUP_FIND_ALREADY_USER = "group_find_already_user";
        public static final String GROUP_FIND_WAIT_BY_USER = "group_find_wait_by_user";
        public static final String GROUP_MODIFY_BY_STATUS = "group_modify_by_status";
        public static final String GROUP_BY_ID = "group_by/{id}";
        public static final String GROUPS = "groups";
    }

    /**
     * 天气URL
     */
    public static final class HeWeather {
        public static final String HeWeather = "he_weather";
        public static final String HeWeatherS = "he_weathers";
    }

    /**
     * 二级管理的组URL
     */
    public static final class SecEnterPriseGroup {

        public static final String SEC_ENTER_PRISE_GROUP = "sec_enter_prise_group";
        public static final String SEC_ENTER_PRISE_GROUP_DEL_BY_ID = "sec_enter_prise_group_del_by_id";
        public static final String SEC_ENTER_PRISE_GROUP_FIND_ALREADY_USER = "sec_enter_prise_group_find_already_user";
        public static final String SEC_ENTER_PRISE_GROUP_FIND_WAIT_BY_USER = "sec_enter_prise_group_find_wait_by_user";
        public static final String SEC_ENTER_PRISE_GROUP_MODIFY_STATUS_BY_ID = "sec_enter_prise_group_modify/status/by/id";
        public static final String SEC_ENTER_PRISE_GROUP_BY_ID = "sec_enter_prise_group_by/{id}";
        public static final String SEC_ENTER_PRISE_GROUPS = "sec_enter_prise_groups";

    }

    /**
     * 省份城市URL
     */
    public static final class AddressDict {
        public static final String ADDRESS_DICT = "address_dict";
        public static final String ADDRESS_DICTS = "address_dicts";
    }

    /**
     * Q币用户URL
     */
    public static final class QUser {
        public static final String Q_USER = "q_user";
        public static final String Q_USER_QUERY_USER_BY_USER_ID = "q_user_query_user_by_user_id";
        public static final String Q_USER_BATCH_MODIFY_BALANCE = "q_user_batch_modify_balance";
        public static final String Q_USER_BATCH_ADD_OR_DEL = "q_user_batch_add_or_del";
        public static final String Q_USER_TOTAL_Q = "q_user_total_q";
        public static final String Q_USERS = "q_users";
        public static final String Q_USER_BY_ID = "q_users_by/{id}";
        public static final String Q_USER_MODIFY_BALANCE_BY_ID = "q_user_modify_balance_by_id";
    }


    /**
     * Q币用户操作URL
     */
    public static final class QUserEvent {
        public static final String Q_USER_EVENT = "q_user_event";
        public static final String Q_USER_EVENTS = "q_user_events";
    }

    /**
     * Q币企业URL
     */
    public static final class QEnterPrise {
        public static final String Q_ENTER_PRISE_TOTAL_Q = "q_enter_prise_total_q";
        public static final String Q_ENTER_PRISE = "q_enter_prise";
        public static final String Q_ENTER_PRISE_NO_PAGE = "q_enter_prise_no_page";
        public static final String Q_ENTER_PRISE_FIND_BY_USER_ID = "q_enter_prise_find_by_user_id";
        public static final String Q_ENTER_PRISE_BATCH_ADD_OR_DEL = "q_enter_prise_batch_add_or_del";
        public static final String Q_ENTER_PRISE_MODIFY_BALANCE_BY_ID = "q_enter_prise_modify_balance_by_id";
        public static final String Q_ENTER_PRISE_BY_ID = "q_enter_prise/by/{id}";
        public static final String Q_ENTER_PRISES = "q_enter_prises";
    }

    /**
     * Q币总代URL
     */
    public static final class QGeneralAgent {
        public static final String Q_GENERAL_AGENT = "q_general_agent";
        public static final String Q_GENERAL_AGENT_TOTAL_BY_YEAR_AND_MONTH = "q_general_agent_total_by_year_and_month";
        public static final String Q_GENERAL_AGENT_YEAR_TOTAL = "q_general_agent_year_total";
        public static final String Q_GENERAL_AGENT_DATA_TOTAL = "q_general_agent_data_total";
        public static final String Q_GENERAL_AGENT_FIND_BY_USER_ID = "q_general_agent_find_by_user_id";
        public static final String Q_GENERAL_AGENT_UPDATE_BALANCE_BY_ID = "q_general_agent_update_balance_by_id";
        public static final String Q_GENERAL_AGENT_TOTAL_Q = "q_general_agent_total_q";
        public static final String Q_GENERAL_AGENT_MODIFY_BALANCE_BY_ID = "q_general_agent_modify_balance_by_id";
        public static final String Q_GENERAL_AGENTS = "q_general_agents";
        public static final String Q_GENERAL_AGENT_BY_ID = "q_general_agent/by/{id}";

    }


    /**
     * Q币权限
     */
    public static final class QValidate {
        public static final String Q_VALIDATE = "q_validate";
        public static final String Q_VALIDATES = "q_validates";
    }

    /**
     * 会计分配Q币时密码
     */
    public  static final class QAccountandPassword{
        public static final String  Q_ACCOUNTANT_PASSWORD="q_accountant_password";
        public static final String  Q_ACCOUNTANT_PASSWORDS="q_accountant_passwords";
        public static final String  Q_ACCOUNTANT_PASSWORD_ISNULL = "q_accountant_password_isnull";

    }

    /**
     * Q币代理URL
     */
    public static final class QMerchant {
        public static final String Q_MERCHANT = "q_merchant";
        public static final String Q_MERCHANT_TOTAL_Q = "q_merchant_total_q";
        public static final String Q_MERCHANT_BATCH_ADD_OR_DEL = "q_merchant_batch_add_or_del";
        public static final String Q_MERCHANT_MODIFY_BALANCE_BY_ID = "q_merchant_modify_balance_by_id";
        public static final String Q_MERCHANTS = "q_merchants";
        public static final String Q_MERCHANT_BY_ID = "q_merchant/by/{id}";

    }


    /**
     * Q币代理操代记录URL
     */
    public static final class QMerchantEvent {
        public static final String Q_MERCHANT_EVENT = "q_merchant_event";
        public static final String Q_MERCHANT_EVENTS = "q_merchant_events";
        public static final String Q_MERCHANT_EVENT_BY_ID = "q_merchant_event/by/{id}";

    }

    /**
     * 总代理Q币操作记录URL
     */
    public static final class QGeneralAgentEvent {
        public static final String Q_GENERAL_AGENT_EVENT = "q_general_agent_event";
        public static final String Q_GENERAL_AGENT_EVENTS = "q_general_agent_events";
        public static final String Q_GENERAL_AGENT_EVENT_BY_ID = "q_general_agent_event_by/{id}";
    }

    /**
     * 总代理操作特殊播报
     */
    public static final class GeneralAgentBoardCast {
        public static final String GENERAL_AGENT_BOARDCAST = "general_agent_boardcast";
        public static final String GENERAL_AGENT_BOARDCAST_MODIFY_STATUS_BY_ID = "general_agent_boardcast_modify_status_by_id";
        public static final String GENERAL_AGENT_BOARDCAST_BY_ID = "general_agent_boardcast/by/{id}";
        public static final String GENERAL_AGENT_BOARDCASTS = "general_agent_boardcasts";
    }

    /**
     * 企业 二级管理Q币操作记录URL
     */
    public static final class QSecEnterPriseEvent {
        public static final String Q_SEC_ENTER_PRISE_EVENT = "q_sec_enter_prise_event";
        public static final String Q_SEC_ENTER_PRISE_EVENT_BY_ID = "q_sec_enter_prise_event/by/{id}";
        public static final String Q_SEC_ENTER_PRISE_EVENTS = "q_sec_enter_prise_events";
    }

    /**
     * 企业二级管理Q币URL
     */
    public static final class QSecEnterPrise {
        public static final String Q_SEC_ENTER_PRISE = "q_sec_enter_prise";
        public static final String Q_SEC_ENTER_PRISE_FIND_USER_ID = "q_sec_enter_prise_find_user_id";
        public static final String Q_SEC_ENTER_PRISE_BY_ID = "q_sec_enter_prise_by/{id}";
        public static final String Q_SEC_ENTER_PRISES = "q_sec_enter_prises";
        public static final String Q_SEC_ENTER_PRISES_NOPAGE = "q_sec_enter_prises_no_page";


    }


    /**
     * Q币企业 操作记录URL
     */
    public static final class QEnterPriseEvent {
        public static final String Q_ENTER_PRISE_EVENT = "q_enter_prise_event";
        public static final String Q_ENTER_PRISE_EVENTS = "q_enter_prise_events";
        public static final String Q_ENTER_PRISE_EVENT_BY_ID = "q_enter_prise_event/by/{id}";

    }

    /**
     * 设备终端URL
     */
    public static final class Terminal {
        public static final String TERMINAL = "terminal";
        public static final String TERMINAL_BY_ID = "terminal_by/{id}";
        public static final String TERMINALS = "terminals";
        public static final String TERMINAL_AUTH = "auth";
    }

    /**
     * 终端操作URL
     */
    public static final class TerminalEvent {
        public static final String TERMINAL_EVENT = "terminal_event";
        public static final String TERMINAL_EVENTS = "terminal_events";
        public static final String TERMINAL_EVENTS_BY_ID = "terminal_events_by/{id}";
    }

    /**
     * 总代信息统计URL
     */
    public static final class TotalInfo {
        public static final String TOTAL_INFO = "total_info";
        public static final String TOTAL_INFOS = "total_infos";
    }

    /**
     * 代理信息统计 URL
     */

    public static final class MerchantTotalInfo {
        public static final String MERCHANT_TOTAL_INFO = "merchant_total_info";
        public static final String MERCHANT_TOTAL_INFOS = "merchant_total_infos";
    }

    /**
     * 企业信息统计URL
     */
    public static final class EnterPriseTotalInfo {
        public static final String ENTER_PRISE_TOTAL_INFO = "enterprise_total_info";
        public static final String ENTER_PRISE_TOTAL_INFOS = "enterprise_total_infos";
    }

    /**
     * 档案数据
     */
    public static final class FileData {
        //获取分页录音数据
        public static final String AUDIO = "audio";
        //获取分页录音数据
        public static final String AUDIO_DOWN = "audio_down";
    }

    public static final class TaskManage{

        public static final  String TASK="task";
    }

    /**
     * 系统
     */
    public static final class System {
        //刷新令牌
        public static final String REFRESHTOKEN = "refreshToken";

    }

    /**
     * SIM 管理
     */
    public static final class SIM{
        public static final String SIM = "sim";
    }
}
