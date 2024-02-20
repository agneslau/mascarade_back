package mascarade.mascaradebackend.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import mascarade.mascaradebackend.dtos.UserDto;
import mascarade.mascaradebackend.security.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document(collection = "User")
@Builder
public record User(

        @MongoId
        ObjectId id,
        @Field("email")
        String email,

        @Field("name")
        String name,

        @Field("roles")
        List<Role> roles,

        @Field("password")
        String password
) implements UserDetails {
        public static User fromUserDto(UserDto userDto) {
                return User.builder()
                        .email(userDto.email())
                        .name(userDto.name())
                        .roles(userDto.roles())
                        .password(userDto.password())
                        .build();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .toList();
        }

        @Override
        public String getPassword() {
                return password;
        }

        @Override
        public String getUsername() {
                return email;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}
