package com.intuit.developer.sampleapp.oauth2.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.intuit.developer.sampleapp.oauth2.domain.OAuth2Configuration;
import com.intuit.developer.sampleapp.oauth2.helper.HttpHelper;
import com.intuit.developer.sampleapp.oauth2.service.ValidationService;

/**
 * @author dderose
 *
 */
@Controller
public class OAuth2Controller {
	
	private static final Logger logger = Logger.getLogger(OAuth2Controller.class);
	
	@Autowired
    public OAuth2Configuration oAuth2Configuration;
	
	@Autowired
    public ValidationService validationService;
	
	@Autowired
    public HttpHelper httpHelper;
	    
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/connected")
	public String connected() {
		return "connected";
	}
	
	/**
	 * Controller mapping for connectToQuickbooks button
	 * @return
	 */
	@RequestMapping("/connectToQuickbooks")
	public View connectToQuickbooks(HttpSession session) {
		logger.info("inside connectToQuickbooks ");
		return new RedirectView(prepareUrl(oAuth2Configuration.getC2QBScope(), generateCSRFToken(session)), true, true, false);
	}
	
	/**
	 * Controller mapping for signInWithIntuit button
	 * @return
	 */
	@RequestMapping("/signInWithIntuit")
	public View signInWithIntuit(HttpSession session) {
		logger.info("inside signInWithIntuit ");
		return new RedirectView("https://app.rdstation.com.br/api/platform/auth?client_id=8159bc58-83c2-470b-8f11-9e6b8d2ad12c&redirect_url=http://41a89885.ngrok.io/api-versao/prest/lead/oauth2redirect");
	}
	
	/**
	 * Controller mapping for getAppNow button
	 * @return
	 */
	@RequestMapping("/getAppNow")
	public View getAppNow(HttpSession session) {
		logger.info("inside getAppNow "  );
		return new RedirectView(prepareUrl(oAuth2Configuration.getAppNowScope(), generateCSRFToken(session)), true, true, false);
	}
	
	private String prepareUrl(String scope, String csrfToken)  {

			return "https://app.rdstation.com.br/api/platform/auth?client_id=8159bc58-83c2-470b-8f11-9e6b8d2ad12c&redirect_url=http://faf75e0f.ngrok.io/oauth2redirect";

	}
	
	private String generateCSRFToken(HttpSession session)  {
		String csrfToken = UUID.randomUUID().toString();
		session.setAttribute("csrfToken", csrfToken);
		return csrfToken;
	}

}
