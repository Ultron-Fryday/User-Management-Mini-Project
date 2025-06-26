package com.omdev.um.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.omdev.um.dto.LoginFormDTO;
import com.omdev.um.dto.QuoteApiResponseDTO;
import com.omdev.um.dto.RegisterFormDTO;
import com.omdev.um.dto.ResetPasswordFormDTO;
import com.omdev.um.dto.UserDTO;
import com.omdev.um.service.DashboardService;
import com.omdev.um.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	private final DashboardService dashboardService;

	@GetMapping("/")
	public String getIndex() {
		return "index";
	}

	@GetMapping("/login")
	public String getLoginPage(Model model) {
		log.info("getLoginPage called");
		LoginFormDTO loginFormDTO = new LoginFormDTO();
		model.addAttribute("logform", loginFormDTO);
		return "login";
	}

	@PostMapping("/login")
	public String loginUserVerify(@ModelAttribute("logform") LoginFormDTO loginFormDTO, HttpServletRequest request,
			Model model) {
		log.info("loginUserVerify-- email:{}, password:{}", loginFormDTO.getEmail());
		UserDTO loginUser = userService.login(loginFormDTO);
		if (loginUser == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			log.info("Login Failed");
			return "login";
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("userId", loginUser.getId());
		session.setAttribute("email", loginUser.getEmail());
		if (!loginUser.isPassword_status()) {
			log.info("loginUserVerify-- password status: {}", (loginUser.isPassword_status() ? "true" : "false"));
			log.info("user first login redirect to reset password");
			return "redirect:/resetpassword";
		}
		log.info("loginUserVerify-- Login Success redirecting to dashboard page");
		return "redirect:/dashboard";
	}

	@GetMapping("/register")
	public String getRegisterForm(Model model) {
		log.info("getRegisterForm called");
		RegisterFormDTO registerFormDTO = new RegisterFormDTO();
		model.addAttribute("regform", registerFormDTO);

		Map<Integer, String> countriesMap = userService.getCountries();
		model.addAttribute("countries", countriesMap);
		return "register";
	}

	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable Integer countryId) {
		Map<Integer, String> statesMap = userService.getStates(countryId);
		return statesMap;
	}

	@GetMapping("/cities/{stateId}")
	@ResponseBody
	public Map<Integer, String> getCitites(@PathVariable Integer stateId) {
		Map<Integer, String> citiesMap = userService.getCities(stateId);
		return citiesMap;
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("regform") RegisterFormDTO registerFormDTO, Model model)
			throws Exception {
		log.info("registerUser-- Email :{}", registerFormDTO.getEmail());
		boolean isEmailUnique = userService.duplicateEmailCheck(registerFormDTO.getEmail());
		if (!isEmailUnique) {
			model.addAttribute("emsg", "Duplicate Email");
			Map<Integer, String> countriesMap = userService.getCountries();
			model.addAttribute("countries",countriesMap);
			return "register";
		}

		boolean isRegistered = userService.registerUser(registerFormDTO);
		if (isRegistered) {
			// success
			model.addAttribute("smsg", "Registration Successfull and Password Send To Email");
			Map<Integer, String> countriesMap = userService.getCountries();
			model.addAttribute("countries",countriesMap);
			return "register";
		} else {
			// Failure
			model.addAttribute("emsg", "Registration Failed");
			return "register";
		}

	}

	@GetMapping("/dashboard")
	public String getDashboard(Model model,HttpServletRequest request) {
		log.info("getDashboard called");
		
		HttpSession session  = request.getSession(false);
		if(session == null) {
			return "errorpage";
		}
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			return "errorpage";
		}
		QuoteApiResponseDTO quoteResponseDTO = dashboardService.getRandomQuote();
		model.addAttribute("quoteresp", quoteResponseDTO);
		return "dashboard";
	}

	@GetMapping("/userprofile")
	public String getUserDetails(HttpServletRequest request, Model model) {
		log.info("getUserDetails-- called");
		HttpSession session  = request.getSession(false);
		if(session == null) {
			return "errorpage";
		}
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			return "errorpage";
		}
		UserDTO userObj = userService.findUserById(userId);
		model.addAttribute("userdetail", userObj);
		return "userprofile";
	}

	@GetMapping("/resetpassword")
	public String getResetUserPasswordPage(Model model, HttpServletRequest request) {
		log.info("getResetUserPasswordPage called");
		HttpSession session = request.getSession(false);
		String email = (String) session.getAttribute("email");
		Integer userId = (Integer) session.getAttribute("userId");
		ResetPasswordFormDTO resetPwdForm = new ResetPasswordFormDTO();
		resetPwdForm.setId(userId);
		resetPwdForm.setEmail(email);

		model.addAttribute("resetform", resetPwdForm);
		return "resetpassword";
	}

	@PostMapping("/resetpassword")
	public String resetUserPassword(@ModelAttribute("resetform") ResetPasswordFormDTO resetPwdForm,
			HttpServletRequest request, Model model) throws Exception {
		log.info("resetUserPassword-- userId:{}", resetPwdForm.getId());
		if (!resetPwdForm.getConfirmPassword().equals(resetPwdForm.getNewPassword())) {
			log.info("New Password and Confirm Password not matched");
			model.addAttribute("emsg", "New Password and Confirm Password not matched");
			return "resetpassword";
		}
		boolean resetPassword = userService.resetPassword(resetPwdForm);
		if (!resetPassword) {
			model.addAttribute("emsg", "Reset Pasword Failed");
			return "resetpassword";
		}
		log.info("resetUserPassword-- Pasword Update Success");
		return "redirect:/login";
	}

	@GetMapping("/editprofile")
	public String editProfile() {
		return "editprofile";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		// getting existing session and invalidate
		HttpSession session = request.getSession(false);
		session.invalidate();
		// redirect to login page
		return "redirect:/login";
	}

}
