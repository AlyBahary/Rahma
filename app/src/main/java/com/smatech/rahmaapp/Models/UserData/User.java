
package com.smatech.rahmaapp.Models.UserData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("donation_one_family")
    @Expose
    private String donationOneFamily;
    @SerializedName("donation_more_family")
    @Expose
    private String donationMoreFamily;
    @SerializedName("mydonation_one_family")
    @Expose
    private String mydonationOneFamily;
    @SerializedName("mydonation_more_family")
    @Expose
    private String mydonationMoreFamily;
    @SerializedName("user_promo")
    @Expose
    private String userPromo;
    @SerializedName("organization_promo")
    @Expose
    private Integer organizationPromo;
    @SerializedName("benifit_promo")
    @Expose
    private String benifitPromo;
    @SerializedName("account_type")
    @Expose
    private String accountType;

    public String getAccount_type_en() {
        return account_type_en;
    }

    @SerializedName("account_type_en")
    @Expose
    private String account_type_en;

    public String getDonationOneFamily() {
        return donationOneFamily;
    }

    public void setDonationOneFamily(String donationOneFamily) {
        this.donationOneFamily = donationOneFamily;
    }

    public String getDonationMoreFamily() {
        return donationMoreFamily;
    }

    public void setDonationMoreFamily(String donationMoreFamily) {
        this.donationMoreFamily = donationMoreFamily;
    }

    public String getMydonationOneFamily() {
        return mydonationOneFamily;
    }

    public void setMydonationOneFamily(String mydonationOneFamily) {
        this.mydonationOneFamily = mydonationOneFamily;
    }

    public String getMydonationMoreFamily() {
        return mydonationMoreFamily;
    }

    public void setMydonationMoreFamily(String mydonationMoreFamily) {
        this.mydonationMoreFamily = mydonationMoreFamily;
    }

    public String getUserPromo() {
        return userPromo;
    }

    public void setUserPromo(String userPromo) {
        this.userPromo = userPromo;
    }

    public Integer getOrganizationPromo() {
        return organizationPromo;
    }

    public void setOrganizationPromo(Integer organizationPromo) {
        this.organizationPromo = organizationPromo;
    }

    public String getBenifitPromo() {
        return benifitPromo;
    }

    public void setBenifitPromo(String benifitPromo) {
        this.benifitPromo = benifitPromo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}
