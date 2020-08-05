package bean;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBean {

    private String deviceId;
    private String mobile;
    private String graphicCode;
    private String messageCode;
    private String password;

}
