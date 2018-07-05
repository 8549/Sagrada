package it.polimi.ingsw.model;

import com.google.gson.annotations.SerializedName;

public enum ObjectiveCardWhere {
    @SerializedName("column")
    COLUMN,
    @SerializedName("row")
    ROW,
    @SerializedName("diagonals")
    DIAGONALS,
    @SerializedName("everywhere")
    EVERYWHERE
}
