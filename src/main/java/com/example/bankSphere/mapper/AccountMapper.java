package com.example.bankSphere.mapper;

import com.example.bankSphere.dto.AccountDto;
import com.example.bankSphere.dto.UserModelDto;
import com.example.bankSphere.entity.Account;

public class AccountMapper {

    public static AccountDto toDto(Account account) {
        if (account == null) {
            return null;
        }

        UserModelDto userModelDto = null;
        if (account.getUser() != null) {
            userModelDto = new UserModelDto();

            // Autogenerated ID is fetched from the entity, username and password of the user
            userModelDto.setId(account.getUser().getId());
            userModelDto.setUsername(account.getUser().getUsername());
            userModelDto.setPassword(account.getUser().getPassword());;
            userModelDto.setEmail(account.getUser().getEmail());
            userModelDto.setPhone(account.getUser().getPhoneNumber());
            userModelDto.setRole(account.getUser().getRole().getValue());
            userModelDto.setKycStatus(account.getUser().getKycStatus().getValue());
        }

        // Form account dto
        return new AccountDto(
            account.getId(), // account ID, which is also autogenerated
            account.getBalance(),
            userModelDto
        );
    }
}
