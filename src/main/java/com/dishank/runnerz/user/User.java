package com.dishank.runnerz.user;

public record User(
        String id,
        String name,
        String username,
        String email,
        Address address,
        String phone,
        String website,
        Company company
) {
}
