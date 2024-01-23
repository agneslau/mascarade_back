package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.UserDto;
import mascarade.mascaradebackend.security.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Document(collection = "User")
@Builder
public record User(

        @MongoId
        ObjectId id,
        @Field("email")
        String email,

        @Field("name")
        String name,

        @Field("role")
        Role role
) {
        public static User fromUserDto(UserDto userDto) {
                return User.builder()
                        .email(userDto.email())
                        .name(userDto.name())
                        .role(userDto.role())
                        .build();
        }
}
