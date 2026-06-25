package dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ContactsDto {
    private List<Contact> contacts;
}
