package org.example.damo.service;


import org.example.damo.dto.user.UserResponseDto;
import org.example.damo.entity.User;
import org.example.damo.exception.ResourceNotFoundException;
import org.example.damo.mapper.UserMapper;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.dto.user.UserDto;
import org.example.damo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper mapper;



    public ResponseEntity<BaseResponseWithAdditionalDateModel> getUser(){
        List<User> userData = userRepository.findAll();

        List<UserResponseDto> dtos = mapper.toDtoList(userData);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success" , "successfully retrieve user" , dtos));
    }


    public ResponseEntity<BaseResponeModel> createUser(UserDto payload){

        if (userRepository.existsByName(payload.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponeModel("fail" , "username already exist" ));
        }


        if(userRepository.existsByEmail(payload.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponeModel("fail" , "email already exist" ));
        }
        User user = mapper.toEntity(payload);

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponeModel("success" , "successfully created user"));
    }


    public ResponseEntity<BaseResponeModel> deleteUser(Long userId){
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id : "+userId));

        userRepository.deleteById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponeModel("success" , "successfully deleted user"));
    }



    public ResponseEntity<BaseResponeModel> updateUser(UserDto payload , Long userId){
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id : "+userId));


        mapper.updateEntityFromDto(existingUser, payload);

        userRepository.save(existingUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponeModel("success" , "successfully updated user"));
    }

    public ResponseEntity<BaseResponseWithAdditionalDateModel> getOneUser(@PathVariable("user_id") Long user_id) {
        User existingUser = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id : "+user_id));



        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success" , "get user successfully with id : "+user_id , existingUser));
    }

}
