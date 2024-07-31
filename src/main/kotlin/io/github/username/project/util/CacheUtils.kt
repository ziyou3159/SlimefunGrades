package io.github.username.project.util

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

object CacheUtils {

    private val lock = LeaseLock()

    fun safe(func: () -> Unit) {
        val lease = lock.lock(5, TimeUnit.SECONDS)
        try {
            func.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            lock.unlock(lease)

        }
    }
}
class LeaseLock {
    private val lock = AtomicReference<Lease>()

    fun lock(timeout: Long, unit: TimeUnit): Lease {
        while (true) {
            val current = lock.get()
            if (current === Lease(timeout, unit)) {
                // 获取锁成功，继续执行后续操作
                return current
            }

            val lease = Lease(timeout, unit)
            if (lock.compareAndSet(current, lease)) {
                // 获取锁成功，返回锁的信息
                return lease
            }
        }
    }

    fun unlock(lease: Lease) {
        // 将锁的状态设置为未获取
        lock.set(lease)
    }
}
data class Lease(
    val timeout: Long,
    val unit: TimeUnit
)