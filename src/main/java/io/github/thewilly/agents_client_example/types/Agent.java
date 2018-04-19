/*
 * This source file is part of the Agents_i3a open source project.
 *
 * Copyright (c) 2017 Agents_i3a project authors.
 * Licensed under MIT License.
 *
 * See /LICENSE for license information.
 * 
 * This class is based on the AlbUtil project.
 * 
 */
package io.github.thewilly.agents_client_example.types;

import lombok.Data;

/**
 * Instance of the user.
 * 
 * @author Damian.
 * @since 06/02/2017
 */
@Data
public class Agent {

    private String db_id;
    private String name;
    private String email;
    private String password;
    private String id;
    private String location;
    private int kindCode;
    private String kind;
}
