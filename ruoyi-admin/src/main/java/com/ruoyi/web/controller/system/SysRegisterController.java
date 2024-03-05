package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.EmailCodeBody;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysRegisterService;
import com.ruoyi.system.service.ISysConfigService;

import static com.ruoyi.common.core.domain.AjaxResult.MSG_TAG;

/**
 * 注册验证
 *
 * @author ruoyi
 */
@RestController
public class SysRegisterController extends BaseController {
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysUserService iSysUserService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user) {
        System.out.println(configService.selectConfigByKey("sys.account.registerUser"));
        System.out.println(!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))));
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

    @PostMapping("/emailRegister")
    public AjaxResult emailRegister(@RequestBody RegisterBody user) {
        System.out.println(configService.selectConfigByKey("sys.account.registerUser"));
        System.out.println(!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))));
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return error("当前系统没有开启注册功能！");
        }
        SysUser sysUser = iSysUserService.selectUserByEmail(user.getEmail());
        if(sysUser != null){
            throw new RuntimeException("保存用户'" + sysUser.getEmail() + "'失败，注册账号已存在");
        }
        EmailCodeBody emailCodeBody = new EmailCodeBody();
        emailCodeBody.setEmail(user.getEmail());
        emailCodeBody.setCode(user.getEmailCode());
        AjaxResult ajaxResult = iSysUserService.judgmentEmailCode(emailCodeBody);
        if(ajaxResult.isError()){
            throw new RuntimeException(String.valueOf(ajaxResult.get(MSG_TAG)));
        }

        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
