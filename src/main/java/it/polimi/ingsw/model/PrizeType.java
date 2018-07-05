package it.polimi.ingsw.model;

import com.google.gson.annotations.SerializedName;

public enum PrizeType {
    @SerializedName("fixed")
    FIXED,
    @SerializedName("sum")
    SUM,
    @SerializedName("count")
    COUNT
}
