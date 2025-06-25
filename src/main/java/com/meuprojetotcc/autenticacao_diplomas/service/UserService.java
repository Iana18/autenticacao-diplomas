package com.meuprojetotcc.autenticacao_diplomas.service;


import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {




        private final UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public User salvar(User user) {
            return userRepository.save(user);
        }

        public Optional<User> buscarPorId(Long id) {
            return userRepository.findById(id);
        }

        public Optional<User> buscarPorEmail(String email) {
            return userRepository.findByEmail(email);
        }

        public List<User> listarTodos() {
            return userRepository.findAll();
        }

        public void deletar(Long id) {
            userRepository.deleteById(id);
        }
    }


