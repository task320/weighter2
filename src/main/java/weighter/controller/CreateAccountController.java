package weighter.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import weighter.constants.AttributeName;
import weighter.model.CreateAccountLogic;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/account")
public class CreateAccountController {

    @Produces(MediaType.TEXT_HTML)
    @Get("/input")
    @View("input_account")
    public Map<String, Object> inputAccountInfo() {
        return new HashMap<>();
    }

    @Produces(MediaType.TEXT_HTML)
    @Get("/create/complete")
    @View("complete_create_account")
    public Map<String, Object> completeCreateAccount() {
        return new HashMap<>();
    }

    @Produces(MediaType.TEXT_HTML)
    @Post("/create")
    @View("input_account")
    public HttpResponse<Object> createAccount(HttpRequest<?> request) {
        String usernameValue = String.valueOf(request.getAttribute(AttributeName.USERNAME));
        String passwordValue = String.valueOf(request.getAttribute(AttributeName.PASSWORD));

        boolean bStatus = true;
        HashMap<String, String> returnAttribute = new HashMap<String, String>();

        //ユーザIDがすでに登録されていないか確認する。
        if(!CreateAccountLogic.CheckDuplicateAccount(usernameValue))
        {
            return HttpResponse
                    .ok()
                    .setAttribute(AttributeName.FAIL_MSG, "入力したユーザーIDはすでに登録されています。");
        }

        //ユーザID登録
        if(!CreateAccountLogic.CreateAccount(usernameValue, passwordValue))
        {
            return HttpResponse
                    .ok()
                    .setAttribute(AttributeName.FAIL_MSG, "ユーザーIDの登録に失敗しました。<br />もう一度実行するか、管理者に問う合わせをお願い致します。");
        }

        return HttpResponse.redirect(URI.create("/account/create/complete"));
    }
}