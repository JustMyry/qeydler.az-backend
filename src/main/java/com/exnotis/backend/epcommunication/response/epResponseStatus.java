package com.exnotis.backend.epcommunication.response;


import com.exnotis.backend.exception.StatusCode;
import com.exnotis.backend.exception.StatusMessage;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class epResponseStatus {
    private Integer code;
    private String message;


    public static epResponseStatus getSuccess(){
        return epResponseStatus.builder()
                .code(StatusCode.SUCCESS)
                .message(StatusMessage.SUCCESS)
                .build();
    }


    public static epResponseStatus getRequestIsInvalid(){
        return epResponseStatus.builder()
                .code(StatusCode.REQUEST_IS_INVALID)
                .message(StatusMessage.REQUEST_IS_INVALID)
                .build();
    }


    public static epResponseStatus getJwtIsInvalid(){
        return epResponseStatus.builder()
                .code(StatusCode.JWT_IS_NOT_VALID)
                .message(StatusMessage.JWT_IS_NOT_VALID)
                .build();
    }


    public static epResponseStatus getPassIsWrong(){
        return epResponseStatus.builder()
                .code(StatusCode.PASSWORD_IS_WRONG)
                .message(StatusMessage.PASSWORD_IS_WRONG)
                .build();
    }

}