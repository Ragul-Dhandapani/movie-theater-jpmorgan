package com.jpmc.movie.theater.dao;

import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@Builder
public class Customer {
    private @NonNull String name;
    private String id; //not sure whether the id is to keep  number or any proofOfId as String => Clarification
}