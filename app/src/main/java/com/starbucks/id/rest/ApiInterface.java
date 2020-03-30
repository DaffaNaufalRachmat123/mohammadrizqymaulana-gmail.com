package com.starbucks.id.rest;

/*
 * Created by Angga N P on 12/13/2017.
 */

import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.model.extension.WhatsNewModel;
import com.starbucks.id.model.inbox.DetailOfferResponseModel;
import com.starbucks.id.model.inbox.OfferModel;
import com.starbucks.id.model.inbox.ResponseMessageCount;
import com.starbucks.id.model.inbox.ResponseMessageList;
import com.starbucks.id.model.inbox.ResponseOfferModel;
import com.starbucks.id.model.login.LoginResponseModel;
import com.starbucks.id.model.login.ResponseSignUp;
import com.starbucks.id.model.menu.MenuModel;
import com.starbucks.id.model.pay.CardReloadHistoryResponse;
import com.starbucks.id.model.pay.ReportLostResponse;
import com.starbucks.id.model.pay.ResponseCardBalance;
import com.starbucks.id.model.pay.ResponseOpenTrx;
import com.starbucks.id.model.pay.ResponseReloadCard;
import com.starbucks.id.model.pay.ResponseTransferBalance;
import com.starbucks.id.model.reward.RewardModel;
import com.starbucks.id.model.reward_history.RewardResponseModel;
import com.starbucks.id.model.store.StoresModel;
import com.starbucks.id.model.trx.ActivityResponseModel;
import com.starbucks.id.model.user.ResponseRefreshToken;
import com.starbucks.id.model.user.UserResponseModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    /*Version Management Function**************************************/
    //PROMO CODE
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> checkVersion(@Field("r") String r,
                                   @Field("sbux") String sbux);


    /*User Management Function**************************************/
    //Sign In
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<LoginResponseModel> signIn(@Field("r") String r,
                                    @Field("sbux") String sbux);

    //OTP
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<LoginResponseModel> otp(@Field("r") String r,
                                 @Field("sbux") String sbux);

    //OTP RESEND
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<LoginResponseModel> otpResend(@Field("r") String r,
                                       @Field("sbux") String sbux);

    //Sign Up
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseSignUp> signUp(@Field("r") String r,
                                @Field("sbux") String sbux);

    //Sign In
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<LoginResponseModel> signOut(@Field("r") String r,
                                     @Field("sbux") String sbux);

    //PROMO CODE
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<LoginResponseModel> promo(@Field("r") String r,
                                 @Field("sbux") String sbux);

    //Get User & Card List
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<UserResponseModel> getUser(@Field("r") String r,
                                    @Field("sbux") String sbux);

    //Get New Acc token
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseRefreshToken> getAcc(@Field("r") String r,
                                      @Field("sbux") String sbux);


    //Change Profile
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> updateProfile(@Field("r") String r,
                                     @Field("sbux") String sbux);

    //Change Password
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> changePwd(@Field("r") String r,
                                 @Field("sbux") String sbux);

    //Forgot Password
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> forgotPwd(@Field("r") String r,
                                 @Field("sbux") String sbux);

    //Push Notification Register
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> pnReg(@Field("r") String r,
                             @Field("sbux") String sbux);

    /*All Card Management***************************************/
    //Set Default Card
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> setDefaultCard(@Field("r") String r,
                                      @Field("sbux") String sbux
    );

    //Get Card Reward
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<RewardModel> getCardReward(@Field("r") String r,
                                    @Field("sbux") String sbux
    );

    //Add Card
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> addCard(@Field("r") String r,
                               @Field("sbux") String sbux
    );

    //Report Lost / Stolen Card
    @POST("exchange/mapi_sbux.php")
    @FormUrlEncoded
    Call<ReportLostResponse> reportCard(@Field("r") String r,
                                        @Field("sbux") String sbux
    );

    //Request New Reload Trx
    @POST("exchange/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseReloadCard> newReload(@Field("r") String r,
                                          @Field("sbux") String sbux
    );

    //Cancel Open Reload Trx
    @POST("exchange/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseReloadCard> cancelReload(@Field("r") String r,
                                        @Field("sbux") String sbux
    );

    //Check Open Reload Trx
    @POST("exchange/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseOpenTrx> checkOpenReload(@Field("r") String r,
                                          @Field("sbux") String sbux
    );

    //Card Reload History
    @POST("exchange/mapi_sbux.php")
    @FormUrlEncoded
    Call<CardReloadHistoryResponse> getRH(@Field("r") String r,
                                          @Field("sbux") String sbux
    );

    //Transfer Card Balance
    @POST("exchange/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseCardBalance> getCardBalance(@Field("r") String r,
                                             @Field("sbux") String sbux
    );

    //Card Balance
    @POST("exchange/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseTransferBalance> transferCard(@Field("r") String r,
                                               @Field("sbux") String sbux
    );

    //Transaction History Card
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ActivityResponseModel> getTrx(@Field("r") String r,
                                       @Field("sbux") String sbux
    );

    //Transaction History Card
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<RewardResponseModel> getRewardHistory(
            @Field("r") String r,
            @Field("sbux") String sbux
    );

    /*******************************************************/

    /*All Content*/
    /*Store Data Call Management*/
    //Store List
    @POST("mcs/Api/stores/getNearStore")
    @FormUrlEncoded
    Call<List<StoresModel>> getStoreList(
            @Field("lat") String lat,
            @Field("lon") String lon,
            @Field("sbuxmcs") String sbuxmcs
    );

    /********************************************************/

    /*Menu Data Call Management*/
    //Menu List
    @POST("mcs/Api/menu/getAllmenu2")
    @FormUrlEncoded
    Call<List<MenuModel>> getMenuList(@Field("sbuxmcs") String sbuxmcs);

    /******************************************************/


    //Message List
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseMessageList> getMessageList(
            @Field("r") String r,
            @Field("sbux") String sbux);



    //Message Count
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseMessageCount> getMessageCount(
            @Field("r") String r,
            @Field("sbux") String sbux);

    //Message Read Update
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> updateMessage(
            @Field("r") String r,
            @Field("sbux") String sbux);

//    //Offer List
//    @POST("mcs/Api/campaign/getCampaignList")
//    @FormUrlEncoded
//    Call<ResponseOfferModel> getOfferList(
//            @Field("sbuxmcs") String sbuxmcs);
//Offer List
@POST("sessionm/mapi/mapi_sbux.php")
@FormUrlEncoded
Call<ResponseOfferModel> getOfferList(
        @Field("r") String r,
        @Field("sbux") String sbux);

    //Offer Detail
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<DetailOfferResponseModel> getOfferDetail(
            @Field("r") String r,
            @Field("sbux") String sbux);

    /******************************************************/

    //What's New List
    @POST("mcs/Api/Content/getWhatsNewlist")
    @FormUrlEncoded
    Call<List<WhatsNewModel>> getWhatsNew(
            @Field("sbuxmcs") String sbuxmcs
    );

    /*****************************************************/



    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> postPremiumData(@Field("r") String r,
                                    @Field("sbux") String sbux);

    //otp
    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> getSMSOTP(@Field("r") String r,
                                       @Field("sbux") String sbux);

    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> sendSMSOTPValidation(@Field("r") String r,
                                 @Field("sbux") String sbux);

    @POST("sessionm/mapi/mapi_sbux.php")
    @FormUrlEncoded
    Call<ResponseBase> getIntroPremium(@Field("r") String r,
                                            @Field("sbux") String sbux);


}