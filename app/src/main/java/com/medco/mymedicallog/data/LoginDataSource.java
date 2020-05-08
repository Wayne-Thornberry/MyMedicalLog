package com.medco.mymedicallog.data;

import com.medco.mymedicallog.data.model.LoggedInUser;
import com.medco.mymedicallog.entities.AccountDetails;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(AccountDetails mAccountDetails, String username, String password) {

        try {
            if(mAccountDetails.username.equals(username) && mAccountDetails.password.equals(password)) {
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                mAccountDetails.username);
                return new Result.Success<>(fakeUser);
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
