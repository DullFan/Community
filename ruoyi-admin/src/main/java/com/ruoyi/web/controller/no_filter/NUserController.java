package com.ruoyi.web.controller.no_filter;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.EmailCodeBody;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/filterN/user")
public class NUserController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysPostService postService;

    /**
     * 根据用户编号获取详细信息(用户没登录情况下)
     */
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfoNo(@PathVariable(value = "userId", required = false) Long userId) {
        AjaxResult ajax = AjaxResult.success();
        SysUser sysUser = userService.selectUserById(userId);
        sysUser.setUserName("");
        sysUser.setIdCard("");
        sysUser.setPhonenumber("");
        sysUser.setPassword("");
        ajax.put(AjaxResult.DATA_TAG, sysUser);
        ajax.put("postIds", postService.selectPostListByUserId(userId));
        ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        return ajax;
    }

    @PostMapping("/forgetThePassword")
    @Operation(summary = "忘记密码")
    public AjaxResult forgetThePassword(@RequestBody EmailCodeBody emailCodeBody) {
        return userService.forgetThePassword(emailCodeBody);
    }

    @PostMapping("/judgmentEmailCode")
    @Operation(summary = "验证邮箱验证码")
    public AjaxResult judgmentEmailCode(@RequestBody EmailCodeBody emailCodeBody) {
        return userService.judgmentEmailCode(emailCodeBody);
    }

    /**
     * 发送邮箱验证码
     */
    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/sendEmailCode")
    public AjaxResult sendEmailCode(@RequestParam String email) {
        return userService.sendEmailCode(email);
    }
}
