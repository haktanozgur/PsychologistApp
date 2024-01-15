package com.haktanozgur.PsychologistApp.Service;

import com.haktanozgur.PsychologistApp.Models.*;

public interface AuthService {

	LoginResponseModel login(LoginRequestModel model);
}
