package com.renxuanchen.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {

    public static final ResultObj LOGIN_SUCCESS = new ResultObj(Constast.OK, "登录成功");
    public static final ResultObj LOGIN_ERROR_LOGINNAME = new ResultObj(Constast.ERROR, "登录失败,用户名错误");
    public static final ResultObj LOGIN_ERROR_PASS = new ResultObj(Constast.ERROR, "登录失败,密码错误");

    public static final ResultObj UPDATE_SUCCESS = new ResultObj(Constast.OK, "更新成功");
    public static final ResultObj UPDATE_ERROR = new ResultObj(Constast.ERROR, "更新失败");

    public static final ResultObj ADD_WORK_SUCCESS = new ResultObj(Constast.OK, "打卡成功");
    public static final ResultObj ADD_WORK_ERROR = new ResultObj(Constast.ERROR, "打卡失败");
    public static final ResultObj ADD_WORK_ERROR_1 = new ResultObj(Constast.ERROR, "打卡失败,今日已打卡！！！");
    public static final ResultObj ADD_SUCCESS = new ResultObj(Constast.OK, "添加成功");
    public static final ResultObj ADD_ERROR = new ResultObj(Constast.ERROR, "添加失败");

    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constast.OK, "删除成功");
    public static final ResultObj DELETE_ERROR = new ResultObj(Constast.ERROR, "删除失败");
    public static final ResultObj DELETE_ERROR_MGR = new ResultObj(Constast.ERROR, "删除失败,此人还是其他用户的领导,请用户更换领导后再删除");
    public static final ResultObj DELETE_ERROR_TASK = new ResultObj(Constast.ERROR, "删除失败,此流程还有任务未执行完毕");

    public static final ResultObj RESET_SUCCESS = new ResultObj(Constast.OK, "重置成功");
    public static final ResultObj RESET_ERROR = new ResultObj(Constast.ERROR, "重置失败");

    public static final ResultObj DISPATCH_SUCCESS = new ResultObj(Constast.OK, "分配成功");
    public static final ResultObj DISPATCH_ERROR = new ResultObj(Constast.ERROR, "分配失败");

    public static final ResultObj OPERATE_SUCCESS = new ResultObj(Constast.OK, "同步成功");

    public static final ResultObj PWD_SUCCESS = new ResultObj(Constast.OK, "密码正确");
    public static final ResultObj PWD_ERROR = new ResultObj(Constast.ERROR, "密码错误");

    public static final ResultObj DEPLOYMENT_SUCCESS = new ResultObj(Constast.OK, "部署成功");
    public static final ResultObj DEPLOYMENT_ERROR = new ResultObj(Constast.ERROR, "部署失败");

    public static final ResultObj SUMBIT_SUCCESS = new ResultObj(Constast.OK, "提交成功");
    public static final ResultObj SUMBIT_ERROR = new ResultObj(Constast.ERROR, "提交失败");

    public static final ResultObj COMPLETE_SUCCESS =  new ResultObj(Constast.OK, "办理成功");
    public static final ResultObj COMPLETE_ERROR =  new ResultObj(Constast.OK, "办理失败");
    public static final ResultObj APPROVAL_SUCCESS =  new ResultObj(Constast.OK, "审核成功");
    public static final ResultObj APPROVAL_ERROR =  new ResultObj(Constast.ERROR, "审核失败");
    public static final ResultObj APPROVAL_ALREADY_ERROR =  new ResultObj(Constast.ERROR, "不能审核自己的请求");
    private Integer code;
    private String msg;
    private Object data;

    public static final ResultObj OPERATE_ERROR = new ResultObj(500, "操作失败"); // 新增

    // 构造函数
    public ResultObj(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 链式设置消息（返回当前对象）
    public ResultObj setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    // Getter 方法
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    // 新增通用成功方法
    public static ResultObj success() {
        return new ResultObj(200, "操作成功", null);
    }

    public static ResultObj success(Object data) {
        return new ResultObj(200, "操作成功", data);
    }

    public static ResultObj error(String msg) {
        return new ResultObj(500, msg, null);
    }

    // 修改构造函数
    public ResultObj(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
