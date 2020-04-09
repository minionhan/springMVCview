package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import errors.AuthenticationException;
import model.Auth;
import model.LoginCommand;
import model.SecurityLevel;
import service.Authenticator;
import service.LoginCommandValidator;


@Controller
@RequestMapping("/auth/login")
public class LoginController {

	private static final String LOGIN_FORM = "auth/loginForm";
	
	@Autowired
	private Authenticator authenticator;
	
	@ModelAttribute("loginTypes") //뷰단에 자동으로 보냄(객체타입으로 데이터를 뷰단에 뿌려줌) -> 리턴 loginTypes
	protected List<String> referenceData() {
		List<String> loginTypes = new ArrayList<String>();
		loginTypes.add("일반회원"); loginTypes.add("기업회원"); loginTypes.add("헤드헌터회원");
		System.out.println("loginTypes ---->" +loginTypes);
		return loginTypes;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String loginForm(LoginCommand loginCommand) {
		loginCommand.setSecurityLevel(SecurityLevel.HIGH);
		System.out.println("loginForm ---->>>");
		return LOGIN_FORM;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String login(@Valid LoginCommand loginCommand, Errors errors, HttpServletRequest request) {
		if (errors.hasErrors()){
			return LOGIN_FORM;
		}
		
		try {
			
			Auth auth = authenticator.authenticate(loginCommand.getEmail(), 
					loginCommand.getPassword());
			HttpSession session = request.getSession();
			session.setAttribute("auth", auth);
			
			return "redirect:/index.jsp";
			
		} catch (AuthenticationException ex) {
			errors.reject("invalidIdOrPassword");
		
			return LOGIN_FORM;
		}
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		System.out.println("initBinder ---->>>");
		binder.setValidator(new LoginCommandValidator()); //가장 먼저 실행됨. 입력됐는지 확인
	}
}
