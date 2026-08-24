package it.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
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

