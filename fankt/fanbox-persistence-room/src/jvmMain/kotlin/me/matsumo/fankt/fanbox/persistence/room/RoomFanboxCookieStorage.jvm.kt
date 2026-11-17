package me.matsumo.fankt.fanbox.persistence.room

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import me.matsumo.fankt.fanbox.datasource.db.buildFanktDatabase
import me.matsumo.fankt.fanbox.domain.model.db.FanktDatabase
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.absolutePathString

/**
 * Opens Room-backed FANBOX Cookie storage at the user-home `fankt.db` path.
 *
 * The returned storage owns its database instance. Close every [me.matsumo.fankt.fanbox.Fanbox]
 * using it before closing the storage.
 */
public fun createRoomFanboxCookieStorage(
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
): RoomFanboxCookieStorage {
    val database = buildFanktDatabase(
        builder = Room.databaseBuilder<FanktDatabase>(roomFanboxDatabasePath()),
        driver = BundledSQLiteDriver(),
        queryCoroutineContext = ioDispatcher,
    )
    return newRoomFanboxCookieStorage(database, ioDispatcher)
}

internal fun roomFanboxDatabasePath(): String {
    val directory = Path.of(System.getProperty("user.home"), ".fankt")

    Files.createDirectories(directory)

    return directory.resolve("fankt.db").absolutePathString()
}
