package com.ruoyi.common.core.domain.model;

import lombok.Data;

@Data
public class EmailCodeBody {
    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱
     */
    private String code;

    /**
     * 新密码
     */
    private String newPassword;
}
