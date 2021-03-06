/**
 * Copyright (c) 2020 August
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.floofy.api.endpoints.v2

import dev.floofy.api.core.Endpoint
import dev.floofy.api.end
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import java.io.File

class YiffEndpoint: Endpoint(HttpMethod.GET, "/yiff/random") {
    override fun run(ctx: RoutingContext) {
        val res = ctx.response()
        val yiff = File("/var/www/cdn/yiff")
        val files = mutableListOf<File>()
        val listed = yiff.listFiles() ?: emptyArray()

        for (l in listed) {
            if (l.isDirectory) continue

            files.add(l)
        }

        val file = files.random()
        res.setStatusCode(200).sendFile(file.canonicalPath)

        return
    }
}

class RandomYiffEndpoint: Endpoint(HttpMethod.GET, "/yiff") {
    override fun run(ctx: RoutingContext) {
        val res = ctx.response()
        val yiff = File("/var/www/cdn/yiff")
        val files = mutableListOf<File>()
        val listed = yiff.listFiles() ?: emptyArray()

        for (l in listed) {
            if (l.isDirectory) continue

            files.add(l)
        }

        val file = files.random()
        return res.setStatusCode(200).end(JsonObject().apply {
            put("url", "https://cdn.floofy.dev/yiff/${file.name}")
        })
    }
}
