package com.c4.routy.domain.user.service;



import com.c4.routy.domain.user.dto.UserDTO;
import com.c4.routy.domain.user.entity.UserEntity;
import com.c4.routy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO findUserByEmail(UserDTO userDTO){
        String email = userDTO.getEmail();
        System.out.println("email(service) = " + email);
        UserEntity userEntity = userRepository.findByUserEmail(email);
        if(userEntity == null){
            return null;
        }else{
            return modelMapper.map(userEntity, UserDTO.class);
        }
    }

    public String findUserByUsernameAndPhone(String username, String phone) {
       UserEntity user = userRepository.findUserEntitiesByUsernameAndPhone(username,phone);
       if(user == null){
           return "존재하지 않는 회원입니다.";
       }else{
           return user.getEmail();
       }
    }

    public void insertUser(UserDTO userDTO){
        System.out.println("userDTO = " + userDTO);
        if(userDTO.getPassword() != null){
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(modelMapper.map(userDTO, UserEntity.class));
    }
}
