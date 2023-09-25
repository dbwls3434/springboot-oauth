package org.yujin.myproc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yujin.myproc.dto.RoleType;
import org.yujin.myproc.dto.UserDTO;
import org.yujin.myproc.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Log4j2
public class UserAPIController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserDTO> joinProc(@RequestBody UserDTO userDTO) throws Exception {

        userDTO.setRole(RoleType.ROLE_USER.toString());

        UserDTO savedUserDTO = userService.saveUser(userDTO);

        return ResponseEntity.ok().body(savedUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginProc(@RequestBody UserDTO userDTO) throws Exception {

        UserDTO loginUserDTO = userService.loginUser(userDTO);



        return ResponseEntity.ok().body(loginUserDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutProc() {

        //

        return ResponseEntity.ok().body("success");
    }

}
