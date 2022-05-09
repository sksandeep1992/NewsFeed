package sk.sandeep.newsfeedappmvvm.db

import androidx.room.TypeConverter
import sk.sandeep.newsfeedappmvvm.dto_or_models.Source


class SourceConverters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}