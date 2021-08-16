package arturs.suhomiro.translator.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Meanings(
    @field:SerializedName("translation") val translation: Translation?,
    @field:SerializedName("imageUrl") val imageUrl: String?
): Parcelable