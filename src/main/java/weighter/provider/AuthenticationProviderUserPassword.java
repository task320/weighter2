package weighter.provider;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.ArrayList;

import weighter.model.LoginLogic;
import weighter.model.User;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.create(emitter -> {
            String userName = authenticationRequest.getIdentity().toString();
            String password = authenticationRequest.getSecret().toString();

            User user = LoginLogic.Login(userName, password);

            if(user == null) {
                emitter.onError(new AuthenticationException(new AuthenticationFailed()));
            }
            else {
                UserDetails userDetails = new UserDetails(user.getId(), new ArrayList<>());
                emitter.onNext(userDetails);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);
    }
}