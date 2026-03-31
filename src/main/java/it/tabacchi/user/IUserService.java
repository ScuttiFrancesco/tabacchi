package it.tabacchi.user;

public interface IUserService {

    UserDto update(Long id, UserCreationRequest request);

    UserDto getById(Long id);

    UserDto getByEmail(String email);

}
