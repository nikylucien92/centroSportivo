package it.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;


	/**
	 * REGISTRAZIONE
	 *
	 * POST /auth/register
	 */
	@PostMapping("/register")
	public AuthenticationResponse register(
			@RequestBody RegisterRequest registerRequest
	) {

		return authenticationService.register(
				registerRequest
		);
	}


	/**
	 * LOGIN
	 *
	 * POST /auth/login
	 */
	@PostMapping("/login")
	public AuthenticationResponse login(
			@RequestBody LoginRequest request
	) {

		return authenticationService.authenticate(
				request
		);
	}
}

