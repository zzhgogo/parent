package com.zzh.comom;

import com.zzh.consts.ResultConstant;

/**
 * @author zhuhao
 * @version v1.0
 * @create 2018/1/19
 * @decription 数据传输基础对象，所有返回至前端的数据格式
 **/
public class ResultDto {

    private Integer code;

    private String msg;

    private Object data;

    public ResultDto(ResultConstant resultConstant) {
        super();
        this.code = resultConstant.getCode();
        this.msg = resultConstant.getMessage();
    }

    public ResultDto(ResultConstant resultConstant, Object data) {
        super();
        this.code = resultConstant.getCode();
        this.msg = resultConstant.getMessage();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}