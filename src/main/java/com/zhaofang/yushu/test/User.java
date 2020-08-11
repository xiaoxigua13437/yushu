package com.zhaofang.yushu.test;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@ToString
@Builder
public class User {

    @NonNull
    private Integer id;
    @NonNull
    private Integer age;

    private String name;

    @Singular(value = "testOccupations")
    private Set<String> occupations;


    @SneakyThrows
    public static void main(String[] args){


        System.out.println("CPU可用核心数:" + Runtime.getRuntime().availableProcessors());

       /* System.out.println( new User(1,14));
        System.out.println("--------------");
        System.out.println(User.of(2,10));
        System.out.println("--------------");
        System.out.println(new User());
        System.out.println("--------------");
        System.out.println(new User(3,12,"玉书"));

        User user = User.builder()
                                .testOccupations("theShy")
                                .testOccupations("jackLove")
                                .id(1)
                                .age(14)
                                .name("yuShu")
                                .build();

        System.out.println(user);*/

    }



}
