package com.dev.jorge.onlinebookstore.application.usecases;

import com.dev.jorge.onlinebookstore.application.command.user.SignInUserResult;
import com.dev.jorge.onlinebookstore.application.command.user.SignUpUserCommand;
import com.dev.jorge.onlinebookstore.application.command.user.SignInUserCommand;
import com.dev.jorge.onlinebookstore.application.command.user.SignUpUserResult;

public interface AuthUseCases {
    SignUpUserResult signUp(SignUpUserCommand command);
    SignInUserResult signIn(SignInUserCommand command);
}
