package com.smatech.rahmaapp.Utils;

import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel;
import com.smatech.rahmaapp.Models.AllSlidersModel.AllSliderModel;
import com.smatech.rahmaapp.Models.Donation.DonationModel;
import com.smatech.rahmaapp.Models.EmployeModel;
import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.Models.GetImagesURLModel;
import com.smatech.rahmaapp.Models.LoginModel;
import com.smatech.rahmaapp.Models.NewAdress.AddNewAdress;
import com.smatech.rahmaapp.Models.RegistrationModel;
import com.smatech.rahmaapp.Models.RegistrationModels.RegistraionMode;
import com.smatech.rahmaapp.Models.SocialModel.SocialModelExample;
import com.smatech.rahmaapp.Models.UserData.UserDataModel;
import com.smatech.rahmaapp.Models.VideoModel.VideoModelExample;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public class Connectors {


    public interface getRegistrationsConnectionServices {
        public String BaseURL = Constants.mBase_Url;

        @GET(Constants.mLogin)
        Call<LoginModel> login(@Query("mobile") String mobile, @Query("password") String password, @Query("token") String token);

        @GET(Constants.add_empoley)
        Call<RegistrationModel> add_empoley(
                @Query("password") String password
                , @Query("username") String username
                , @Query("mobile") String mobile
                , @Query("role") String role
                , @Query("name") String name
                , @Query("organization_id") String organization_id
        );

        @GET(Constants.mRegistration)
        Call<RegistraionMode> Signup(
                @Query("password") String password
                , @Query("username") String username
                , @Query("mobile") String mobile
                , @Query("role") String role
                , @Query("name") String name
                , @Query("promocode") String promocode
        );

        @GET(Constants.get_empolyees)
        Call<EmployeModel> get_empolyees(
                @Query("user_id") String user_id

        );

        @GET(Constants.get_home_sliders)
        Call<AllSliderModel> get_home_sliders(

        );

        @GET(Constants.get_sponsors)
        Call<AllSliderModel> get_sponsors(

        );

        @GET(Constants.get_User_Data)
        Call<UserDataModel> getUserData(@Query("user_id") String user_id);

        @GET(Constants.mset_Edit)
        Call<ForgetPassModel> EditAcc(
                @Query("username") String email
                , @Query("mobile") String mobile
                , @Query("name") String name
                , @Query("id") String id
        );

        @GET(Constants.mForgetPass)
        Call<ForgetPassModel> ForgetPass(
                @Query("email") String email
        );

        @GET(Constants.delete_empoley)
        Call<ForgetPassModel> delete_empoley(
                @Query("user_id") String user_id
        );

        @GET(Constants.send_feedback)
        Call<ForgetPassModel> send_feedback(
                @Query("name") String name
                , @Query("mobile") String mobile
                , @Query("message") String message
                , @Query("user_id") String user_id
                , @Query("title") String title
        );

        @GET(Constants.change_password)
        Call<ForgetPassModel> change_Pass(
                @Query("verification") String verification
                , @Query("new_password") String new_password
        );


        @GET(Constants.get_addresses)
        Call<getAllAdressModel> GetAllAdress(
                @Query("user_id") String user_id
        );

        @GET(Constants.delete_address)
        Call<getAllAdressModel> Delete_Adress(
                @Query("user_id") String user_id
                , @Query("id") String id
        );

        @GET(Constants.add_address)
        Call<AddNewAdress> AddNewAddress(
                @Query("address") String address
                , @Query("city_id") String city_id
                , @Query("longitude") String longitude
                , @Query("latitude") String latitude
                , @Query("description") String description
                , @Query("type") String type
                , @Query("block_no") String block_no
                , @Query("floor_no") String floor_no
                , @Query("house_no") String house_no
                , @Query("user_id") String user_id
                , @Query("title") String title
                , @Query("mobile") String mobile
                , @Query("organization") String organization
        );
 @GET(Constants.edit_address)
        Call<ForgetPassModel> edit_address(
                @Query("address") String address
                , @Query("city_id") String city_id
                , @Query("longitude") String longitude
                , @Query("latitude") String latitude
                , @Query("description") String description
                , @Query("type") String type
                , @Query("block_no") String block_no
                , @Query("floor_no") String floor_no
                , @Query("house_no") String house_no
                , @Query("user_id") String user_id
                , @Query("title") String title
                , @Query("mobile") String mobile
                , @Query("id") String id
                , @Query("organization") String organization
        );

        @GET(Constants.add_donation)
        Call<ForgetPassModel> addDonation(
                @Query("address_id") String address_id
                , @Query("user_id") String user_id
                , @Query("type") String type
                , @Query("description") String description
                , @Query("description") String descriptionDetail
                , @Query("image") String image
                , @Query("category") String category

        );


        @GET(Constants.enroll_to_donation)
        Call<ForgetPassModel> enroll_to_donation(
                @Query("user_id") String user_id
                , @Query("donation_id") String donation_id
                , @Query("from_id") String from_id
        );

        @GET(Constants.cancel_donation)
        Call<ForgetPassModel> cancel_donation(
                @Query("user_id") String user_id
                , @Query("id") String from_id
                , @Query("donation_id") String donation_id

        );

        @GET(Constants.complete_donation)
        Call<ForgetPassModel> complete_donation(
                @Query("user_id") String user_id
                , @Query("id") String from_id
                , @Query("donation_id") String donation_id

        );

        @GET(Constants.report_user_donation)
        Call<ForgetPassModel> report_user_donation(
                @Query("user_id") String user_id
                , @Query("id") String id
                , @Query("donation_id") String donation_id
        );


        @GET(Constants.delete_donation)
        Call<ForgetPassModel> Delete_user_donation(
                @Query("user_id") String user_id
                , @Query("id") String id
        );

        @GET(Constants.edit_donation)
        Call<ForgetPassModel> edit_donation(
                @Query("address_id") String address_id
                , @Query("user_id") String user_id
                , @Query("type") String type
                , @Query("description") String description
                , @Query("description") String description1
                , @Query("image") String image
                , @Query("id") String id
                , @Query("category") String category
        );

        @GET(Constants.delete_address)
        Call<ForgetPassModel> delete_address(
                @Query("user_id") String user_id
                , @Query("id") String id
        );

        /////////////
        ////////////////
        //////////////
        ///////////////
        @GET(Constants.mget_donations)
        Call<DonationModel> getDonations(
                @Query("user_id") String user_id
                , @Query("city_id") String city_id
                , @Query("long") String longm
                , @Query("lat") String latm
                , @Query("role") String role
                , @Query("type") String type
        );

        @GET(Constants.mget_donations)
        Call<DonationModel> getDonations(
                @Query("user_id") String user_id,
                @Query("type") String type
        );
        @GET(Constants.mget_donations)
        Call<DonationModel> getDonations(
                @Query("user_id") String user_id
        );
        @GET(Constants.get_video)
        Call<VideoModelExample> get_video();

        @GET(Constants.get_social)
        Call<SocialModelExample> get_social();

        @Multipart
        @POST(Constants.Upload_image)
        Call<GetImagesURLModel> Upload_Img(
                @Part MultipartBody.Part file
        );
       /* @Multipart
        @POST(Constants.Upload_image)
        Call<GetImagesURLModel> postImage(@Part MultipartBody.Part image);
    */
    }
}
