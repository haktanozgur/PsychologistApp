package com.haktanozgur.PsychologistApp.Models;

import com.haktanozgur.PsychologistApp.Enum.*;

import lombok.Data;

@Data
public class AddUserRequestModel {

	private String username;
	private String password;
	private String identifier;
	private String name;
	private String surname;
	private Roles role;
}
