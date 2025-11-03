package org.example.damo.service;



import org.example.damo.dto.user.ChangePasswordUserDto;
import org.example.damo.dto.user.UserResponseDto;
import org.example.damo.dto.user.UserUpdateDto;
import org.example.damo.entity.User;
import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.mapper.UserMapper;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Objects;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;




    public ResponseEntity<BaseResponseWithAdditionalDateModel> getUser(){
        List<User> userData = userRepository.findAll();

        List<UserResponseDto> dtos = mapper.toDtoList(userData);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success" , "successfully retrieve user" , dtos));
    }



    public ResponseEntity<BaseResponeModel> deleteUser(Long userId){
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + userId));

        userRepository.deleteById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponeModel("success" , "successfully deleted user"));
    }



    public ResponseEntity<BaseResponeModel> updateUser(UserUpdateDto payload , Long userId){
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + userId));


        mapper.updateEntityFromDto(existingUser, payload);

        userRepository.save(existingUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponeModel("success" , "successfully updated user"));
    }

    public ResponseEntity<BaseResponseWithAdditionalDateModel> getOneUser(Long user_id) {

        User existingUser = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + user_id));



        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success" , "get user successfully with id : "+user_id , existingUser));
    }

    public ResponseEntity<BaseResponeModel> changePassword( Long userId , ChangePasswordUserDto payload) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id : " + userId));


        //old password is incorrect
        if (!Objects.equals(user.getPassword(), payload.getOldPassword())){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new BaseResponeModel("fail" , "old password is incorrect"));
        }

        //new password and confirm password not match
        if (!Objects.equals(payload.getNewPassword(), payload.getConfirmPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponeModel("fail" , "new password and confirm password must be the same"));
        }

        mapper.updateEntityChangePassword(user , payload.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success" , "successfully changed password"));
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .or(()->userRepository.findByEmail(username))
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("user not found with username : " + username);

                });
    }
}
