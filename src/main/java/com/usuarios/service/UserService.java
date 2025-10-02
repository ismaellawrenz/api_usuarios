package com.usuarios.service;

import com.usuarios.dto.PageResponse;
import com.usuarios.dto.UserRequest;
import com.usuarios.dto.UserResponse;
import com.usuarios.entity.User;
import com.usuarios.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserResponse save(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com este email informado.");
        }
        User user = new User();
        user.setNome(userRequest.nome());
        user.setEmail(userRequest.email());
        user = userRepository.save(user);
        return toResponse(user);
    }

    public UserResponse findById(Long id) {
        var user =  userRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuário não encontrado."));
        return toResponse(user);
    }

    public UserResponse update(UserRequest userRequest, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        if(userRepository.findByEmailAndIdNot(userRequest.email(), id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com este email informado.");
        }
        user.setId(id);
        user.setNome(userRequest.nome());
        user.setEmail(userRequest.email());
        user = userRepository.save(user);
        return toResponse(user);
    }

    public PageResponse findAll(int  page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        if(sortDirection.equals("asc")){
            sort = sort.ascending();
        }else{
            sort = sort.descending();
        }
        var pageable = PageRequest.of(page, size, sort);
        var users = userRepository.findAll(pageable);

        return new PageResponse(users.getContent().stream().map(this::toResponse).toList(), users.getNumber(),users.getSize(), users.getTotalElements());
    }

    public void  deleteById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getNome(), user.getEmail(), user.getDataCriacao());
    }


}
