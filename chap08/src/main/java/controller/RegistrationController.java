package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import errors.AuthenticationException;
import model.Auth;
import model.Code;
import model.LoginCommand;
import model.MemberRegistRequest;
import model.SecurityLevel;
import service.Authenticator;
import service.LoginCommandValidator;
import service.MemberRegistValidator;
import service.MemberService;


@Controller
@RequestMapping("/member/regist")
public class RegistrationController {

	private static final String MEMBER_REGISTRATION_FORM = "member/registrationForm";
	@Autowired
	private MemberService memberService;
	
	@ModelAttribute("jobCodes") //데이터를 뷰단에 자동으로 보냄 -> 리턴 loginTypes
	protected List<Code> jobCodes() {
		return Arrays.asList(new Code("1", "운동선수"), new Code("2", "프로그래머"), new Code("3", "예술가"), new Code("4", "작가"));
	}
	
	@ModelAttribute("favoriteOsNames")
	protected List<String> favoriteOs() {
		return Arrays.asList("윈도우XP", "윈도우7", "윈도우8", "맥OS", "우분투");
	}
	
	@ModelAttribute("tools")
	protected List<String> tools() {
		return Arrays.asList("이클립스", "인텔리J", "넷빈즈", "Vim");
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String form(@ModelAttribute("memberInfo") MemberRegistRequest memRegReq) {
		
		return MEMBER_REGISTRATION_FORM;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String regist(@ModelAttribute("memberInfo") MemberRegistRequest memRegReq,
			BindingResult bindingResult) {
		
		new MemberRegistValidator().validate(memRegReq, bindingResult);
		
		if(bindingResult.hasErrors()) {
			return MEMBER_REGISTRATION_FORM;
		}
		
		memberService.registNewMember(memRegReq);
		
		return "member/registered";
	}
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
}
