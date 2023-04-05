package com.tutorial.springboot.request;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
