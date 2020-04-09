package controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;


@Controller
public class LocalChangeController {
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@RequestMapping("/changeLanguage")
	public String change(@RequestParam("lang") String language,
			HttpServletRequest request, HttpServletResponse response){
		Locale locale = new Locale(language);
		localeResolver.setLocale(request,response,locale);
		return "redirect:/index";
	}
	public void setLocalResolver(LocaleResolver localeResolver){
		this.localeResolver = localeResolver;
	}

}
