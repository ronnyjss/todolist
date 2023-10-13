package br.art.jss.todolist.filter;

import br.art.jss.todolist.user.IUserRepository;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        var authorization = request.getHeader("Authorization");

        authorization = authorization.substring("Basic".length()).trim();

        byte[] authDecode = Base64.getDecoder().decode(authorization);

        var authString = new String(authDecode);

        String[] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];

        // Validar usuario
        var user = this.userRepository.findByUsername(username);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            // Validar senha
            var passwordVerify = BCrypt
                .verifyer()
                .verify(password.toCharArray(), user.getPassword())
                .verified;

            if (!passwordVerify) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        System.out.println(username);
        System.out.println(password);

        chain.doFilter(request, response);
    }
}
