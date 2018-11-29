package com.example.bahary.rahma.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class Constants {
    public static final String Bundle_Login_Type="LoginType";
    public static final String Donor="0";
    public static final String Beneficiary="1";
    public static final String Organization="2";

    public static final String UserType="UserType";
    public static final String UserName="UserName";
    public static final String Name="Name";
    public static final String Mobile="Mobile";
    public static final String USerID="UserID";
    public static final String UserRole="UserRole";
    public static final String USerEmail="USerEmail";
    public static final String User_Stuck="User_Stuck";
    public static final String UserPromoCode="UserPromoCode";
    ///

    //
    public static final String USerFromID="USerFromID";
    public static final String DontionID="DontionID";

    //
    public static final String Addlocationdlong="Addlocationdlong";
    public static final String Addlocationdlat="Addlocationdlat";
    public static final String add_new_adress_ID="add_new_adress_ID";


    ////////////////URLS///////
    public static final String mBase_Url="http://www.rahma-app.com/";
    public static final String mLogin="rahma/api/login";
    public static final String mRegistration="rahma/api/signup";
    public static final String get_User_Data="rahma/api/get_User_Data";
    public static final String mForgetPass="rahma/api/forget_password";
    public static final String change_password="rahma/api/change_password";
    public static final String mget_donations="rahma/api/get_donations";
    public static final String mset_Edit="rahma/api/edit";
    public static final String add_address="rahma/api/add_address";
    public static final String get_addresses="rahma/api/get_addresses";
    public static final String add_donation="rahma/api/add_donation";
    public static final String edit_donation="rahma/api/edit_donation";
    public static final String enroll_to_donation="rahma/api/enroll_to_donation";
    public static final String cancel_donation="rahma/api/cancel_donation";
    public static final String complete_donation="rahma/api/complete_donation";
    public static final String Upload_image="rahma/api/upload_image_api";
    public static final String report_user_donation="rahma/api/report_user_donation";
    public static final String delete_donation="rahma/api/delete_donation";
    public static final String delete_address="rahma/api/delete_address";
    public static final String add_empoley="rahma/api/add_empoley";
    public static final String get_empolyees="rahma/api/get_empolyees";
    public static final String get_home_sliders="rahma/api/get_home_sliders";
    public static final String get_sponsors="rahma/api/get_sponsors";
    public static final String send_feedback="rahma/api/send_feedback";
    public static final String delete_empoley="rahma/api/delete_empoley";


    public static final String Budle_Fragment_Donation_type="DonationType";
    public static final String Budle_Fragment_From_My_Adress="Budle_Fragment_From_My_Adress";
    public static final String UserOrganzationID="UserOrganzationID";
    public static final String UserCityID="UserCityID";

//////////////////////////////////////////////// Item Details Bundle
    public static final String Budle_Fragment_From_Donation_Itme_ID="Budle_Fragment_From_Donation_Itme_ID";
    public static final String Budle_Fragment_From_Donation_Itme_TO_ID="Budle_Fragment_From_Donation_Itme_TO_ID";
    public static final String Budle_Fragment_From_Donation_Itme_Category="Budle_Fragment_From_Donation_Itme_Category";
    public static final String Budle_Fragment_From_Donation_Itme_AdressDesc="Budle_Fragment_From_Donation_Itme_AdressDesc";
    public static final String Budle_Fragment_From_Donation_Itme_Image="Budle_Fragment_From_Donation_Itme_Image";
    public static final String Budle_Fragment_From_Donation_Itme_Type="Budle_Fragment_From_Donation_Itme_Type";
    public static final String Budle_Fragment_From_Donation_Itme_Describtion="Budle_Fragment_From_Donation_Itme_Describtion";
    public static final String Budle_Fragment_From_Donation_Itme_AdressDes="Budle_Fragment_From_Donation_Itme_Type";
    ////////////////
    public static final String Language="Language";
    public static final String Time="Time";
    public static final String DonationDetailsTOEdit="DonationDetailsTOEdit";
    public static final String User_Exist="User_Exist";
    public static final String AlertMSGFlag="AlertMSGFlag";

    /////////////
public static final void languageChange(String L, Activity context){
    String languageToLoad  = L; // your language
    Locale locale = new Locale(languageToLoad);
    Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.locale = locale;
    context.getBaseContext().getResources().updateConfiguration(config,
            context.getBaseContext().getResources().getDisplayMetrics());
}
}

