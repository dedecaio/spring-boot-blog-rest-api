package com.caioprogramador.blog.service;

import com.caioprogramador.blog.dto.LoginDTO;
import com.caioprogramador.blog.dto.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}
