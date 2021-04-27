package com.epam.esm.dto.response;

import com.epam.esm.models.User;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class UserResponse extends RepresentationModel<UserResponse> {

    private int id;
    private String name;

    public UserResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserResponse)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        UserResponse that = (UserResponse) o;
        return getId() == that.getId() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getName());
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static UserResponse toDto(User user) {
        return builder().setId(user.getId()).setName(user.getName()).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String name;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(id, name);
        }
    }
}
