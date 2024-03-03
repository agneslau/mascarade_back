package mascarade.mascaradebackend.entities;

import mascarade.mascaradebackend.security.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public record DeletedUser(@Field("id")
                          ObjectId id,
                          @Field("email")
                          String email,

                          @Field("name")
                          String name,

                          @Field("roles")
                          List<Role> roles,

                          @Field("password")
                          String password) {
    public static DeletedUser fromUser(User user) {
        return new DeletedUser(user.id(), user.email(), user.name(), user.roles(), user.password());
    }

}
