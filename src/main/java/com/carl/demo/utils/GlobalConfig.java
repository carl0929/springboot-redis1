package com.carl.demo.utils;


public class GlobalConfig {

    public static final String M_VERSION = "1.3.8";

    /**
     * 文件根目录
     */
    //public static final String M_FILES_BASE_DIR = "c:\\sdk_files";
    public static final String M_FILES_BASE_DIR = "/usr/local/sdk_files";
    /**
     * 网络环境：数据连接
     */
    public static final int M_NET_ENV_MOBILE = 0;

    /**
     * 网络环境：WiFi
     */
    public static final int M_NET_ENV_WIFI = 1;
    /**
     * 网络环境：所有的
     */
    public static final int M_NET_ENV_ALL = 101;

    /**
     * 网络状态类型是否有效
     * @param env
     * @return
     */
    public static boolean isNetworkEnvValid(int env) {
        return (env == M_NET_ENV_MOBILE)
                || (env == M_NET_ENV_WIFI)
                || (env == M_NET_ENV_ALL);
    }

    /**
     * 任务状态：正在运行
     */
    public static final int M_TASK_STATE_RUNNING = 1;

    /**
     * 任务状态：暂停运行
     */
    public static final int M_TASK_STATE_PAUSE = 0;

    /**
     * 任务状态是否合法
     * @param state
     * @return
     */
    public static boolean isTaskStateValid(int state) {
        return (state == M_TASK_STATE_RUNNING)
                || (state == M_TASK_STATE_PAUSE);
    }

    /**
     * 正常信息埋点
     */
    public static final int M_SPOT_TYPE_NORMAL 	= 1;

    /**
     * 错误信息埋点
     */
    public static final int M_SPOT_TYPE_ERR		= 2;

    /**
     * 测试任务
     */
    public static final int M_TASK_TYPE_TEST	= 1;

    /**
     * 线上任务
     */
    public static final int M_TASK_TYPE_ONLINE	= 0;

    /**
     * 任务是否是测试任务
     * @param type
     * @return
     */
    public static boolean isTaskForTest(int type) {
        return type == M_TASK_TYPE_TEST;
    }

    public static final String M_PAYOUT_TYPE_CPA = "CPA";
    public static final String M_PAYOUT_TYPE_CPI = "CPI";
    public static final String M_PAYOUT_TYPE_CPS = "CPS";
    public static final String M_PAYOUT_TYPE_CPC = "CPC";
    public static final String M_PAYOUT_TYPE_CPM = "CPM";
    public static final String M_PAYOUT_TYPE_CPE = "CPE";
    public static final String M_PAYOUT_TYPE_SMARTLINK = "SmartLink";

    /**
     * 任务执行结果——成功：{@value}
     */
    public static final int M_TASK_EXEC_RESULT_SUCCEED = 1;

    /**
     * 任务执行结果——失败：{@value}
     */
    public static final int M_TASK_EXEC_RESULT_FAILED = 2;

    /**
     * 任务是否执行成功
     * @param rst
     * @return
     */
    public static boolean isTaskExecSucceed(int rst) {
        return rst == M_TASK_EXEC_RESULT_SUCCEED;
    }

    /**
     * 任务执行结果是否有效
     * @param rst
     * @return
     */
    public static boolean isTaskExecResultValid(int rst) {
        return (rst == M_TASK_EXEC_RESULT_FAILED || rst == M_TASK_EXEC_RESULT_SUCCEED);
    }

    /**
     * 单个任务状态——成功：{@value}
     */
    public static final int M_TASK_RUNNING_STATE_SUCCEED = 1;
    /**
     * 单个任务状态——失败：{@value}
     */
    public static final int M_TASK_RUNNING_STATE_FAILED = 2;
    /**
     * 单个任务状态——正在执行：{@value}
     */
    public static final int M_TASK_RUNNING_STATE_RUNNING = 3;
    /**
     * 单个任务状态——上报无效数据：{@value}
     */
    public static final int M_TASK_RUNNING_STATE_INVALID_REPORT = 4;

    /**
     * 任务运行状态是否有效
     * @param state
     * @return
     */
    public static boolean isTaskRunningStateValid(int state) {
        boolean res = false;

        switch(state) {
            case M_TASK_RUNNING_STATE_SUCCEED:
            case M_TASK_RUNNING_STATE_FAILED:
            case M_TASK_RUNNING_STATE_RUNNING:
            case M_TASK_RUNNING_STATE_INVALID_REPORT:
                res = true;
                break;
        }

        return res;
    }

    /**
     * 任务请求状态——成功
     */
    public static final int M_TASK_REQUEST_STATUS_SUCCEED = 0;
}
