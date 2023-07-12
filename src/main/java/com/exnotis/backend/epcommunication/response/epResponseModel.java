package com.exnotis.backend.epcommunication.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class epResponseModel<T> {
    private T response;
    private epResponseStatus status;
}
