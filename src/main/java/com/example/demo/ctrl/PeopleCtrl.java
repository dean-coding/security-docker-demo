package com.example.demo.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.PeopleVo;
import com.example.demo.security.token.ConsumerAuthToken;
import com.example.demo.security.token.ManagerAuthToken;

/**
 * @author fuhw
 * @date 2017年11月28日
 */
@Controller
public class PeopleCtrl {


	/**
	 * 普通用户登录首页
	 */
	@RequestMapping("/")
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof ConsumerAuthToken) {
			model.addAttribute("username", authentication.getName());
		}
		List<PeopleVo> many = new ArrayList<>();
		many.add(new PeopleVo("BB", 23));
		many.add(new PeopleVo("CC", 24));
		many.add(new PeopleVo("DD", 25));
		model.addAttribute("many", many);
		return "index";
	}

	/**
	 * 管理员登录首页
	 */
	@RequestMapping("/manager-index")
	public String managerIndex(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof ManagerAuthToken) {
			model.addAttribute("username", authentication.getName());
		}
		return "manager-index";
	}
	/**
	 * 当前用户信息
	 */
	@ResponseBody
	@RequestMapping("/userinfo")
	public Object userinfo() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
