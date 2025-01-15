package kr.co.MyPick_server.DTO.eMail;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * MailSendReq is a Data Transfer Object used for encapsulating the necessary details
 * required to send an email to a user. This usually involves sending verification codes,
 * password reset links, or other security-related notifications.
 */
@Data
public class MailSendReq {

    /**
     * A unique identifier for the user within the system, often used to associate the email
     * send request with a specific account. This is important for ensuring that emails
     * are sent to the correct user.
     */
    private int idx;

    /**
     * The user's ID, which could be an email address or username, depending on the system's
     * requirements. This ID is used to address the email and must be validated to ensure it
     * corresponds to a valid user.
     */
    private String id;

    /**
     * The code to be included in the email, which may serve various purposes such as verification,
     * authentication, or password reset. The code must be securely generated and handled.
     */
    private String code;

}
