package com.farooq.lastfm.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.farooq.lastfm.data.local.model.AlbumInfoEntity
import com.farooq.lastfm.data.local.model.SearchArtist
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


@ProvidedTypeConverter
class Converters @Inject constructor(private val jsonParser: com.farooq.core.utils.JsonParser) {

    @TypeConverter
    fun fromTrackJson(json: String): List<AlbumInfoEntity.Track> {
        return jsonParser.fromJson<ArrayList<AlbumInfoEntity.Track>?>(json = json, object : TypeToken<ArrayList<AlbumInfoEntity.Track>>() {}.type)
            ?: emptyList()
    }

    @TypeConverter
    fun toTrackJson(meanings: List<AlbumInfoEntity.Track>?): String {
        return jsonParser.toJson(meanings, object : TypeToken<ArrayList<AlbumInfoEntity.Track>>() {}.type) ?: "[]"
    }

    @TypeConverter
    fun fromImageJson(json: String): List<AlbumInfoEntity.Image> {
        return jsonParser.fromJson<ArrayList<AlbumInfoEntity.Image>>(json = json, object : TypeToken<ArrayList<AlbumInfoEntity.Image>>() {}.type)
            ?: emptyList()
    }

    @TypeConverter
    fun toImageJson(meanings: List<AlbumInfoEntity.Image>?): String {
        return jsonParser.toJson(meanings, object : TypeToken<ArrayList<AlbumInfoEntity.Image>>() {}.type) ?: "[]"
    }

    @TypeConverter
    fun fromSearchImageJson(json: String): List<SearchArtist.Image> {
        return jsonParser.fromJson<ArrayList<SearchArtist.Image>>(json = json, object : TypeToken<ArrayList<SearchArtist.Image>>() {}.type)
            ?: emptyList()
    }

    @TypeConverter
    fun toSearchImageJson(meanings: List<SearchArtist.Image>?): String {
        return jsonParser.toJson(meanings, object : TypeToken<ArrayList<SearchArtist.Image>>() {}.type) ?: "[]"
    }
}