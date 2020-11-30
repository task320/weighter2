package weighter.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.micronaut.session.Session;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static weighter.constants.WeightData.NOTHING_WEIGHT;
import weighter.constants.AttributeName;
import weighter.model.MainLogic;
import weighter.model.User;
import weighter.util.ObjectUtil;

import javax.annotation.Nullable;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/main")
public class MainController {
    @Produces(MediaType.TEXT_HTML)
    @Get()
    @View("main")
    public Map<String, Object> viewMain(HttpRequest<?> request, @Nullable Principal principal) {

        String id = principal.getName();

        Map<String, Object> data = new HashMap<>();

        // 今日の体重取得
        Double todayWeight = MainLogic.GetTodayWeight(id);
        // 今日の体重が登録されているか
        if (todayWeight == NOTHING_WEIGHT)
        {
            data.put(AttributeName.TODAY_WEIGHT.toString(), "登録されていません");
            data.put(AttributeName.REGIST_FLG.toString(), 0);
        }
        else
        {
            data.put(AttributeName.TODAY_WEIGHT.toString(), todayWeight.toString() + "Kg");
            data.put(AttributeName.REGIST_FLG.toString(), 1);
        }

        // 過去30日分の体重データを表示する。
        data.put(AttributeName.PAST_30_DAYS.toString(), MainLogic.CreatePast30DaysWeightData(id));

        return data;
    }

    @Produces(MediaType.TEXT_HTML)
    @Post()
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @View("main")
    public Map<String, Object> insertWeight(HttpRequest<?> request, @Nullable Principal principal,
                                            @Body("registFlg") String regitFlg,
                                            @Body("weight") String weightValue) {
        String id = principal.getName();

        Map<String, Object> data = new HashMap<>();

        // コメント
        StringBuilder comment = new StringBuilder();
        comment.append("");

        //上書きフラグ
        int registFlg = 0;
        String strRegistFlg = regitFlg;
        if(strRegistFlg != null && !(strRegistFlg.isEmpty()))
            if(strRegistFlg.equals("1"))
                registFlg = 1;

        try {
            // 体重
            Double weight = ObjectUtil.toDouble(weightValue);

            // 今日の体重データを登録
            if (!MainLogic.ProcGateWeightData(id, new Date(), weight, comment, registFlg)) {
                data.put(AttributeName.FAIL_MSG.toString(), "体重データの登録に失敗しました。もう一度やり直してください。");
            }

        } catch (NullPointerException e) {
            data.put(AttributeName.FAIL_MSG.toString(), e.getMessage());
        } catch (NumberFormatException e) {
            data.put(AttributeName.FAIL_MSG.toString(), "登録できない値が入力されています。もう一度やり直してください。");
        }

        // 今日の体重取得
        Double todayWeight = MainLogic.GetTodayWeight(id);
        // 今日の体重が登録されているか
        if (todayWeight == NOTHING_WEIGHT)
        {
            data.put(AttributeName.TODAY_WEIGHT.toString(), "登録されていません");
            data.put(AttributeName.REGIST_FLG.toString(), 0);
        }
        else
        {
            data.put(AttributeName.TODAY_WEIGHT.toString(), todayWeight.toString() + "Kg");
            data.put(AttributeName.REGIST_FLG.toString(), 1);
        }

        // 過去30日分の体重データを表示する。
        data.put(AttributeName.PAST_30_DAYS.toString(), MainLogic.CreatePast30DaysWeightData(id));

        return data;
    }
}