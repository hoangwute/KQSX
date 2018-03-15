package com.wuochoang.kqsx.network;

import com.wuochoang.kqsx.model.ApiResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by quyenlx on 8/9/2017.
 */

public interface ApiService {
    String API_LOTTERY_RESULT = "get-so-ket-qua";

    @Headers({
            "Accept: */*",
            "Origin: https://ketqua.me",
            "X-Requested-With: XMLHttpRequest",
            "User-Agent: Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Mobile Safari/537.36",
            "Referer: https://ketqua.me/so-ket-qua",
            "Accept-Language: en,en-NZ;q=0.9,en-GB;q=0.8,en-US;q=0.7,en-AU;q=0.6,vi;q=0.5",
            "Cookie: _ga=GA1.2.449455120.1519890251; _gid=GA1.2.221665276.1519890251; PHPSESSID=9q5alsg2lj5158mga6lnt55ac5"
    })
    @FormUrlEncoded
    @POST(API_LOTTERY_RESULT)
    Observable<ApiResult> searchLotteryResult(@Field("province") int province,
                                 @Field("range") String dateRange);

}
